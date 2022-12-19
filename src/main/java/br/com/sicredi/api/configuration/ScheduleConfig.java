package br.com.sicredi.api.configuration;

import br.com.sicredi.api.domain.Ruling;
import br.com.sicredi.api.domain.enu.RulingStatus;
import br.com.sicredi.api.dto.response.ResultRulingResponse;
import br.com.sicredi.api.service.RabbitMqService;
import br.com.sicredi.api.service.RulingService;
import br.com.sicredi.api.service.SessionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@EnableScheduling
public class ScheduleConfig {

    private static final Logger logger = LoggerFactory.getLogger(ScheduleConfig.class);
    @Autowired
    private RabbitMqService rabbitMqService;
    @Autowired
    private RulingService rulingService;
    @Autowired
    private SessionService sessionService;

    @Scheduled(fixedDelay = 1000)
    public void closesRecentlyCompletedSessions() {
        List<Ruling> openRulings = rulingService.getRulingByStatus(RulingStatus.OPEN.name());

        logger.info("{} found open rulings", openRulings.size());
        openRulings.stream()
                .filter(ruling -> sessionService.sessionIsClosed(ruling.getSession()))
                .map(ruling -> {
                    logger.info("Ending ruling voting session {}", ruling);
                    return rulingService.closeSession(ruling.getId());
                }).forEach(
                        rulingClosed -> {
                            logger.info("Generating voting results for the ruling {}", rulingClosed);
                            ResultRulingResponse result = rulingService.getPollResult(rulingClosed.getId());

                            logger.info("Publishing result {} to queue {}", result, RabbitMqConfig.RULING_QUEUE);
                            rabbitMqService.sendMessage(RabbitMqConfig.RULING_QUEUE, result);
                        }
                );
    }
}

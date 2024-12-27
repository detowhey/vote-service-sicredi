package br.com.sicredi.api.service.implementation;

import br.com.sicredi.api.service.IRabbitMqService;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RabbitMqService implements IRabbitMqService {

    private final RabbitTemplate rabbitTemplate;

    @Override
    public void sendMessage(String queueName, Object message) {
        this.rabbitTemplate.convertAndSend(queueName, message);
    }
}

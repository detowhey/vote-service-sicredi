package br.com.sicredi.api.service;

import br.com.sicredi.api.domain.Ruling;
import br.com.sicredi.api.domain.Session;
import br.com.sicredi.api.domain.Vote;
import br.com.sicredi.api.domain.enu.PollResult;
import br.com.sicredi.api.domain.enu.RulingStatus;
import br.com.sicredi.api.domain.enu.VoteOption;
import br.com.sicredi.api.dto.response.ResultRulingResponse;
import br.com.sicredi.api.exception.DataBaseException;
import br.com.sicredi.api.exception.RulingNotFoundException;
import br.com.sicredi.api.exception.SessionAlreadyStartedException;
import br.com.sicredi.api.repository.RulingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class RulingService {

    @Autowired
    private RulingRepository rulingRepository;
    @Autowired
    private CpfService cpfService;
    @Autowired
    private SessionService sessionService;

    public Ruling save(Ruling ruling) {
        try {
            rulingRepository.insert(ruling);
            return ruling;
        } catch (DataBaseException e) {
            throw new DataBaseException();
        }
    }

    public Ruling update(Ruling ruling) {
        var data = this.findById(ruling.getId());
        this.updateData(data, ruling);
        return rulingRepository.save(data);
    }

    public List<Ruling> returnAllRulings() {
        return rulingRepository.findAll();
    }

    public List<Ruling> findByNameRegex(String name) {
        return rulingRepository.findByName(name);
    }

    public Ruling findById(String id) {
        return rulingRepository.findById(id).orElseThrow(() -> {
            throw new RulingNotFoundException(id);
        });
    }

    public Ruling openSession(String rulingId, Integer minutes) {
        return Optional.of(this.findById(rulingId)).map(ruling -> {
                    if (ruling.getSession() != null)
                        throw new SessionAlreadyStartedException(ruling.getId());

                    ruling.setStatus(RulingStatus.OPEN);
                    ruling.setSession(new Session(minutes));
                    return ruling;
                })
                .map(rulingRepository::save).get();
    }

    public Ruling closeSession(String rulingId) {
        return Optional.of(this.findById(rulingId)).map(ruling -> {
                    ruling.setStatus(RulingStatus.CLOSED);
                    return ruling;
                })
                .map(rulingRepository::save).get();
    }

    public Ruling addVote(String rulingId, Vote vote) {
        cpfService.validateCpf(vote.getMemberCpf());

        return Optional.of(this.findById(rulingId)).map(ruling -> {
                    if (ruling.getSession() == null)
                        throw new RulingNotFoundException(rulingId);

                    return this.addVote(ruling, vote);
                })
                .map(rulingRepository::save).get();
    }

    public List<Ruling> getRulingByStatus(RulingStatus rulingStatus) {
        return rulingRepository.findAllByStatus(rulingStatus.name());
    }

    public ResultRulingResponse getPollResult(String rulingId) {
        Ruling ruling = this.findById(rulingId);
        Map<VoteOption, Long> pollResult = this.getPollResult(ruling);
        long yesVotes = Optional.ofNullable(pollResult.get(VoteOption.YES)).orElse(0L);
        long noVotes = Optional.ofNullable(pollResult.get(VoteOption.NO)).orElse(0L);

        return ResultRulingResponse.builder()
                .rulingId(rulingId)
                .name(ruling.getName())
                .numberYesVotes(yesVotes)
                .numberNoVotes(noVotes)
                .result(this.calculatePollResult(yesVotes, noVotes))
                .build();
    }

    private PollResult calculatePollResult(long yesVotes, long noVotes) {
        var pollResult = PollResult.DRAW;

        if (yesVotes > noVotes)
            pollResult = PollResult.YES;
        else if (yesVotes < noVotes)
            pollResult = PollResult.NO;
        return pollResult;
    }

    private void updateData(Ruling newRuling, Ruling oldRuling) {
        newRuling.setName(oldRuling.getName());
        newRuling.setSession(oldRuling.getSession());
    }

    private Ruling addVote(Ruling ruling, Vote vote) {
        if (ruling.getSession() == null)
            throw new RulingNotFoundException(ruling.getId());

        sessionService.vote(ruling.getSession(), vote);
        return ruling;
    }

    private Map<VoteOption, Long> getPollResult(Ruling ruling) {
        return Optional.ofNullable(ruling.getSession())
                .map(sessionService::verifyResult)
                .orElseThrow(() -> new RulingNotFoundException(ruling.getId()));
    }
}

package br.com.sicredi.api.service;

import br.com.sicredi.api.domain.Ruling;
import br.com.sicredi.api.domain.Session;
import br.com.sicredi.api.domain.Vote;
import br.com.sicredi.api.domain.enu.PollResult;
import br.com.sicredi.api.domain.enu.RulingStatus;
import br.com.sicredi.api.domain.enu.VoteOption;
import br.com.sicredi.api.dto.response.ResultRulingResponse;
import br.com.sicredi.api.exception.DataBaseException;
import br.com.sicredi.api.exception.InvalidRulingAttributeException;
import br.com.sicredi.api.exception.RulingNotFoundException;
import br.com.sicredi.api.exception.SessionAlreadyStartedException;
import br.com.sicredi.api.repository.RulingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

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

    public List<Ruling> findByNameRegex(String name, Integer numberPage, Integer size) {
        return rulingRepository.findByName(name, this.createPageRequest(numberPage, size)).stream().collect(Collectors.toList());
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

    public List<Ruling> findByRulingByStatus(String rulingStatus, Integer numberPage, Integer size) {
        if (this.checkStatus(rulingStatus))
            return rulingRepository.findAllByStatus(rulingStatus, this.createPageRequest(numberPage, size)).stream().collect(Collectors.toList());
        throw new InvalidRulingAttributeException(rulingStatus);
    }

    public List<Ruling> findByRulingByStatus(String rulingStatus) {
        if (this.checkStatus(rulingStatus))
            return rulingRepository.findAllByStatus(rulingStatus);
        throw new InvalidRulingAttributeException(rulingStatus);
    }

    public ResultRulingResponse findByRulingPollResult(String rulingId) {
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

    public List<ResultRulingResponse> findByListRulingsPollResult(String pollResult) {
        if (pollResult.equalsIgnoreCase(PollResult.YES.name()) || pollResult.equalsIgnoreCase(PollResult.NO.name()) || pollResult.equalsIgnoreCase(PollResult.DRAW.name())) {
            List<Ruling> rulingList = this.returnAllRulings();
            List<ResultRulingResponse> results = new ArrayList<>();

            rulingList.forEach(ruling -> results.add(this.findByRulingPollResult(ruling.getId())));
            return results.stream().filter(result -> result.getResult().name().equals(pollResult)).collect(Collectors.toList());
        }
        throw new InvalidRulingAttributeException(pollResult);
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

    private PageRequest createPageRequest(Integer numberPage, Integer size) {
        PageRequest page;

        if (numberPage == null && size == null)
            page = PageRequest.of(0, 20);
        else if (size == null)
            page = PageRequest.of(numberPage, 20);
        else
            page = PageRequest.of(Objects.requireNonNullElse(numberPage, 0), size);
        return page;
    }

    private boolean checkStatus(String rulingStatus) {
        return rulingStatus.equalsIgnoreCase(RulingStatus.OPEN.name())
                || rulingStatus.equalsIgnoreCase(RulingStatus.CLOSED.name())
                || rulingStatus.equalsIgnoreCase(RulingStatus.NOT_STARTED.name());
    }
}

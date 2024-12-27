package br.com.sicredi.api.service.implementation;

import br.com.sicredi.api.dto.response.ResultRulingResponse;
import br.com.sicredi.api.exception.DataBaseException;
import br.com.sicredi.api.exception.InvalidRulingAttributeException;
import br.com.sicredi.api.exception.RulingNotFoundException;
import br.com.sicredi.api.exception.SessionAlreadyStartedException;
import br.com.sicredi.api.model.Ruling;
import br.com.sicredi.api.model.Session;
import br.com.sicredi.api.model.Vote;
import br.com.sicredi.api.model.enu.PollResult;
import br.com.sicredi.api.model.enu.RulingStatus;
import br.com.sicredi.api.model.enu.VoteOption;
import br.com.sicredi.api.repository.RulingRepository;
import br.com.sicredi.api.service.IRulingService;
import br.com.sicredi.api.util.PageUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RulingService implements IRulingService {

    private final RulingRepository rulingRepository;
    private final CpfService cpfService;
    private final SessionService sessionService;

    @Override
    public Ruling insertTo(Ruling ruling) {
        try {
            Ruling newRuling = Ruling.builder()
                    .name(ruling.getName())
                    .build();

            return rulingRepository.insert(newRuling);
        } catch (DataBaseException e) {
            throw new DataBaseException();
        }
    }

    @Override
    public Page<Ruling> returnAllRulings(Integer page, Integer size, String sortedBy, String orderBy) {
        Pageable pageable = PageUtils.createPageRequest(page, size, sortedBy, orderBy);
        return rulingRepository.findAll(pageable);
    }

    @Override
    public Page<Ruling> findByNameRegex(
            String name,
            Integer pageNumber,
            Integer size,
            String sortedBy,
            String orderBy) {
        Pageable pageable = PageUtils.createPageRequest(pageNumber, size, sortedBy, orderBy);

        if (name == null || name.isBlank()) {
            return rulingRepository.findAll(pageable);
        }
        return rulingRepository.findByName(name, pageable);
    }

    @Override
    public Ruling findById(String id) {
        return rulingRepository.findById(id)
                .orElseThrow(() -> new RulingNotFoundException(id));
    }

    @Override
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

    @Override
    public Ruling closeSession(String rulingId) {
        return Optional.of(this.findById(rulingId)).map(ruling -> {
                    ruling.setStatus(RulingStatus.CLOSED);
                    return ruling;
                })
                .map(rulingRepository::save).get();
    }

    @Override
    public Ruling addVote(String rulingId, Vote vote) {
        cpfService.validateCpf(vote.getMemberCpf());

        return Optional.of(this.findById(rulingId)).map(ruling -> {
                    if (ruling.getSession() == null)
                        throw new RulingNotFoundException(rulingId);

                    return this.addVote(ruling, vote);
                })
                .map(rulingRepository::save).get();
    }

    @Override
    public Page<Ruling> findByRulingByStatus(String rulingStatus, Integer page, Integer size,
                                             String sortedBy, String orderBy) {
        if (this.checkStatus(rulingStatus)) {
            return rulingRepository.findByStatus(rulingStatus, PageUtils.createPageRequest(page, size, sortedBy, orderBy));
        }
        throw new InvalidRulingAttributeException(rulingStatus);
    }

    @Override
    public List<Ruling> findByRulingByStatus(String rulingStatus) {
        if (this.checkStatus(rulingStatus))
            return rulingRepository.findByStatus(rulingStatus);
        throw new InvalidRulingAttributeException(rulingStatus);
    }

    @Override
    public ResultRulingResponse findByRulingPollResult(String rulingId) {
        Ruling ruling = this.findById(rulingId);
        Map<VoteOption, Long> pollResult = this.getPollResult(ruling);
        long yesVotes = Optional.ofNullable(pollResult.get(VoteOption.YES)).orElse(0L);
        long noVotes = Optional.ofNullable(pollResult.get(VoteOption.NO)).orElse(0L);

        return new ResultRulingResponse(
                rulingId,
                ruling.getName(),
                yesVotes,
                noVotes,
                this.calculatePollResult(yesVotes, noVotes)
        );
    }

    @Override
    public Page<ResultRulingResponse> findByListRulingsPollResult(
            String pollResult,
            Integer page,
            Integer size,
            String sortedBy,
            String orderBy) {
        if (pollResult.equalsIgnoreCase(PollResult.YES.name()) || pollResult.equalsIgnoreCase(PollResult.NO.name()) || pollResult.equalsIgnoreCase(PollResult.DRAW.name())) {
            Page<Ruling> rulingList = this.returnAllRulings(page, size, sortedBy, orderBy);
            List<ResultRulingResponse> results = new ArrayList<>();

            rulingList.forEach(ruling -> results.add(this.findByRulingPollResult(ruling.getId())));

            List<ResultRulingResponse> filteredResults = results.stream()
                    .filter(result -> result.name().equals(pollResult))
                    .toList();

            return new PageImpl<>(filteredResults, PageRequest.of(page, size), filteredResults.size());

        }
        throw new InvalidRulingAttributeException(pollResult);
    }

    private PollResult calculatePollResult(long yesVotes, long noVotes) {
        if (yesVotes < 0 || noVotes < 0) {
            throw new InvalidRulingAttributeException("0");
        }

        if (yesVotes > noVotes) {
            return PollResult.YES;
        } else if (yesVotes < noVotes) {
            return PollResult.NO;
        } else {
            return PollResult.DRAW;
        }
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


    private boolean checkStatus(String rulingStatus) {
        return rulingStatus.equalsIgnoreCase(RulingStatus.OPEN.name())
                || rulingStatus.equalsIgnoreCase(RulingStatus.CLOSED.name())
                || rulingStatus.equalsIgnoreCase(RulingStatus.NOT_STARTED.name());
    }
}

package br.com.sicredi.api.service.implementation;

import br.com.sicredi.api.model.Session;
import br.com.sicredi.api.model.Vote;
import br.com.sicredi.api.model.enu.VoteOption;
import br.com.sicredi.api.exception.DuplicateVoteException;
import br.com.sicredi.api.exception.SessionClosedException;
import br.com.sicredi.api.service.ISessionService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Map;

import static java.util.stream.Collectors.counting;
import static java.util.stream.Collectors.groupingBy;

@Service
public class SessionService implements ISessionService {

    @Override
    public void vote(Session session, Vote vote) {
        if (this.sessionIsClosed(session))
            throw new SessionClosedException();

        this.addVote(vote, session);
    }

    @Override
    public boolean memberIsVoted(String memberId, Session session) {
        return session.getVotes().stream().anyMatch(vote -> vote.getMemberId().equals(memberId));
    }

    @Override
    public boolean sessionIsClosed(Session session) {
        return LocalDateTime.now().isAfter(session.getCreationDate());
    }

    public @Override Map<VoteOption, Long> verifyResult(Session session) {
        return session.getVotes().stream().collect(groupingBy(Vote::getVoteOption, counting()));
    }

    private void addVote(Vote vote, Session session) {
        if (this.memberIsVoted(vote.getMemberId(), session))
            throw new DuplicateVoteException(vote.getMemberId());

        session.getVotes().add(vote);
    }
}

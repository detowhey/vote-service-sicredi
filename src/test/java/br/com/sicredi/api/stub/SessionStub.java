package br.com.sicredi.api.stub;

import br.com.sicredi.api.data_provider.FakeData;
import br.com.sicredi.api.domain.Session;
import br.com.sicredi.api.domain.Vote;
import br.com.sicredi.api.domain.enu.VoteOption;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

public class SessionStub {

    private static final FakeData FAKE_DATA = new FakeData();

    public static Session session(int minutes) {
        return new Session(minutes);
    }

    public static Session sessionDefault() {
        return new Session(null);
    }

    public static Session endingVoteSession() {
        Session session = new Session();
        session.setCreationDate(LocalDateTime.now().minusMinutes(1));
        return session;
    }

    public static Session returnSessionYesVotes() {
        return createSession(VoteOption.NO, VoteOption.YES, VoteOption.YES, VoteOption.YES);
    }

    public static Session returnSessionNoVotes() {
        return createSession(VoteOption.NO, VoteOption.NO, VoteOption.NO, VoteOption.YES);
    }

    public static Session returnSessionOnlyYesVotes() {
        return createSession(VoteOption.YES, VoteOption.YES, VoteOption.YES, VoteOption.YES);
    }

    public static Session returnSessionDrawVotes() {
        return createSession(VoteOption.YES, VoteOption.YES, VoteOption.NO, VoteOption.NO);
    }

    private static Session createSession(VoteOption voteOption1, VoteOption voteOption2, VoteOption voteOption3, VoteOption voteOption4) {
        Session session = new Session();
        session.setCreationDate(LocalDateTime.now().minusMinutes(1));
        List<Vote> votes = Arrays.asList(
                createVote(voteOption1),
                createVote(voteOption2),
                createVote(voteOption3),
                createVote(voteOption4)
        );
        session.setVotes(votes);
        return session;
    }

    private static Vote createVote(VoteOption voteOption) {
        return new Vote(FAKE_DATA.generatedId(), FAKE_DATA.generateCpf(), voteOption);
    }
}

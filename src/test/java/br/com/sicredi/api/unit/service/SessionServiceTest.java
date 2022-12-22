package br.com.sicredi.api.unit.service;

import br.com.sicredi.api.data_provider.FakeData;
import br.com.sicredi.api.domain.Session;
import br.com.sicredi.api.domain.enu.VoteOption;
import br.com.sicredi.api.exception.SessionClosedException;
import br.com.sicredi.api.service.SessionService;
import br.com.sicredi.api.stub.SessionStub;
import br.com.sicredi.api.stub.VoteStub;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;

public class SessionServiceTest {

    private final FakeData fakeData = FakeData.getInstance();

    @Mock
    private SessionService sessionServiceMock;

    @BeforeEach
    public void beforeEach() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("Must set default time to 1 minute")
    public void setDefaultTimeout() {
        assertThat(new Session(null).getCreationDate()).isBefore(LocalDateTime.now().plusMinutes(1L));
    }

    @Test
    @DisplayName("Throw an exception for attempted voting in a closed session")
    public void addVoteInSessionEnded() {
        var session = SessionStub.endingVoteSession();
        var vote = VoteStub.createVote(VoteOption.YES);
        doThrow(SessionClosedException.class).when(sessionServiceMock).vote(session, vote);
        assertThrows(SessionClosedException.class, () -> sessionServiceMock.vote(session, vote));
    }

    @Test
    @DisplayName("Throw exception for member who already voted")
    public void memberHasVoted() {
        var session = SessionStub.returnSessionOnlyYesVotes();
        var memberId = fakeData.generatedId();
        when(sessionServiceMock.memberIsVoted(memberId, session)).thenReturn(true);
        assertTrue(sessionServiceMock.memberIsVoted(memberId, session));
    }

    @Test
    @DisplayName("Ending an open session")
    public void sessionFinished() {
        var session = SessionStub.endingVoteSession();
        when(sessionServiceMock.sessionIsClosed(session)).thenCallRealMethod();
        assertTrue(sessionServiceMock.sessionIsClosed(session));
    }

    @Test
    @DisplayName("Should only return results with yes votes")
    public void resultOnlyWithVotesYes() {
        var session = SessionStub.returnSessionOnlyYesVotes();
        var amountOfVotes = Map.of(VoteOption.YES, 4L);

        when(sessionServiceMock.verifyResult(session)).thenCallRealMethod();
        assertEquals(4, amountOfVotes.get(VoteOption.YES));
        assertNull(amountOfVotes.get(VoteOption.NO));
    }

    @Test
    @DisplayName("Should return a result with a DRAW")
    public void resultWithDraw() {
        var session = SessionStub.returnSessionDrawVotes();
        var amountOfVotes = Map.of(VoteOption.YES, 2L, VoteOption.NO, 2L);

        when(sessionServiceMock.verifyResult(session)).thenCallRealMethod();
        assertEquals(2, amountOfVotes.get(VoteOption.YES));
        assertEquals(2, amountOfVotes.get(VoteOption.NO));
    }
}

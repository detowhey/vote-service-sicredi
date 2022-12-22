package br.com.sicredi.api.unit.service;

import br.com.sicredi.api.data_provider.FakeData;
import br.com.sicredi.api.domain.Ruling;
import br.com.sicredi.api.domain.Vote;
import br.com.sicredi.api.domain.enu.PollResult;
import br.com.sicredi.api.domain.enu.VoteOption;
import br.com.sicredi.api.exception.RulingNotFoundException;
import br.com.sicredi.api.exception.SessionClosedException;
import br.com.sicredi.api.repository.RulingRepository;
import br.com.sicredi.api.service.RulingService;
import br.com.sicredi.api.service.SessionService;
import br.com.sicredi.api.stub.RulingStub;
import br.com.sicredi.api.stub.VoteStub;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class RulingServiceTest {

    private final FakeData fakeData = FakeData.getInstance();

    @InjectMocks
    private RulingService rulingService;
    @Spy
    @MockBean
    private RulingRepository rulingRepository;
    @Spy
    @MockBean
    private SessionService sessionService;
    @Captor
    private ArgumentCaptor<Ruling> rulingArgumentCaptor;
    Vote vote;

    @BeforeEach
    public void beforeEach() {
        MockitoAnnotations.openMocks(this);
        vote = VoteStub.createVote(VoteOption.YES);
    }

    @Test
    @DisplayName("You must successfully create a ruling")
    public void createRuling() {
        var ruling = RulingStub.rulingWithoutSession();
        rulingService.insertTo(ruling);
        verify(rulingRepository, Mockito.times(1)).insert(ruling);
    }

    @Test
    @DisplayName("Must start voting session success")
    public void openingSession() {
        when(rulingRepository.findById(any())).thenReturn(Optional.of(RulingStub.rulingWithoutSession()));
        when(rulingRepository.save(any())).thenReturn(RulingStub.openRuling());
        rulingService.openSession(fakeData.generatedId(), null);
        verify(rulingRepository, Mockito.times(1)).save(rulingArgumentCaptor.capture());
        assertNotNull(rulingArgumentCaptor.getValue().getSession());
    }

    @Test
    @DisplayName("Should get an exception Ruling Not Found Exception")
    public void openSessionNoneExistentScore() {
        String id = fakeData.generatedId();

        when(rulingRepository.findById(any())).thenReturn(Optional.empty());
        Throwable exception = Assertions.catchThrowable(
                () -> rulingService.openSession(id, null)
        );
        assertThat(exception).isInstanceOf(RulingNotFoundException.class);
        assertThat(exception.getMessage()).isEqualTo("Ruling not found with this id '" + id + "'");
    }

    @Test
    @DisplayName("You should get a Session Closed Exception")
    public void voteInSessionNotInitiated() {
        when(rulingRepository.findById(any())).thenReturn(Optional.of(RulingStub.endingRuling()));
        Throwable exception = Assertions.catchThrowable(() ->
                rulingService.addVote(fakeData.generatedId(), this.vote)
        );
        assertThat(exception).isInstanceOf(SessionClosedException.class);
    }

    @Test
    @DisplayName("Must count and return the final result of the poll")
    public void getVotingResult() {
        when(rulingRepository.findById(any())).thenReturn(Optional.of(RulingStub.rulingYesVotes()));
        var result = rulingService.findByRulingPollResult(fakeData.generatedId());
        assertNotNull(result.getName());
        assertEquals(3, result.getNumberYesVotes());
        assertEquals(1, result.getNumberNoVotes());
        assertEquals(PollResult.YES, result.getResult());
    }

    @Test
    @DisplayName("Must count and return the final result of the poll")
    public void getVoteResultWithDraw() {
        when(rulingRepository.findById(any())).thenReturn(Optional.of(RulingStub.rulingDrawVotes()));
        var result = rulingService.findByRulingPollResult(fakeData.generatedId());
        assertNotNull(result.getName());
        assertEquals(2, result.getNumberYesVotes());
        assertEquals(2, result.getNumberNoVotes());
        assertEquals(PollResult.DRAW, result.getResult());
    }
}

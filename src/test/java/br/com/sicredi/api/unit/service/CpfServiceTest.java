package br.com.sicredi.api.unit.service;

import br.com.sicredi.api.client.CpfVoteClient;
import br.com.sicredi.api.data_provider.FakeData;
import br.com.sicredi.api.domain.Cpf;
import br.com.sicredi.api.exception.InvalidCpfException;
import br.com.sicredi.api.service.CpfService;
import br.com.sicredi.api.stub.CpfStub;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class CpfServiceTest {

    private final FakeData fakeData = FakeData.getInstance();

    @Spy
    @MockBean
    private CpfVoteClient cpfVoteClientSpy;
    @InjectMocks
    private CpfService cpfServiceInjectMock;
    @Mock
    private CpfService cpfServiceMock;

    @BeforeEach
    public void beforeEach() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("Validate CPF with successfully")
    public void mustValidateCpfSuccessfully() {
        String validCpf = fakeData.generateCpf();

        doReturn(CpfStub.returnCpf(new Cpf(validCpf, true, "ABLE_TO_VOTE"))).when(cpfVoteClientSpy).getCpfEnableToVote(validCpf);
        assertAll(
                () -> assertTrue(cpfServiceInjectMock.getCpfIsValid(validCpf).getAbleVote()),
                () -> assertEquals(cpfServiceInjectMock.getCpfIsValid(validCpf).getStatus(), "ABLE_TO_VOTE")
        );
    }

    @Test
    @DisplayName("Should return unable to vote")
    public void shouldReturnUnableToVote() {
        String invalidCpf = fakeData.generateCpf();

        doReturn(CpfStub.returnCpf(new Cpf(invalidCpf, false, "UNABLE_TO_VOTE"))).when(cpfVoteClientSpy).getCpfEnableToVote(invalidCpf);
        assertAll(
                () -> assertFalse(cpfServiceInjectMock.getCpfIsValid(invalidCpf).getAbleVote()),
                () -> assertEquals(cpfServiceInjectMock.getCpfIsValid(invalidCpf).getStatus(), "UNABLE_TO_VOTE")
        );
    }

    @Test
    @DisplayName("Should return Invalid Cpf Exception")
    public void shouldReturnInvalidCpf() {
        String invalidCpf = fakeData.generateInvalidCpf();

        doThrow(InvalidCpfException.class).when(cpfServiceMock).validateCpf(invalidCpf);
        assertThrows(InvalidCpfException.class, () -> cpfServiceMock.validateCpf(invalidCpf));
    }
}

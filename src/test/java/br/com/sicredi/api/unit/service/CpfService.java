package br.com.sicredi.api.unit.service;

import br.com.sicredi.api.dto.response.CpfResponse;
import br.com.sicredi.api.stub.CpfStub;
import org.junit.jupiter.api.BeforeEach;

public class CpfService {

    private CpfResponse validCpf = CpfStub.returnCpf(true);
    private CpfResponse invalidCpf = CpfStub.returnCpf(false);

    @BeforeEach
    public void setUp() {

    }
}

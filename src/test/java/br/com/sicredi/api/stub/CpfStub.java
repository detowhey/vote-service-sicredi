package br.com.sicredi.api.stub;

import br.com.sicredi.api.data_provider.FakeData;
import br.com.sicredi.api.domain.Cpf;
import br.com.sicredi.api.dto.response.CpfExternalResponse;
import br.com.sicredi.api.dto.response.CpfResponse;

public class CpfStub {

    private static final FakeData fakeData = new FakeData();

    public static CpfResponse returnCpf(boolean valid) {
        return new CpfResponse(fakeData.generateCpf(), valid);
    }

    public static CpfExternalResponse returnCpf(Cpf cpf) {
        return new CpfExternalResponse(cpf);
    }
}
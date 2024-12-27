package br.com.sicredi.api.service.implementation;

import br.com.sicredi.api.feign.client.CpfVoteClient;
import br.com.sicredi.api.feign.model.CpfResponseExternal;
import br.com.sicredi.api.model.Cpf;
import br.com.sicredi.api.model.enu.EligibleToVote;
import br.com.sicredi.api.dto.response.CpfExternalResponse;
import br.com.sicredi.api.exception.InvalidCpfException;
import br.com.sicredi.api.service.ICpfService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class CpfService implements ICpfService {

    private final CpfVoteClient cpfVoteClient;

    public Cpf getCpfIsValid(String cpf) {
        CpfResponseExternal info = cpfVoteClient.getCpfEnableToVote(cpf);
        String status = info.status();
        boolean isAble = status.equals(EligibleToVote.ABLE_TO_VOTE.name());
        return new Cpf(cpf, isAble, status);
    }

    @Override
    public void validateCpf(String cpf) {
        log.info("Validating CPF {}", cpf);

        if (!getCpfIsValid(cpf).getIsValidToVote()) {
            throw new InvalidCpfException(cpf);
        }
    }
}

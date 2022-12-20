package br.com.sicredi.api.service;

import br.com.sicredi.api.client.CpfVoteClient;
import br.com.sicredi.api.domain.Cpf;
import br.com.sicredi.api.domain.enu.EligibleToVote;
import br.com.sicredi.api.dto.response.CpfExternalResponse;
import br.com.sicredi.api.exception.InvalidCpfException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CpfService {

    private final Logger logger = LoggerFactory.getLogger(CpfService.class);

    @Autowired
    private CpfVoteClient cpfVoteClient;

    public Cpf getCpfIsValid(String cpf) {
        CpfExternalResponse info = cpfVoteClient.getCpfEnableToVote(cpf);
        String status = info.getStatus();
        boolean isAble = status.equals(EligibleToVote.ABLE_TO_VOTE.name());
        return new Cpf(cpf, isAble, status);
    }

    public void validateCpf(String cpf) {
        logger.info("Validating CPF {}", cpf);
        this.cpfOk(cpf);
    }

    private void cpfOk(String cpf) {
        if (!getCpfIsValid(cpf).getAbleVote()) {
            throw new InvalidCpfException(cpf);
        }
    }
}

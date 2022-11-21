package br.com.sicredi.api.service;

import br.com.sicredi.api.client.CpfVoteClient;
import br.com.sicredi.api.domain.Cpf;
import br.com.sicredi.api.domain.enu.EligibleToVote;
import br.com.sicredi.api.dto.CpfInformationClientDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CpfService {

    @Autowired
    private CpfVoteClient cpfVoteClient;

    public Cpf getCpfIsValid(String cpf) {
        CpfInformationClientDTO info = cpfVoteClient.getCpfEnaleToVote(cpf);
        long cpfNUmber = Long.parseLong(cpf);
        boolean isAble = info.getStatus().equals(EligibleToVote.ABLE_TO_VOTE);
        return new Cpf(cpfNUmber, isAble);
    }
}

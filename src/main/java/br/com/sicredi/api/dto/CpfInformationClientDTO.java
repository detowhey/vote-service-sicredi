package br.com.sicredi.api.dto;

import br.com.sicredi.api.domain.Cpf;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CpfInformationClientDTO {
    private String status;
    private Boolean isValidToVote;
    private String cpfNumber;

    public CpfInformationClientDTO(Cpf cpf) {
        this.status = cpf.getStatus();
        this.isValidToVote = cpf.getAbleVote();
        this.cpfNumber = cpf.getCpfNumber();
    }
}

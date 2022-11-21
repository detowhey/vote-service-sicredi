package br.com.sicredi.api.dto;

import br.com.sicredi.api.domain.Cpf;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
public class CpfInformationClientDTO {
    @Getter
    private String status;
    @Getter
    private Boolean isValidToVote;
    @Getter
    private String cpfNumber;

        public CpfInformationClientDTO(Cpf cpf) {
            this.status = cpf.getStatus();
            this.isValidToVote = cpf.getAbleVote();
            this.cpfNumber = cpf.getCpfNumber();
        }
}

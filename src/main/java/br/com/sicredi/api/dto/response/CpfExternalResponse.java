package br.com.sicredi.api.dto.response;

import br.com.sicredi.api.domain.Cpf;
import io.swagger.v3.oas.annotations.Hidden;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Hidden
public class CpfExternalResponse {
    private String status;
    private Boolean isValidToVote;
    private String cpfNumber;

    public CpfExternalResponse(Cpf cpf) {
        this.status = cpf.getStatus();
        this.isValidToVote = cpf.getAbleVote();
        this.cpfNumber = cpf.getCpfNumber();
    }
}

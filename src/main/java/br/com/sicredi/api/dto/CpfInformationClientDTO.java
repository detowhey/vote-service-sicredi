package br.com.sicredi.api.dto;

import br.com.sicredi.api.domain.Cpf;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
public class CpfInformationClientDTO {
    @Getter
    private String status;

    public CpfInformationClientDTO(Cpf cpf) {
        this.status = cpf.getNumber().toString();
    }
}

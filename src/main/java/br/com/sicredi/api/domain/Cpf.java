package br.com.sicredi.api.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.hibernate.validator.constraints.br.CPF;

@Data
@AllArgsConstructor
public class Cpf {

    private String cpfNumber;
    private Boolean ableVote;
    private String status;
}

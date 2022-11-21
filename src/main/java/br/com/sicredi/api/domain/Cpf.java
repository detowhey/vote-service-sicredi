package br.com.sicredi.api.domain;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Cpf {

    private String cpfNumber;
    private Boolean ableVote;
    private String status;
}

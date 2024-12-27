package br.com.sicredi.api.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Cpf {

    private String cpfNumber;
    private Boolean isValidToVote;
    private String status;
}

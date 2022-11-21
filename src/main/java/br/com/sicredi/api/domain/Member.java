package br.com.sicredi.api.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Member {

    private String id;
    private Cpf cpf;
    private Vote vote;
    private List<Session> session;
    private Boolean isVoted;
}

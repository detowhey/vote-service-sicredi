package br.com.sicredi.api.domain;

import br.com.sicredi.api.domain.enu.VoteEnum;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Vote {

    private String id;
    private VoteEnum voteEnum;
}

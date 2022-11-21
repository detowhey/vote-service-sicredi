package br.com.sicredi.api.domain.enu;

import lombok.AllArgsConstructor;
import lombok.Getter;


@AllArgsConstructor
public enum VoteEnum {

    YES(true),
    NO(false);

    @Getter
    private final Boolean voteType;
}

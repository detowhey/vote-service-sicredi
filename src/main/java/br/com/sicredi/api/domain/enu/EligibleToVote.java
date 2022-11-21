package br.com.sicredi.api.domain.enu;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public enum EligibleToVote {

    ABLE_TO_VOTE("ABLE_TO_VOTE"),
    UNABLE_TO_VOTE("UNABLE_TO_VOTE");

    @Getter
    private final String isAble;
}

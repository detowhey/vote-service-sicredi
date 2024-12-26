package br.com.sicredi.api.dto.response;

import io.swagger.v3.oas.annotations.Hidden;


@Hidden
public record CpfExternalResponse(
        String status,
        Boolean isValidToVote,
        String cpfNumber
) {
}

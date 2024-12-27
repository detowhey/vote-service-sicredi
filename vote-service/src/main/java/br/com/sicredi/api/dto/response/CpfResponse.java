package br.com.sicredi.api.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;


public record CpfResponse(
        @Schema(description = "Member's CPF", example = "12345678910", maxLength = 11)
        String cpfNumber,
        @Schema(description = "True for CPF enabled to vote", example = "true")
        Boolean isValidToVote
) {
}

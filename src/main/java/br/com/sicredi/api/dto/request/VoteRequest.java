package br.com.sicredi.api.dto.request;

import br.com.sicredi.api.domain.enu.VoteOption;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.hibernate.validator.constraints.br.CPF;


public record VoteRequest(
        @Schema(description = "Member identity", requiredMode = Schema.RequiredMode.REQUIRED, example = "507f1f77bcf86cd799439011")
        @NotBlank(message = "Id is required")
        String memberId,
        @Schema(description = "Member's CPF", requiredMode = Schema.RequiredMode.REQUIRED, maxLength = 11, example = "12345678910")
        @CPF(message = "CPF is not valid")
        @NotBlank(message = "CPF is required")
        String memberCpf,
        @Schema(description = "Votes member", requiredMode = Schema.RequiredMode.REQUIRED, example = "YES or NO")
        @NotNull(message = "Vote option cannot be null")
        VoteOption vote
) {
}

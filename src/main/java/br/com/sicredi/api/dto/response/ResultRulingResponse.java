package br.com.sicredi.api.dto.response;

import br.com.sicredi.api.domain.enu.PollResult;
import io.swagger.v3.oas.annotations.media.Schema;

public record ResultRulingResponse(
        @Schema(description = "Identity of the ruling", example = "507f1f77bcf86cd799439011")
        String rulingId,
        @Schema(description = "Rulings name", example = "Name of ruling")
        String name,
        @Schema(description = "Number of votes 'YES'", example = "2")
        Long numberYesVotes,
        @Schema(description = "Number of votes 'NO'", example = "1")
        Long numberNoVotes,
        @Schema(description = "Result of voting", example = "YES")
        PollResult result
) {
}

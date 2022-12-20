package br.com.sicredi.api.dto.response;

import br.com.sicredi.api.domain.enu.PollResult;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ResultRulingResponse {

    @Schema(description = "Identity of the ruling", example = "507f1f77bcf86cd799439011")
    private String rulingId;
    @Schema(description = "Rulings name", example = "Name of ruling")
    private String name;
    @Schema(description = "Number of votes 'YES'", example = "2")
    private Long numberYesVotes;
    @Schema(description = "Number of votes 'NO'", example = "1")
    private Long numberNoVotes;
    @Schema(description = "Result of voting", example = "YES")
    private PollResult result;
}

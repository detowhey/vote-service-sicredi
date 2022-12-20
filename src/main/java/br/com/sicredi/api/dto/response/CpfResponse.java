package br.com.sicredi.api.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CpfResponse {
    @Schema(description = "Member's CPF", example = "12345678910", maxLength = 11)
    private String cpfNumber;
    @Schema(description = "True for CPF enabled to vote", example = "true")
    private Boolean isValidToVote;

    public CpfResponse(CpfExternalResponse cpfExternalResponse) {
        this.cpfNumber = cpfExternalResponse.getCpfNumber();
        this.isValidToVote = cpfExternalResponse.getIsValidToVote();
    }
}

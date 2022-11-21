package br.com.sicredi.api.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
public class CpfInformationDTO {

    @Getter
    private String cpfNumber;

    @Getter
    private Boolean isValidToVote;

    public CpfInformationDTO(CpfInformationClientDTO informationClientDTO) {
        this.cpfNumber = informationClientDTO.getCpfNumber();
        this.isValidToVote = informationClientDTO.getIsValidToVote();
    }
}

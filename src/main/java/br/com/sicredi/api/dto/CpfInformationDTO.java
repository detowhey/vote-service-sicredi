package br.com.sicredi.api.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@NoArgsConstructor
public class CpfInformationDTO {

    private String cpfNumber;

    private Boolean isValidToVote;

    public CpfInformationDTO(CpfInformationClientDTO informationClientDTO) {
        this.cpfNumber = informationClientDTO.getCpfNumber();
        this.isValidToVote = informationClientDTO.getIsValidToVote();
    }
}

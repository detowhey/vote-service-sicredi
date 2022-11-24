package br.com.sicredi.api.dto;

import br.com.sicredi.api.domain.Ruling;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@JsonPropertyOrder({"id"})
public class RulingIdDTO extends RulingDTO {

    @Getter
    private String id;

    public RulingIdDTO(Ruling ruling) {
        super(ruling);
        this.id = ruling.getId();
    }
}

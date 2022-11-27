package br.com.sicredi.api.dto;

import br.com.sicredi.api.domain.Ruling;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.format.DateTimeFormatter;
import java.util.Locale;

@NoArgsConstructor
@JsonPropertyOrder({"id", "name"})
public class RulingIdDTO extends RulingDTO {

    @Getter
    private String id;
    @Getter
    private String creationDate;

    public RulingIdDTO(Ruling ruling) {
        super(ruling);
        this.id = ruling.getId();
        this.creationDate = ruling.getSession().getCreationDate()
                .format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss", new Locale("pt-br")));
    }
}

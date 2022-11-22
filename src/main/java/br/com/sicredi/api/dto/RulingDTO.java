package br.com.sicredi.api.dto;

import br.com.sicredi.api.domain.Ruling;
import br.com.sicredi.api.domain.Session;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class RulingDTO {

    private String id;
    private Session session;
    private String name;

    public RulingDTO(Ruling ruling) {
        this.id = ruling.getId();
        this.session = ruling.getSession();
        this.name = ruling.getName();
    }
}

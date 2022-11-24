package br.com.sicredi.api.dto;

import br.com.sicredi.api.domain.Member;
import br.com.sicredi.api.domain.Session;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Timer;

@Data
@NoArgsConstructor
public class SessionDTO {

    private Long numberVanancies;

    public SessionDTO(Session session) {
        this.numberVanancies = session.getNumberVacancies();
    }
}

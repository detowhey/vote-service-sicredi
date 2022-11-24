package br.com.sicredi.api.dto;

import br.com.sicredi.api.domain.Ruling;
import com.mongodb.lang.Nullable;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Unwrapped;

@Data
@NoArgsConstructor
public class RulingDTO {

    private String name;
    private Long numberVanancies;

    public RulingDTO(Ruling ruling) {
        this.numberVanancies = ruling.getSession().getNumberVacancies();
        this.name = ruling.getName();
    }
}

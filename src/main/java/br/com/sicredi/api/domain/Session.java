package br.com.sicredi.api.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Session {

    private static Integer DEFAULT_TIME_LIMIT = 1;
    private LocalDateTime creationDate;
    private List<Vote> votes = new ArrayList<>();

    public Session(Integer timeMinutes) {
        this.creationDate = LocalDateTime.now().plusMinutes(timeMinutes == null ? DEFAULT_TIME_LIMIT : timeMinutes);
    }
}

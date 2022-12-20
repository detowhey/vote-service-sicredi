package br.com.sicredi.api.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
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
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy HH:mm:ss", timezone = "UTC")
    private LocalDateTime creationDate;
    private List<Vote> votes = new ArrayList<>();

    public Session(Integer timeMinutes) {
        this.creationDate = LocalDateTime.now().plusMinutes(timeMinutes == null ? DEFAULT_TIME_LIMIT : timeMinutes);
    }
}

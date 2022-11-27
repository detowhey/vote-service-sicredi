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

    @NotNull
    private Boolean isOpen;
    @NotNull
    @Min(value = 1)
    private Long numberVacancies;
    private Long yesVotes;
    private Long noVotes;
    private List<Member> memberList;
    private LocalDateTime creationDate;

    public Session(Long numberVacancies) {
        this.isOpen = false;
        this.numberVacancies = numberVacancies;
        this.yesVotes = 0L;
        this.noVotes = 0L;
        this.memberList = new ArrayList<>();
        this.creationDate = LocalDateTime.now();
    }
}

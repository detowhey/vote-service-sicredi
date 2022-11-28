package br.com.sicredi.api.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document
public class Session {

    private Boolean isOpen;
    private Boolean alreadyVoted;
    @NotNull
    @Min(value = 1)
    private Long numberVacancies;
    private Long yesVotes;
    private Long noVotes;
    private List<Member> memberList;
    private LocalDateTime creationDate;

    public Session(Long numberVacancies) {
        this.isOpen = false;
        this.alreadyVoted = false;
        this.numberVacancies = numberVacancies;
        this.yesVotes = 0L;
        this.noVotes = 0L;
        this.memberList = new ArrayList<>();
        this.creationDate = LocalDateTime.now();
    }
}

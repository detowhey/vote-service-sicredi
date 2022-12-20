package br.com.sicredi.api.domain;

import br.com.sicredi.api.domain.enu.VoteOption;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Vote {

    private String id;
    private String memberId;
    private String memberCpf;
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private VoteOption voteOption;

    public Vote(String memberId, String memberCpf, VoteOption voteOption) {
        this.memberId = memberId;
        this.memberCpf = memberCpf;
        this.voteOption = voteOption;
    }
}

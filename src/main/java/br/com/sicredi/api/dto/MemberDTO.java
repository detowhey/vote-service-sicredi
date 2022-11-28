package br.com.sicredi.api.dto;

import br.com.sicredi.api.domain.Member;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class MemberDTO {

    private String id;
    private String cpf;
    private Boolean isVoted;

    public MemberDTO(Member member) {
        this.id = member.getId();
        this.cpf = getCpf();
        this.isVoted = false;
    }
}

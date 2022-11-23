package br.com.sicredi.api.dto;

import br.com.sicredi.api.domain.Member;
import br.com.sicredi.api.domain.Session;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Timer;

@Data
@NoArgsConstructor
public class SessionDTO {

    private String id;
    private Long totalVotes;
    private Boolean isOpen;
    private Long yesVotes;
    private Long noVotes;
    private List<Member> members;
    private Timer timer;

    public SessionDTO(Session session) {
        this.id = session.getId();
        this.totalVotes = session.getTotalVotes();
        this.isOpen = session.getIsOpen();
        this.yesVotes = session.getYesVotes();
        this.noVotes = session.getNoVotes();
        this.members = session.getMembers();
        this.timer = session.getTimer();
    }
}

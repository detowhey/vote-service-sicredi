package br.com.sicredi.api.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Timer;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Session {

    private String id;
    private Long totalVotes;
    private Long yesVotes;
    private Long noVotes;
    private List<Member> members;
    private Timer timer;
}

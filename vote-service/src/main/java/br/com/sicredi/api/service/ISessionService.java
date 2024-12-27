package br.com.sicredi.api.service;

import br.com.sicredi.api.model.Session;
import br.com.sicredi.api.model.Vote;
import br.com.sicredi.api.model.enu.VoteOption;

import java.util.Map;

public interface ISessionService {

    void vote(Session session, Vote vote);

    boolean memberIsVoted(String memberId, Session session);

    boolean sessionIsClosed(Session session);

    Map<VoteOption, Long> verifyResult(Session session);
}

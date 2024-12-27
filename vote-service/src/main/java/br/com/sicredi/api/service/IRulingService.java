package br.com.sicredi.api.service;

import br.com.sicredi.api.model.Ruling;
import br.com.sicredi.api.model.Vote;
import br.com.sicredi.api.model.enu.PollResult;
import br.com.sicredi.api.dto.response.ResultRulingResponse;
import org.springframework.data.domain.Page;

import java.util.List;

public interface IRulingService {

    Ruling insertTo(Ruling ruling);

    Page<Ruling> returnAllRulings(
            Integer page,
            Integer size,
            String sortedBy,
            String orderBy)
            ;

    Page<Ruling> findByNameRegex(
            String name,
            Integer numberPage,
            Integer size,
            String sortedBy,
            String orderBY
    );

    Ruling findById(String id);

    Ruling openSession(String rulingId, Integer minutes);

    Ruling closeSession(String rulingId);

    Ruling addVote(String rulingId, Vote vote);

    Page<Ruling> findByRulingByStatus(String rulingStatus, Integer numberPage, Integer size, String sortedBy, String orderBY);

    List<Ruling> findByRulingByStatus(String rulingStatus);

    ResultRulingResponse findByRulingPollResult(String rulingId);

    Page<ResultRulingResponse> findByListRulingsPollResult(
            String pollResult,
            Integer page,
            Integer size,
            String sortedBy,
            String orderBy
    );
}

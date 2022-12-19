package br.com.sicredi.api.controller;


import br.com.sicredi.api.domain.Ruling;
import br.com.sicredi.api.domain.Vote;
import br.com.sicredi.api.dto.request.RulingRequest;
import br.com.sicredi.api.dto.request.SessionRequest;
import br.com.sicredi.api.dto.request.VoteRequest;
import br.com.sicredi.api.dto.response.ResultRulingResponse;
import br.com.sicredi.api.dto.response.RulingResponse;
import br.com.sicredi.api.service.RulingService;
import org.bson.types.ObjectId;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/api/v1/ruling", produces = MediaType.APPLICATION_JSON_VALUE)
public class RulingController {

    private final Logger logger = LoggerFactory.getLogger(RulingController.class);

    @Autowired
    private RulingService rulingService;
    @Autowired
    private ModelMapper modelMapper;

    @PostMapping
    public ResponseEntity<RulingResponse> createRuling(@Valid @RequestBody RulingRequest rulingRequest) {
        logger.info("Attempt to create ruling {}", rulingRequest);

        var ruling = modelMapper.map(rulingRequest, Ruling.class);
        ruling.setId(ObjectId.get().toString());
        ruling = rulingService.save(ruling);
        var uri = ServletUriComponentsBuilder.fromCurrentRequest().path("{/id}").buildAndExpand(ruling.getId()).toUri();
        return ResponseEntity.created(uri).body(transform(ruling));
    }

    @PostMapping("/{rulingId}/open")
    public ResponseEntity<RulingResponse> openSession(@PathVariable String rulingId, @Valid @RequestBody SessionRequest sessionRequest) {
        logger.info("Attempt to open a session for the ruling {}", rulingId);
        return ResponseEntity.ok(transform(rulingService.openSession(rulingId, sessionRequest.getMinutes())));
    }

    @PostMapping("/{rulingId}/vote")
    public ResponseEntity<RulingResponse> voteRulingSession(@PathVariable String rulingId, @Valid @RequestBody VoteRequest voteRequest) {
        logger.info("Vote attempt {} for ruling {}", voteRequest, rulingId);
        var vote = modelMapper.map(voteRequest, Vote.class);
        return ResponseEntity.ok(transform(rulingService.addVote(rulingId, vote)));
    }

    @GetMapping("/{rulingId}/result")
    public ResponseEntity<ResultRulingResponse> getRulingResult(@PathVariable String rulingId) {
        logger.info("Getting result for ruling {}", rulingId);
        return ResponseEntity.ok(rulingService.getPollResult(rulingId));
    }

    @GetMapping("/result/{resultStatus}")
    public ResponseEntity<List<ResultRulingResponse>> getRulingsByResultStatus(@PathVariable String resultStatus) {
        logger.info("Search for rulings by the result '{}' of the session", resultStatus);
        return ResponseEntity.ok(transform(rulingService.getRulingByStatus(resultStatus)));
    }



    private RulingResponse transform(Ruling ruling) {
        return modelMapper.map(ruling, RulingResponse.class);
    }

    private List<ResultRulingResponse> transform(List<Ruling> rulingList) {
        return rulingList.stream().map(ruling ->
                modelMapper.map(ruling, ResultRulingResponse.class)).collect(Collectors.toList());
    }
}

package br.com.sicredi.api.controller;


import br.com.sicredi.api.domain.Ruling;
import br.com.sicredi.api.domain.Vote;
import br.com.sicredi.api.dto.request.RulingRequest;
import br.com.sicredi.api.dto.request.SessionRequest;
import br.com.sicredi.api.dto.request.VoteRequest;
import br.com.sicredi.api.dto.response.ResultRulingResponse;
import br.com.sicredi.api.dto.response.RulingResponse;
import br.com.sicredi.api.exception.error.StandbardErrorResponse;
import br.com.sicredi.api.service.RulingService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
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

@Tag(name = "Ruling", description = "Ruling endpoint operations")
@RestController
@RequestMapping(value = "/api/v1/ruling", produces = MediaType.APPLICATION_JSON_VALUE)
public class RulingController {

    private final Logger logger = LoggerFactory.getLogger(RulingController.class);

    @Autowired
    private RulingService rulingService;
    @Autowired
    private ModelMapper modelMapper;

    @Operation(summary = "Create new Ruling", responses = {
            @ApiResponse(responseCode = "201", description = "Ruling created successfully", content = {
                    @Content(schema = @Schema(implementation = RulingResponse.class))
            })
    })
    @PostMapping
    public ResponseEntity<RulingResponse> createRuling(@Valid @RequestBody RulingRequest rulingRequest) {
        logger.info("Attempt to create ruling {}", rulingRequest);

        var ruling = modelMapper.map(rulingRequest, Ruling.class);
        ruling.setId(ObjectId.get().toString());
        ruling = rulingService.save(ruling);
        var uri = ServletUriComponentsBuilder.fromCurrentRequest().path("{/id}").buildAndExpand(ruling.getId()).toUri();
        return ResponseEntity.created(uri).body(transformToRulingResponse(ruling));
    }

    @Operation(summary = "Open the ruling session", responses = {
            @ApiResponse(responseCode = "200", description = "Voting session opened successfully", content = {
                    @Content(schema = @Schema(implementation = RulingResponse.class))
            }),
            @ApiResponse(responseCode = "409", description = "The voting session for the requested agenda has already been opened previously", content = {
                    @Content(schema = @Schema(implementation = StandbardErrorResponse.class))
            })
    })
    @PostMapping("/open/{rulingId}")
    public ResponseEntity<RulingResponse> openSession(@PathVariable String rulingId, @Valid @RequestBody SessionRequest sessionRequest) {
        logger.info("Attempt to open a session for the ruling {}", rulingId);
        return ResponseEntity.ok(transformToRulingResponse(rulingService.openSession(rulingId, sessionRequest.getMinutes())));
    }


    @Operation(summary = "Add a new vote", responses = {
            @ApiResponse(responseCode = "200", description = "Vote successfully added", content = {
                    @Content(schema = @Schema(implementation = RulingResponse.class))
            }),
            @ApiResponse(responseCode = "409", description = "The requested ruling is not open for voting", content = {
                    @Content(schema = @Schema(implementation = StandbardErrorResponse.class))
            })
    })
    @PostMapping("/vote/{rulingId}")
    public ResponseEntity<RulingResponse> voteRulingSession(@PathVariable String rulingId, @Valid @RequestBody VoteRequest voteRequest) {
        logger.info("Vote attempt {} for ruling {}", voteRequest, rulingId);
        var vote = modelMapper.map(voteRequest, Vote.class);
        return ResponseEntity.ok(transformToRulingResponse(rulingService.addVote(rulingId, vote)));
    }

    @Operation(summary = "Returns result of voting session", responses = {
            @ApiResponse(responseCode = "200", description = "Successfully obtained result", content = {
                    @Content(schema = @Schema(implementation = ResultRulingResponse.class))
            }),
            @ApiResponse(responseCode = "409", description = "The requested ruling doesn't have a voting session", content = {
                    @Content(schema = @Schema(implementation = StandbardErrorResponse.class))
            })
    })
    @GetMapping("/result/{rulingId}")
    public ResponseEntity<ResultRulingResponse> getRulingResult(@PathVariable String rulingId) {
        logger.info("Getting result for ruling {}", rulingId);
        return ResponseEntity.ok(rulingService.findByRulingPollResult(rulingId));
    }

    @Operation(summary = "Returns rulings by result status", responses = {
            @ApiResponse(responseCode = "200", description = "Successfully obtained rulings", content = {
                    @Content(schema = @Schema(implementation = ResultRulingResponse.class))
            }),
            @ApiResponse(responseCode = "400", description = "The attribute value is not valid", content = {
                    @Content(schema = @Schema(implementation = StandbardErrorResponse.class))
            })
    })
    @GetMapping("/resultStatus/{resultStatus}")
    public ResponseEntity<List<ResultRulingResponse>> getRulingsByResultStatus(@PathVariable String resultStatus) {
        logger.info("Search for rulings by the result '{}' of the session", resultStatus);
        return ResponseEntity.ok(transformToResultRulingResponse(rulingService.findByRulingByStatus(resultStatus)));
    }

    @Operation(summary = "Returns rulings by vote result", responses = {
            @ApiResponse(responseCode = "200", description = "Successfully obtained rulings", content = {
                    @Content(schema = @Schema(implementation = ResultRulingResponse.class))
            }),
            @ApiResponse(responseCode = "400", description = "The attribute value is not valid", content = {
                    @Content(schema = @Schema(implementation = StandbardErrorResponse.class))
            })
    })
    @GetMapping("/pollResult/{votedResult}")
    public ResponseEntity<List<ResultRulingResponse>> getRulingByPollResult(@PathVariable String voteResult) {
        logger.info("Search for rulings by poll result '{}'", voteResult);
        return ResponseEntity.ok(rulingService.findByListRulingsPollResult(voteResult));
    }

    @Operation(summary = "Returns rulings by name", responses = {
            @ApiResponse(responseCode = "200", description = "Successfully obtained rulings", content = {
                    @Content(schema = @Schema(implementation = ResultRulingResponse.class))
            })
    })
    @GetMapping
    public ResponseEntity<List<RulingResponse>> getRulingsByName(@Valid @RequestParam String name) {
        logger.info("Search for rulings by name '{}'", name);
        return ResponseEntity.ok(transformToRulingResponse(rulingService.findByNameRegex(name)));
    }

    private RulingResponse transformToRulingResponse(Ruling ruling) {
        return modelMapper.map(ruling, RulingResponse.class);
    }

    private List<RulingResponse> transformToRulingResponse(List<Ruling> rulingList) {
        return rulingList.stream().map(ruling ->
                modelMapper.map(ruling, RulingResponse.class)).collect(Collectors.toList());
    }

    private List<ResultRulingResponse> transformToResultRulingResponse(List<Ruling> rulingList) {
        return rulingList.stream().map(ruling ->
                modelMapper.map(ruling, ResultRulingResponse.class)).collect(Collectors.toList());
    }
}

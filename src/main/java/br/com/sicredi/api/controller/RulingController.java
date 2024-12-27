package br.com.sicredi.api.controller;


import br.com.sicredi.api.configuration.swagger.succes.CreatedOpenApiResponse;
import br.com.sicredi.api.configuration.swagger.succes.OkOpenApiResponse;
import br.com.sicredi.api.configuration.swagger.succes.OkPaginationOpenApiResponse;
import br.com.sicredi.api.dto.generic.DataResponse;
import br.com.sicredi.api.dto.generic.DataResponsePage;
import br.com.sicredi.api.dto.request.RulingRequest;
import br.com.sicredi.api.dto.request.SessionRequest;
import br.com.sicredi.api.dto.request.VoteRequest;
import br.com.sicredi.api.dto.response.ResultRulingResponse;
import br.com.sicredi.api.dto.response.RulingResponse;
import br.com.sicredi.api.mapper.RulingMapper;
import br.com.sicredi.api.mapper.VoteMapper;
import br.com.sicredi.api.mapper.implementation.PageConverter;
import br.com.sicredi.api.model.Ruling;
import br.com.sicredi.api.model.Vote;
import br.com.sicredi.api.service.implementation.RulingService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api/${api.version}/ruling", produces = MediaType.APPLICATION_JSON_VALUE)
@Tag(name = "Ruling", description = "Ruling endpoint operations")
public class RulingController {

    private final RulingService rulingService;
    private final RulingMapper rulingMapper;
    private final VoteMapper voteMapper;
    private final PageConverter pageConverter;

    @Operation(summary = "Create new Ruling")
    @CreatedOpenApiResponse
    @PostMapping("/save")
    @ResponseStatus(HttpStatus.CREATED)
    public DataResponse<RulingResponse> createRuling(@Valid @RequestBody RulingRequest rulingRequest) {
        Ruling ruling = rulingMapper.dtoToEntity(rulingRequest);
        ruling = rulingService.insertTo(ruling);
        return DataResponse.of(rulingMapper.transformToRulingResponse(ruling));
    }

    @Operation(summary = "Open the ruling session")
    @CreatedOpenApiResponse
    @PostMapping("/open/{rulingId}")
    @ResponseStatus(HttpStatus.CREATED)
    public DataResponse<RulingResponse> openSession(
            @PathVariable String rulingId,
            @Valid @RequestBody SessionRequest sessionRequest) {
        return DataResponse.of(rulingMapper
                .transformToRulingResponse(rulingService
                        .openSession(rulingId, sessionRequest.minutes())));
    }


    @Operation(summary = "Add a new vote")
    @CreatedOpenApiResponse
    @PostMapping("/vote/{rulingId}")
    @ResponseStatus(HttpStatus.CREATED)
    public DataResponse<RulingResponse> voteRulingSession(
            @PathVariable String rulingId,
            @Valid @RequestBody VoteRequest voteRequest) {
        Vote vote = voteMapper.dtoToEntity(voteRequest);
        return DataResponse.of(rulingMapper.transformToRulingResponse(rulingService.addVote(rulingId, vote)));
    }

    @Operation(summary = "Returns result of voting session")
    @OkOpenApiResponse
    @GetMapping("/result/{id}")
    public DataResponse<ResultRulingResponse> getRulingResult(@PathVariable String id) {
        return DataResponse.of(rulingService.findByRulingPollResult(id));
    }

    @Operation(summary = "Returns rulings by result status")
    @OkOpenApiResponse
    @GetMapping("/resultStatus/{resultStatus}")
    public DataResponsePage<RulingResponse> getRulingsByResultStatus(
            @PathVariable String resultStatus,
            @RequestParam(required = false) Integer page,
            @RequestParam(required = false) Integer size,
            @RequestParam(value = "sortedBy", defaultValue = "id", required = false) String sortedBy,
            @RequestParam(value = "orderBy", defaultValue = "DESC", required = false) String orderBy
    ) {
        var rulings = rulingService.
                findByRulingByStatus(resultStatus, page, size, sortedBy, orderBy);
        return pageConverter.toPageRulingDto(rulings);
    }

    @Operation(summary = "Returns rulings by vote result")
    @OkOpenApiResponse
    @GetMapping("/pollResult/{votedResult}")
    public DataResponsePage<ResultRulingResponse> getRulingByPollResult(
            @PathVariable String voteResult,
            @RequestParam(defaultValue = "1", required = false) Integer page,
            @RequestParam(defaultValue = "10", required = false) Integer size,
            @RequestParam(value = "sortedBy", defaultValue = "id", required = false) String sortedBy,
            @RequestParam(value = "orderBy", defaultValue = "DESC", required = false) String orderBy
    ) {
        return pageConverter
                .toPageResultRulingDto(rulingService
                        .findByListRulingsPollResult(voteResult, page, size, sortedBy, orderBy));
    }

    @Operation(summary = "Returns rulings by name",
            parameters = @Parameter(name = "name", description = "Name of ruling"))
    @OkPaginationOpenApiResponse
    @GetMapping("/search")
    public DataResponsePage<RulingResponse> getRulingsByName(
            @RequestParam(required = false) String name,
            @RequestParam(required = false, defaultValue = "1") Integer page,
            @RequestParam(required = false, defaultValue = "10") Integer size,
            @RequestParam(value = "sortedBy", defaultValue = "id", required = false) String sortedBy,
            @RequestParam(value = "orderBy", defaultValue = "DESC", required = false) String orderBy
    ) {
        var result = rulingService.findByNameRegex(name, page, size, sortedBy, orderBy);
        return pageConverter.toPageRulingDto(result);
    }
}

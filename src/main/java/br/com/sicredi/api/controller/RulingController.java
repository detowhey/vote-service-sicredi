package br.com.sicredi.api.controller;


import br.com.sicredi.api.domain.Ruling;
import br.com.sicredi.api.dto.RulingDTO;
import br.com.sicredi.api.service.RulingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "v1/api/ruling", produces = MediaType.APPLICATION_JSON_VALUE)
public class RulingController {

    @Autowired
    private RulingService rulingService;

    @GetMapping(value = "/{id}")
    public ResponseEntity<RulingDTO> getRulingById(@PathVariable String id) {
        return ResponseEntity.ok().body(new RulingDTO(rulingService.findById(id)));
    }

    @GetMapping
    public ResponseEntity<List<RulingDTO>> getAllRulings() {
        return ResponseEntity.ok(transformToDTO(rulingService.returnAllRulings()));
    }

    @GetMapping
    public ResponseEntity<List<RulingDTO>> getRullingsByNameRegex(@Valid @RequestParam(required = false) String name) {
        return ResponseEntity.ok().body(transformToDTO(rulingService.findByNameRegex(name)));
    }

    private List<RulingDTO> transformToDTO(List<Ruling> rulingList) {
        return rulingList.stream().map(RulingDTO::new).collect(Collectors.toList());
    }
}

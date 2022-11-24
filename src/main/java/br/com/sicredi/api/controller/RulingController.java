package br.com.sicredi.api.controller;


import br.com.sicredi.api.domain.Ruling;
import br.com.sicredi.api.dto.RulingDTO;
import br.com.sicredi.api.dto.RulingIdDTO;
import br.com.sicredi.api.service.RulingService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "api/v1/ruling", produces = MediaType.APPLICATION_JSON_VALUE)
public class RulingController {

    @Autowired
    private RulingService rulingService;

    @GetMapping(value = "/{id}")
    public ResponseEntity<RulingIdDTO> getRulingById(@PathVariable String id) {
        return ResponseEntity.ok().body(new RulingIdDTO(rulingService.findById(id)));
    }

    @GetMapping(value = "/all")
    public ResponseEntity<List<RulingIdDTO>> getAllRulings() {
        return ResponseEntity.ok().body(rulingService.returnAllRulings().stream().map(RulingIdDTO::new).collect(Collectors.toList()));
    }

    @GetMapping
    public ResponseEntity<List<RulingDTO>> getRullingOrByName(@Valid @RequestParam(required = false) String name) {
        return ResponseEntity.ok().body(transformToDTO(rulingService.findByNameRegex(name)));
    }

    @PostMapping
    public ResponseEntity<RulingDTO> insertRuling(@Valid @RequestBody RulingDTO rulingDTO) {
        var ruling = rulingService.fromDTO(rulingDTO);
        ruling.setId(ObjectId.get().toString());
        ruling = rulingService.save(ruling);
        var uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(ruling.getId()).toUri();
        return ResponseEntity.created(uri).body(rulingDTO);
    }

    private List<RulingDTO> transformToDTO(List<Ruling> rulingList) {
        return rulingList.stream().map(RulingDTO::new).collect(Collectors.toList());
    }
}

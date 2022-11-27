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
    public ResponseEntity<RulingDTO> getRulingById(@PathVariable String id) {
        return ResponseEntity.ok().body(new RulingIdDTO(rulingService.findById(id)));
    }

    @GetMapping(value = "/all")
    public ResponseEntity<List<RulingDTO>> getAllRulings() {
        return ResponseEntity.ok().body(transformToDTO(rulingService.returnAllRulings()));
    }

    @GetMapping
    public ResponseEntity<List<RulingDTO>> getRullingOrByName(@Valid @RequestParam(required = false) String name) {
        return ResponseEntity.ok().body(transformToDTO(rulingService.findByNameRegex(name)));
    }

    @PostMapping
    public ResponseEntity<RulingDTO> insertRuling(@Valid @RequestBody RulingDTO rulingDTO) {
        var ruling = rulingService.fromDTO(rulingDTO);
        ruling.setId(ObjectId.get().toString());

        return ResponseEntity.created(ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(ruling.getId())
                .toUri()).body(new RulingIdDTO(rulingService.save(ruling)));
    }

    @PutMapping("open/{id}")
    public ResponseEntity<String> openSession(@PathVariable String id) {
        var ruling = rulingService.findById(id);
        ruling.setId(id);
        ruling.getSession().setIsOpen(true);
        rulingService.update(ruling);
        return ResponseEntity.ok().body("Ruling with id " + id + " is open");
    }

    @PutMapping

    private List<RulingDTO> transformToDTO(List<Ruling> rulingList) {
        return rulingList.stream().map(RulingIdDTO::new).collect(Collectors.toList());
    }
}

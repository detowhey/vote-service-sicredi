package br.com.sicredi.api.controller;


import br.com.sicredi.api.domain.Cpf;
import br.com.sicredi.api.domain.Member;
import br.com.sicredi.api.domain.Ruling;
import br.com.sicredi.api.dto.MemberDTO;
import br.com.sicredi.api.dto.RulingDTO;
import br.com.sicredi.api.dto.RulingIdDTO;
import br.com.sicredi.api.exception.OpenSessionException;
import br.com.sicredi.api.service.CpfService;
import br.com.sicredi.api.service.RulingService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "api/v1/ruling", produces = MediaType.APPLICATION_JSON_VALUE)
public class RulingController {

    @Autowired
    private RulingService rulingService;

    @Autowired
    private CpfService cpfService;

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

        if (ruling.getSession().getMemberList().isEmpty())
            throw new OpenSessionException("No members");

        ruling.setId(id);
        ruling.getSession().setIsOpen(true);
        rulingService.update(ruling);
        return ResponseEntity.ok().body("Ruling with id " + id + " is open");
    }

    @PutMapping("members/{id}")
    public ResponseEntity<List<MemberDTO>> addMembersList(@PathVariable String id, @RequestBody List<MemberDTO> memberDTOList) {
        var ruling = rulingService.findById(id);
        List<MemberDTO> memberDTOs = new ArrayList<>();

        memberDTOList.forEach(memberDTO -> {
            if (cpfService.getCpfIsValid(memberDTO.getCpf()).getAbleVote())
                memberDTOs.add(memberDTO);
        });
        ruling.setId(id);
        ruling.getSession().setMemberList(transformListMemberDTO(memberDTOList));
        rulingService.update(ruling);
        return ResponseEntity.ok().body(memberDTOs);
    }

    private List<RulingDTO> transformToDTO(List<Ruling> rulingList) {
        return rulingList.stream().map(RulingIdDTO::new).collect(Collectors.toList());
    }

    private List<Member> transformListMemberDTO(List<MemberDTO> memberDTOList) {
        List<Member> listMember = new ArrayList<>();

        memberDTOList.forEach(dto -> listMember.add
                (new Member(dto.getId(), new Cpf(dto.getCpf(),
                        cpfService.getCpfIsValid(dto.getCpf()).getAbleVote(),
                        cpfService.getCpfIsValid(dto.getCpf()).getStatus()))
                )
        );
        return listMember;
    }
}

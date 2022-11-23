package br.com.sicredi.api.controller;

import br.com.sicredi.api.dto.CpfInformationClientDTO;
import br.com.sicredi.api.dto.CpfInformationDTO;
import br.com.sicredi.api.service.CpfService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "v1/api/cpf", produces = MediaType.APPLICATION_JSON_VALUE)
public class CpfController {

    @Autowired
    private CpfService cpfService;

    @GetMapping(value = "/{cpf}")
    public ResponseEntity<CpfInformationDTO> getIsEnableCpf(@PathVariable String cpf) {
        return ResponseEntity.ok().body(new CpfInformationDTO
                (new CpfInformationClientDTO(cpfService.getCpfIsValid(cpf))));
    }
}
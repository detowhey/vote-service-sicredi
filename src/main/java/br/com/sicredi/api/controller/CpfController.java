package br.com.sicredi.api.controller;

import br.com.sicredi.api.dto.response.CpfExternalResponse;
import br.com.sicredi.api.dto.response.CpfResponse;
import br.com.sicredi.api.service.CpfService;
import io.swagger.v3.oas.annotations.Hidden;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "api/v1/cpf", produces = MediaType.APPLICATION_JSON_VALUE)
@Hidden
public class CpfController {

    @Autowired
    private CpfService cpfService;

    @GetMapping(value = "/{cpf}")
    public ResponseEntity<CpfResponse> getIsEnableCpf(@PathVariable String cpf) {
        return ResponseEntity.ok().body(new CpfResponse
                (new CpfExternalResponse(cpfService.getCpfIsValid(cpf))));
    }
}

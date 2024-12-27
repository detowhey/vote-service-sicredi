package br.com.sicredi.generate_cpf.controller

import br.com.sicredi.generate_cpf.model.Status
import br.com.sicredi.generate_cpf.service.CpfService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping(value = ["/users"], produces = [MediaType.APPLICATION_JSON_VALUE])
class CpfController(@Autowired private val cpfService: CpfService) {

    @GetMapping("/{cpf}")
    fun getCpfStatus(@PathVariable cpf: String): Status {
        return cpfService.generateCpfStatus(cpf)
    }
}
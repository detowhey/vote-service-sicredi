package br.com.sicredi.api.feign.client;

import br.com.sicredi.api.feign.model.CpfResponseExternal;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "cpf-to-vote", url = "${cpf.api.url}")
public interface CpfVoteClient {

    @GetMapping(value = "users/{cpf}", consumes = MediaType.APPLICATION_JSON_VALUE)
    CpfResponseExternal getCpfEnableToVote(@PathVariable("cpf") String cpf);
}

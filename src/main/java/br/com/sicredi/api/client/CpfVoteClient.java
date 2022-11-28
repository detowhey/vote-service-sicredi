package br.com.sicredi.api.client;

import br.com.sicredi.api.dto.CpfInformationClientDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "cpf-to-vote", url = "https://user-info.herokuapp.com/")
public interface CpfVoteClient {

    @GetMapping(value = "users/{cpf}", consumes = MediaType.APPLICATION_JSON_VALUE)
    CpfInformationClientDTO getCpfEnaleToVote(@PathVariable("cpf") String cpf);

}

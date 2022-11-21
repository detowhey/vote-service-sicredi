package br.com.sicredi.api.client;

import br.com.sicredi.api.dto.CpfInformationClientDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "getCpfToVote", url = "https://user-info.herokuapp.com/")
public interface CpfVoteClient {


    @GetMapping("users/{cpf}")
    CpfInformationClientDTO getCpfEnaleToVote(@PathVariable("cpf") String cpf);

}

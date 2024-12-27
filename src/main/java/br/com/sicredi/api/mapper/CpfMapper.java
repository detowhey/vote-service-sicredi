package br.com.sicredi.api.mapper;

import br.com.sicredi.api.dto.response.CpfResponse;
import br.com.sicredi.api.model.Cpf;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface CpfMapper {

    default CpfResponse cpfToCpfResponse(Cpf cpf){
        return new CpfResponse(
                cpf.getCpfNumber(),
                cpf.getIsValidToVote()
        );
    }
}

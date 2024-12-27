package br.com.sicredi.api.mapper;

import br.com.sicredi.api.dto.request.VoteRequest;
import br.com.sicredi.api.model.Vote;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface VoteMapper {

    default Vote dtoToEntity(VoteRequest request) {
        return Vote.builder()
                .memberId(request.memberId())
                .memberCpf(request.memberCpf())
                .voteOption(request.vote())
                .build();
    }
}

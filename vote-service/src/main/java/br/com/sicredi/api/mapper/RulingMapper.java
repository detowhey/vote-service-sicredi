package br.com.sicredi.api.mapper;

import br.com.sicredi.api.dto.request.RulingRequest;
import br.com.sicredi.api.dto.response.RulingResponse;
import br.com.sicredi.api.model.Ruling;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

import java.util.List;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface RulingMapper {

    default RulingResponse transformToRulingResponse(Ruling ruling) {
        return new RulingResponse(
                ruling.getId(),
                ruling.getName()
        );
    }

    default List<RulingResponse> transformToRulingListResponse(List<Ruling> rulingList) {
        return rulingList.stream().map(this::transformToRulingResponse).toList();
    }

    default Ruling dtoToEntity(RulingRequest request) {
        return Ruling.builder()
                .name(request.name())
                .build();
    }
}

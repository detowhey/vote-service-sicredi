package br.com.sicredi.api.mapper;

import br.com.sicredi.api.dto.generic.DataResponsePage;
import br.com.sicredi.api.dto.response.ResultRulingResponse;
import br.com.sicredi.api.dto.response.RulingResponse;
import br.com.sicredi.api.model.Ruling;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.springframework.data.domain.Page;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface PageMapper {

    DataResponsePage<RulingResponse> toPageRulingDto(Page<Ruling> rulingPage);

    DataResponsePage<ResultRulingResponse> toPageResultRulingDto(Page<ResultRulingResponse>  resultRulingResponses);
}

package br.com.sicredi.api.mapper.implementation;

import br.com.sicredi.api.dto.generic.DataResponsePage;
import br.com.sicredi.api.dto.response.ResultRulingResponse;
import br.com.sicredi.api.dto.response.RulingResponse;
import br.com.sicredi.api.mapper.PageMapper;
import br.com.sicredi.api.mapper.RulingMapper;
import br.com.sicredi.api.model.Ruling;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.function.Function;

@Component
@RequiredArgsConstructor
public class PageConverter implements PageMapper {

    private final RulingMapper rulingMapper;

    @Override
    public DataResponsePage<RulingResponse> toPageRulingDto(Page<Ruling> rulingPage) {
        return this.toCustomPageDto(rulingPage,rulingMapper::transformToRulingListResponse);
    }

    @Override
    public DataResponsePage<ResultRulingResponse> toPageResultRulingDto(Page<ResultRulingResponse> resultRulingResponses) {
       return this.toCustomPageDto(resultRulingResponses);
    }

    private <T, R> DataResponsePage<R> toCustomPageDto(Page<T> page, Function<List<T>, List<R>> mapper) {
        if (page == null)
            return null;

        return DataResponsePage.of(
                mapper.apply(page.getContent()),
                page.getPageable().getPageNumber() + 1,
                page.getPageable().getPageSize(),
                page.getTotalPages(),
                page.getTotalElements()
        );
    }

    private <T> DataResponsePage<T> toCustomPageDto(Page<T> page){
      return   DataResponsePage.of(
                page.getContent(),
                page.getPageable().getPageNumber() + 1,
                page.getPageable().getPageSize(),
                page.getTotalPages(),
                page.getTotalElements()
        );
    }
}

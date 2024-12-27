package br.com.sicredi.api.dto.generic;

import java.util.List;

public record DataResponsePage<T>(
        List<T> data,
        Integer pageNumber,
        Integer pageSize,
        Integer totalPages,
        Long totalElements
) {

    public static <T> DataResponsePage<T> withData(DataResponsePage<T> original, List<T> data) {
        return new DataResponsePage<>(
                data,
                original.pageNumber,
                original.pageSize,
                original.totalPages,
                original.totalElements
        );
    }

    public static <T> DataResponsePage<T> of(List<T> data,
                                             Integer pageNumber,
                                             Integer pageSize,
                                             Integer totalPages,
                                             Long totalElements) {
        return new DataResponsePage<>(data, pageNumber, pageSize, totalPages, totalElements);
    }
}

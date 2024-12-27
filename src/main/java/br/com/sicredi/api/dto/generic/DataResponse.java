package br.com.sicredi.api.dto.generic;

public record DataResponse<T>(T data) {

    public static <T> DataResponse<T> of(T data) {
        return new DataResponse<>(data);
    }
}

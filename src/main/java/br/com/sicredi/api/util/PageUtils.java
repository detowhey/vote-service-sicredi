package br.com.sicredi.api.util;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

public class PageUtils {

    public static PageRequest createPageRequest(Integer pageNumber, Integer pageSize, String sortedBy, String orderBy) {
        if (pageNumber < 1 || pageSize < 1) {
            throw new IllegalArgumentException("'Page' or 'size' cannot be less than 1");
        }

        return PageRequest.of(pageNumber - 1, pageSize,
                Sort.Direction.fromString(orderBy.toUpperCase()), sortedBy);
    }

    public static PageRequest createPageRequest(Integer pageNumber, Integer pageSize) {
        if (pageNumber < 1 || pageSize < 1) {
            throw new IllegalArgumentException("'Page' or 'size' cannot be less than 1");
        }

        return PageRequest.of(pageNumber - 1, pageSize);
    }
}

package com.aurelius.springrealworld.facade.mapper;


import com.aurelius.springrealworld.facade.model.PageModel;
import org.springframework.data.domain.Page;

public abstract class PageMapper<T, K> {
    public PageModel<K> fromPage(Page<T> page) {
        return PageModel.<K>builder()
                .hasNext(page.hasNext())
                .isLast(page.isLast())
                .totalPages(page.getTotalPages())
                .totalElements(page.getTotalElements())
                .build();

    }
}

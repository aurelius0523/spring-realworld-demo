package com.aurelius.springrealworld.facade.model;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class PageModel<T> {
    private List<T> data;
    private long totalElements;
    private int totalPages;
    private boolean isLast;
    private boolean hasNext;
}

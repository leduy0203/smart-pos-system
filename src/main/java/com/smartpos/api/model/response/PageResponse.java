package com.smartpos.api.model.response;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PageResponse {

    private int pageNumber;
    private int pageSize;
    private long totalElements;
    private int totalPages;
    private Object items;
}

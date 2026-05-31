package com.smartpos.api.model.response;


import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CategoryDetailResponse {

    private Long id;
    private String name;
    private String description;
    private List<ProductResponse> products;
}

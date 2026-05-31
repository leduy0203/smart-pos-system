package com.smartpos.api.model.response;

import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProductResponse {
    private Long id;
    private String name;
    private String description;
    private BigDecimal costPrice;
    private CategoryResponse category;
    private Double targetFoodPercent;
    private BigDecimal sellingPrice;
    private boolean isAvailable;
    private String imageUrl;
    private LocalDateTime createdAt;
}

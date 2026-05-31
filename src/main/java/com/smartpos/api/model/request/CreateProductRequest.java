package com.smartpos.api.model.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Getter;

import java.math.BigDecimal;

@Getter
public class CreateProductRequest {

    @NotBlank(message = "Product name must not be blank")
    private String name;

    @NotBlank(message = "Product description must not be blank")
    private String description;

    @NotNull(message = "Selling price must not be blank")
    @Positive
    private BigDecimal sellingPrice;

    @NotNull(message = "Cost price must not be blank")
    @Positive
    private BigDecimal costPrice;

    @NotNull(message = "Target food cost percent must not be null")
    @Positive
    private Double targetFoodCostPercent;

    @NotNull(message = "Category ID must not be null")
    private Long categoryId;
}

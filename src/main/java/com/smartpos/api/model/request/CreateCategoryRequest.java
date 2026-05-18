package com.smartpos.api.model.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class CreateCategoryRequest {

    @NotBlank(message = "Category name not be blank")
    private String name;

    @NotBlank(message = "Category description not be blank")
    private String description;
}

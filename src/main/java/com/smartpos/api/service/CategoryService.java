package com.smartpos.api.service;

import com.smartpos.api.model.request.CreateCategoryRequest;
import com.smartpos.api.model.response.CategoryResponse;

public interface CategoryService {

    CategoryResponse createCategory(CreateCategoryRequest request);

    CategoryResponse updateCategory(Long id, CreateCategoryRequest request);

    CategoryResponse getCategoryById(Long id);

    void deleteCategory(Long id);

}

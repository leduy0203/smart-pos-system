package com.smartpos.api.service;

import com.smartpos.api.model.request.CreateCategoryRequest;
import com.smartpos.api.model.response.CategoryResponse;
import com.smartpos.api.model.response.PageResponse;

import java.util.List;

public interface CategoryService {

    CategoryResponse createCategory(CreateCategoryRequest request);

    CategoryResponse updateCategory(Long id, CreateCategoryRequest request);

    CategoryResponse getCategoryById(Long id);

    void deleteCategory(Long id);

    List<CategoryResponse> getAll();

}

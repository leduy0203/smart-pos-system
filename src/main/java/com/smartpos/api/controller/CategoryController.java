package com.smartpos.api.controller;

import com.smartpos.api.model.request.CreateCategoryRequest;
import com.smartpos.api.model.response.CategoryResponse;
import com.smartpos.api.model.response.ResponseData;
import com.smartpos.api.service.CategoryService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1")
@Slf4j(topic = "CATEGORY-CONTROLLER")
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;
    @PostMapping("/categories")
    @Operation(summary = "Create a new category", description = "Creates a new category with name & des.")
    public ResponseData<CategoryResponse> createCategory(@RequestBody CreateCategoryRequest request){
        log.info("Received request to create category with name: {}", request.getName());

        CategoryResponse categoryResponse = this.categoryService.createCategory(request);

        return ResponseData.<CategoryResponse>builder()
                .code(201)
                .message("Category created successfully")
                .data(categoryResponse)
                .build();
    }


    @DeleteMapping("/categories/{id}")
    @Operation(summary = "Delete a category", description = "Deletes a category by id")
    public ResponseData<Void> deleteCategory(@PathVariable Long id){
        log.info("Received request to delete category with id: {}", id);

        this.categoryService.deleteCategory(id);

        return ResponseData.<Void>builder()
                .code(200)
                .message("Category deleted successfully")
                .build();
    }

    @PutMapping("/categories/{id}")
    @Operation(summary = "Update a category", description = "Updates a category by id")
    public ResponseData<CategoryResponse> updateCategory(@PathVariable Long id, @RequestBody CreateCategoryRequest request){
        log.info("Received request to update category with id: {}", id);

        CategoryResponse categoryResponse = this.categoryService.updateCategory(id, request);

        return ResponseData.<CategoryResponse>builder()
                .code(200)
                .message("Category updated successfully")
                .data(categoryResponse)
                .build();
    }

    @GetMapping("/categories/{id}")
    @Operation(summary = "Get a category", description = "Gets a category by id")
    public ResponseData<CategoryResponse> getCategoryById(@PathVariable Long id){
        log.info("Received request to get category with id: {}", id);

        CategoryResponse categoryResponse = this.categoryService.getCategoryById(id);

        return ResponseData.<CategoryResponse>builder()
                .code(200)
                .message("Category retrieved successfully")
                .data(categoryResponse)
                .build();
    }
}

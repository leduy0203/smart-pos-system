package com.smartpos.api.service.impl;

import com.smartpos.api.exception.AppException;
import com.smartpos.api.exception.ErrorCode;
import com.smartpos.api.model.Category;
import com.smartpos.api.model.request.CreateCategoryRequest;
import com.smartpos.api.model.response.CategoryResponse;
import com.smartpos.api.repository.CategoryRepository;
import com.smartpos.api.service.CategoryService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j(topic = "CATEGORY-SERVICE")
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;

    @Override
    public CategoryResponse createCategory(CreateCategoryRequest request) {
        log.info("Creating category with name: {}", request.getName());

        if (categoryRepository.existsByName(request.getName())) {
            throw new AppException(ErrorCode.CATEGORY_ALREADY_EXISTS);
        }

        Category category = Category.builder()
                .name(request.getName())
                .description(request.getDescription())
                .active(true)
                .build();

        category = categoryRepository.save(category);
        log.info("Category created with id: {}", category.getId());
        return CategoryResponse.builder()
                .id(category.getId())
                .name(category.getName())
                .description(category.getDescription())
                .build();
    }

    @Override
    public CategoryResponse updateCategory(Long id, CreateCategoryRequest request) {

        log.info("Updating category with id: {}", id);

        Category category = getCategoryByIdOrThrow(id);

        if (categoryRepository.existsByNameAndIdNot(request.getName(), id)) {
            throw new AppException(ErrorCode.CATEGORY_ALREADY_EXISTS);
        }

        category.setName(request.getName());
        category.setDescription(request.getDescription());

        category = categoryRepository.save(category);

        log.info("Category with id: {} has been updated", id);

        return CategoryResponse.builder()
                .id(category.getId())
                .name(category.getName())
                .description(category.getDescription())
                .build();
    }

    @Override
    public CategoryResponse getCategoryById(Long id) {
        log.info("Getting category with id: {}", id);
        Category category = getCategoryByIdOrThrow(id);

        return CategoryResponse.builder()
                .id(category.getId())
                .name(category.getName())
                .description(category.getDescription())
                .build();
    }

    @Override
    public void deleteCategory(Long id) {
        log.info("Deleting category with id: {}", id);

        Category category = getCategoryByIdOrThrow(id);
        category.setActive(false);

        categoryRepository.save(category);
        log.info("Category with id: {} has been deleted", id);
    }

    private Category getCategoryByIdOrThrow(Long id) {
        log.info("Fetching category with id: {}", id);
        return categoryRepository.findByIdAndActiveTrue(id)
                .orElseThrow(() -> new AppException(ErrorCode.CATEGORY_NOT_FOUND));
    }
}

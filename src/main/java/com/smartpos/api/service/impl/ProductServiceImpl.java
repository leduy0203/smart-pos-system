package com.smartpos.api.service.impl;

import com.smartpos.api.exception.AppException;
import com.smartpos.api.exception.ErrorCode;
import com.smartpos.api.model.Category;
import com.smartpos.api.model.Product;
import com.smartpos.api.model.request.CreateProductRequest;
import com.smartpos.api.model.response.CategoryResponse;
import com.smartpos.api.model.response.ImageUploadResult;
import com.smartpos.api.model.response.ProductResponse;
import com.smartpos.api.repository.CategoryRepository;
import com.smartpos.api.repository.ProductRepository;
import com.smartpos.api.service.CloudinaryService;
import com.smartpos.api.service.ProductService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
@Slf4j(topic = "PRODUCT-SERVICE")
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;

    private final CategoryRepository categoryRepository;

    private final CloudinaryService cloudinaryService;

    @Override
    @Transactional(rollbackOn = Exception.class)
    public ProductResponse createProduct(CreateProductRequest request , MultipartFile thumbnail) {
        log.info("Creating product with name: {}", request.getName());

        Category category = categoryRepository.findById(request.getCategoryId())
                .orElseThrow(() -> new AppException(ErrorCode.CATEGORY_NOT_FOUND));

        String imageUrl = null;

        if (thumbnail != null && !thumbnail.isEmpty()){

            ImageUploadResult thumbnailUrl = cloudinaryService.uploadImage(thumbnail);

            imageUrl= thumbnailUrl.getImageUrl();
        }

        Product product = Product.builder()
                .name(request.getName())
                .description(request.getDescription())
                .sellingPrice(request.getSellingPrice())
                .costPrice(request.getCostPrice())
                .category(category)
                .imageUrl(imageUrl)
                .targetFoodCostPercent(request.getTargetFoodCostPercent())
                .isAvailable(true)
                .build();

        productRepository.save(product);
        log.info("Product created with ID: {}", product.getId());

        return toResponse(product);
    }

    @Override
    public ProductResponse getProductDetail(Long id) {
        return null;
    }

    private Product getProduct(Long id ){
        return productRepository.findByIdAndIsAvailableTrue(id)
                .orElseThrow(() -> new AppException(ErrorCode.PRODUCT_NOT_FOUND));
    }

    private ProductResponse toResponse(Product product){
        return ProductResponse.builder()
                .id(product.getId())
                .name(product.getName())
                .description(product.getDescription())
                .sellingPrice(product.getSellingPrice())
                .costPrice(product.getCostPrice())
                .category(CategoryResponse.builder().id(product.getCategory().getId()).build())
                .targetFoodPercent(product.getTargetFoodCostPercent())
                .isAvailable(product.isAvailable())
                .createdAt(product.getCreatedAt())
                .build();
    }
}

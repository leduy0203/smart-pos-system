package com.smartpos.api.service.impl;

import ch.qos.logback.core.util.StringUtil;
import com.smartpos.api.exception.AppException;
import com.smartpos.api.exception.ErrorCode;
import com.smartpos.api.model.Category;
import com.smartpos.api.model.Product;
import com.smartpos.api.model.request.CreateProductRequest;
import com.smartpos.api.model.response.CategoryResponse;
import com.smartpos.api.model.response.ImageUploadResult;
import com.smartpos.api.model.response.PageResponse;
import com.smartpos.api.model.response.ProductResponse;
import com.smartpos.api.repository.CategoryRepository;
import com.smartpos.api.repository.ProductRepository;
import com.smartpos.api.service.CloudinaryService;
import com.smartpos.api.service.ProductService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
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
    public PageResponse getProducts(String keyword , Long categoryId , String sort, int page, int size) {
        log.info("Getting products with keyword: {}, categoryId: {}, sort: {}, page: {}, size: {}"
                , keyword, categoryId, sort, page, size);

        Sort sortOrder = Sort.by("id").ascending();

        if (StringUtils.hasLength(sort)){

            String[] part = sort.split(":");

            if (part.length == 2){
                String field = part[0].trim();
                String direction = part[1].trim();

                sortOrder = "desc".equalsIgnoreCase(direction) ?
                        Sort.by(field).descending() :
                        Sort.by(field).ascending();
            }
        }

        int pageNo = 0;

        if (page > 0) {
            pageNo = page - 1;
        }

        Page<Product> products;

        Pageable pageable = PageRequest.of(pageNo, size, sortOrder);

        String searchKeyword = null;

        if (StringUtils.hasText(keyword)){
            searchKeyword = "%" + keyword.trim().toLowerCase() + "%";
        }

        if (searchKeyword != null || categoryId != null){

            products = productRepository.search(searchKeyword, categoryId, pageable);

            log.info("Search products with keyword: {}, categoryId: {}, sort: {}, page: {}, size: {}"
                    , searchKeyword, categoryId, sort, page, size);
        } else {
            products = productRepository.findByIsAvailableTrue(pageable);
        }

        return toPageResponse(products);
    }


    @Override
    public ProductResponse getProductDetail(Long id) {
        return null;
    }



    private Product getProduct(Long id ){
        return productRepository.findByIdAndIsAvailableTrue(id)
                .orElseThrow(() -> new AppException(ErrorCode.PRODUCT_NOT_FOUND));
    }

    private PageResponse toPageResponse(Page<Product> products){
        return PageResponse.builder()
                .items(products.getContent().stream().map(this::toResponse).toList())
                .pageNumber(products.getNumber() + 1)
                .pageSize(products.getSize())
                .totalElements(products.getTotalElements())
                .totalPages(products.getTotalPages())
                .build();
    }




    private ProductResponse toResponse(Product product){
        return ProductResponse.builder()
                .id(product.getId())
                .name(product.getName())
                .description(product.getDescription())
                .sellingPrice(product.getSellingPrice())
                .costPrice(product.getCostPrice())
                .category(CategoryResponse.builder()
                        .id(product.getCategory().getId())
                        .name(product.getCategory().getName())
                        .description(product.getCategory().getDescription())
                        .build())
                .targetFoodPercent(product.getTargetFoodCostPercent())
                .imageUrl(product.getImageUrl())
                .isAvailable(product.isAvailable())
                .createdAt(product.getCreatedAt())
                .build();
    }
}

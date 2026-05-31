package com.smartpos.api.controller;

import com.smartpos.api.model.request.CreateProductRequest;
import com.smartpos.api.model.response.ProductResponse;
import com.smartpos.api.model.response.ResponseData;
import com.smartpos.api.service.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
@Slf4j(topic = "PRODUCT-CONTROLLER")
public class ProductController {

    private final ProductService productService;

    @PostMapping("/products")
    @Operation(summary = "Create a new product", description = "Creates a new product with name, price, category id")
    public ResponseData<ProductResponse> createProduct(
            @RequestPart(name = "request") @Valid CreateProductRequest request,
            @RequestPart(name = "thumbnail" , required = false) MultipartFile thumbnail
    ) {
        log.info("Received request to create product with name: {}", request.getName());

        ProductResponse productResponse = productService.createProduct(request , thumbnail);

        return ResponseData.<ProductResponse>builder()
                .code(201)
                .message("Product created successfully")
                .data(productResponse)
                .build();
    }

}

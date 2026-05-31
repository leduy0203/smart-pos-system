package com.smartpos.api.service;

import com.smartpos.api.model.request.CreateProductRequest;
import com.smartpos.api.model.response.ProductResponse;
import org.springframework.web.multipart.MultipartFile;

public interface ProductService {
    ProductResponse createProduct(CreateProductRequest request , MultipartFile thumbnail);
    ProductResponse getProductDetail(Long id);
}

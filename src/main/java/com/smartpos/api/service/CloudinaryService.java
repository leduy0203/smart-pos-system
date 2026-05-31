package com.smartpos.api.service;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.smartpos.api.exception.AppException;
import com.smartpos.api.exception.ErrorCode;
import com.smartpos.api.model.response.ImageUploadResult;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Slf4j(topic = "CLOUDINARY-SERVICE")
public class CloudinaryService {

    private final Cloudinary cloudinary;

    public ImageUploadResult uploadImage(MultipartFile file) {

        validateFile(file);

        try {

            Map<?, ?> result = cloudinary.uploader().upload(
                    file.getBytes(),
                    ObjectUtils.asMap(
                            "folder", "smart-pos/products",
                            "resource_type", "auto"
                    )
            );

            return ImageUploadResult.builder()
                    .imageUrl(result.get("secure_url").toString())
                    .publicId(result.get("public_id").toString())
                    .build();

        } catch (IOException ex) {

            log.error("Failed to upload image", ex);

            throw new RuntimeException("Image upload failed");
        }
    }

    private void validateFile(MultipartFile file) {

        if (file == null || file.isEmpty()) {
            throw new AppException(ErrorCode.IMAGE_IS_REQUIRE);
        }

        List<String> allowedTypes = List.of(
                "image/jpeg",
                "image/png",
                "image/webp"
        );

        if (!allowedTypes.contains(file.getContentType())) {
            throw new AppException(ErrorCode.INVALID_IMAGE_TYPE);
        }
    }

}

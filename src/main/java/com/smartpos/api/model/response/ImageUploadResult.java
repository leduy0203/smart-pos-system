package com.smartpos.api.model.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class ImageUploadResult {

    private String imageUrl;

    private String publicId;
}

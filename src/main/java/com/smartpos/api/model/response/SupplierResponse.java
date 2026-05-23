package com.smartpos.api.model.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Builder
public class SupplierResponse {

    private Long id;
    private String name;
    private String phone;
    private String address;
}

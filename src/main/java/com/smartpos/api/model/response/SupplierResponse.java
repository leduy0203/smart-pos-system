package com.smartpos.api.model.response;

import lombok.Builder;
import lombok.Setter;

@Setter
@Builder
public class SupplierResponse {

    private Long id;
    private String name;
    private String phone;
    private String address;
}

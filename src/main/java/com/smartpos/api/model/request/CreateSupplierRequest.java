package com.smartpos.api.model.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;

@Getter
public class CreateSupplierRequest {

    @NotBlank(message = "Supplier name not be blank")
    private String name;

    @NotBlank(message = "Supplier email not be blank")
    private String address;

    @NotBlank(message = "Supplier phone not be blank")
    @Pattern(
            regexp = "^(0|\\+84)[3|5|7|8|9][0-9]{8}$",
            message = "Invalid phone number"
    )
    private String phone;
}

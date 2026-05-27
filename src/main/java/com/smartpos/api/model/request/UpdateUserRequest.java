package com.smartpos.api.model.request;

import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateUserRequest {

    @NotBlank(message = "Full Name cannot be blank")
    private String fullName;

    @NotBlank(message = "Email cannot be blank")
    @Email(message = "Email must be valid")
    private String email;

    @NotBlank(message = "Phone Number cannot be blank")
    @Pattern(
            regexp = "^(0|\\+84)\\d{9}$",
            message = "Invalid Vietnamese phone number"
    )
    private String phoneNumber;


    @NotEmpty(message = "Role IDs cannot be empty")
    private Long[] roleIds;
}

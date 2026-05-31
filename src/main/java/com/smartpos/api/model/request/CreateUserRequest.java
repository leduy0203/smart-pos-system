package com.smartpos.api.model.request;

import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class CreateUserRequest {

    @NotBlank(message = "Username cannot be blank")
    @Size(min = 3, max = 50, message = "Username must be between 3 and 50 characters")
    private String userName;

    @NotBlank(message = "Password cannot be blank")
    @Size(min = 6, max = 100, message = "Password must be between 6 and 100 characters")
    private String password;

    @NotBlank(message = "Confirm Password cannot be blank")
    private String confirmPassword;

    @NotBlank(message = "Full Name cannot be blank")
    private String fullName;

    @NotBlank(message = "Email cannot be blank")
    @Email
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

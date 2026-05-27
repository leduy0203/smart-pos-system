package com.smartpos.api.model.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ChangePasswordRequest {

    @NotBlank(message = "Old password cannot be blank")
    private String oldPassword;

    @NotBlank(message = "New password cannot be blank")
    @Size(min = 6, max = 100, message = "Password must be between 6 and 100 characters")
    private String newPassword;

    @NotBlank(message = "Confirm password cannot be blank")
    @Size(min = 6, max = 100, message = "Confirm password must be between 6 and 100 characters")
    private String confirmPassword;
}


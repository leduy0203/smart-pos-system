package com.smartpos.api.model.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateUserRequest {
    private String userName;
    private String password;
    private String confirmPassword;
    private String fullName;
    private String email;
    private String phoneNumber;
}

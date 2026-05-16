package com.smartpos.api.service;

import com.smartpos.api.model.request.CreateUserRequest;
import com.smartpos.api.model.response.UserResponse;

public interface UserService {

    UserResponse createUser(CreateUserRequest request);
}

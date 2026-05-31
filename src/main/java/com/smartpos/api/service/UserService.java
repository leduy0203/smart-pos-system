package com.smartpos.api.service;

import com.smartpos.api.model.request.ChangePasswordRequest;
import com.smartpos.api.model.request.CreateUserRequest;
import com.smartpos.api.model.request.UpdateUserRequest;
import com.smartpos.api.model.response.UserResponse;

public interface UserService {

    UserResponse createUser(CreateUserRequest request);

    UserResponse updateUser(Long id , UpdateUserRequest request);

    UserResponse getUserById(Long id);

    void deleteUser(Long id);

    void changePassword(Long id, ChangePasswordRequest request);

}

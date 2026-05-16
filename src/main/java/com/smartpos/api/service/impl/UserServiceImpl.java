package com.smartpos.api.service.impl;

import com.smartpos.api.model.request.CreateUserRequest;
import com.smartpos.api.model.response.UserResponse;
import com.smartpos.api.service.UserService;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
    @Override
    public UserResponse createUser(CreateUserRequest request) {
        return UserResponse.builder()
                .id(1L)
                .userName(request.getUserName())
                .fullName(request.getFullName())
                .email(request.getEmail())
                .phoneNumber(request.getPhoneNumber())
                .build();
    }
}

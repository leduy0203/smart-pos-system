package com.smartpos.api.controller;

import com.smartpos.api.model.request.CreateUserRequest;
import com.smartpos.api.model.response.ResponseData;
import com.smartpos.api.model.response.UserResponse;
import com.smartpos.api.service.UserService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1")
@Slf4j(topic = "USER-CONTROLLER")
public class UserController {

    private final UserService userService;

    @PostMapping("/users")
    public ResponseData<UserResponse> createUser(@RequestBody CreateUserRequest request) {
        log.info("Received request to create user");
        UserResponse userResponse = this.userService.createUser(request);
        return ResponseData.<UserResponse>builder()
                .code(200)
                .message("User created successfully")
                .data(userResponse)
                .build();
    }

}

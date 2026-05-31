package com.smartpos.api.controller;

import com.smartpos.api.model.request.ChangePasswordRequest;
import com.smartpos.api.model.request.CreateUserRequest;
import com.smartpos.api.model.request.UpdateUserRequest;
import com.smartpos.api.model.response.ResponseData;
import com.smartpos.api.model.response.UserResponse;
import com.smartpos.api.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1")
@Slf4j(topic = "USER-CONTROLLER")
public class UserController {

    private final UserService userService;

    @PostMapping("/users")
    @Operation(summary = "Create a new user", description = "Creates a new user with name, email, password and role")
    public ResponseData<UserResponse> createUser(@Valid @RequestBody CreateUserRequest request) {
        log.info("Received request to create user");

        UserResponse userResponse = this.userService.createUser(request);

        return ResponseData.<UserResponse>builder()
                .code(201)
                .message("User created successfully")
                .data(userResponse)
                .build();
    }

    @PutMapping("/users/{id}")
    @Operation(summary = "Update a user", description = "Updates a user by id with name, email, phone and roles")
    public ResponseData<UserResponse> updateUser(@PathVariable Long id, @Valid @RequestBody UpdateUserRequest request) {
        log.info("Received request to update user with id: {}", id);

        UserResponse userResponse = this.userService.updateUser(id, request);

        return ResponseData.<UserResponse>builder()
                .code(200)
                .message("User updated successfully")
                .data(userResponse)
                .build();
    }

    @GetMapping("/users/{id}")
    @Operation(summary = "Get user by ID", description = "Retrieves a user by their ID")
    public ResponseData<UserResponse> getUserById(@PathVariable Long id) {
        log.info("Received request to get user with id: {}", id);

        UserResponse userResponse = this.userService.getUserById(id);

        return ResponseData.<UserResponse>builder()
                .code(200)
                .message("User retrieved successfully")
                .data(userResponse)
                .build();
    }

    @DeleteMapping("/users/{id}")
    @Operation(summary = "Delete a user", description = "Soft deletes a user by ID")
    public ResponseData<Void> deleteUser(@PathVariable Long id) {
        log.info("Received request to delete user with id: {}", id);

        this.userService.deleteUser(id);

        return ResponseData.<Void>builder()
                .code(200)
                .message("User deleted successfully")
                .build();
    }

    @PostMapping("/users/{id}/change-password")
    @Operation(summary = "Change user password", description = "Changes the password for a user by ID")
    public ResponseData<Void> changePassword(@PathVariable Long id, @Valid @RequestBody ChangePasswordRequest request) {
        log.info("Received request to change password for user with id: {}", id);

        this.userService.changePassword(id, request);

        return ResponseData.<Void>builder()
                .code(200)
                .message("Password changed successfully")
                .build();
    }
}

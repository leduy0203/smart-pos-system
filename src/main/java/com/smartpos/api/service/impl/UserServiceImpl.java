package com.smartpos.api.service.impl;

import com.smartpos.api.common.UserRole;
import com.smartpos.api.exception.AppException;
import com.smartpos.api.exception.ErrorCode;
import com.smartpos.api.model.Role;
import com.smartpos.api.model.User;
import com.smartpos.api.model.UserHasRole;
import com.smartpos.api.model.request.CreateUserRequest;
import com.smartpos.api.model.request.UpdateUserRequest;
import com.smartpos.api.model.response.UserResponse;
import com.smartpos.api.repository.RoleRepository;
import com.smartpos.api.repository.UserRepository;
import com.smartpos.api.service.UserService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Slf4j(topic = "USER-SERVICE")
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    private final RoleRepository roleRepository;

    @Override
    @Transactional(rollbackOn = Exception.class)
    public UserResponse createUser(CreateUserRequest request) {
        log.info("Creating user with username: {}", request.getUserName());

        validateCreateUserRequest(request);

        User user = User.builder()
                .userName(request.getUserName())
                .email(request.getEmail())
                .phoneNumber(request.getPhoneNumber())
                .password(request.getPassword())
                .active(true)
                .build();

        Set<UserHasRole> ls = new HashSet<>();

        for (Long roleId : request.getRoleIds()) {

            Role role = this.roleRepository
                    .findById(roleId)
                    .orElseThrow(() -> new AppException(ErrorCode.ROLE_NOT_FOUND));

            ls.add(UserHasRole.builder()
                    .user(user)
                    .role(role)
                    .build());
        }

        user.setUserHasRoles(ls);

        try {
            user = userRepository.save(user);
        } catch (DataIntegrityViolationException e) {
            handleUserConstraintViolation(e);
        }

        log.info("User created successfully. id={}, username={}", user.getId(), user.getUserName());
        return toResponse(user);
    }

    @Override
    public UserResponse updateUser(UpdateUserRequest request) {
        return null;
    }

    @Override
    public UserResponse getUserById(Long id) {
        return null;
    }

    @Override
    public void deleteUser(Long id) {

    }

    private User getUserByIdOrThrow(Long id) {
        return userRepository.findByIdAndActiveTrue(id)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + id));
    }

    private void validateCreateUserRequest(CreateUserRequest request) {

        if (request.getRoleIds() == null ) {
            throw new AppException(ErrorCode.INVALID_REQUEST);
        }

        if (userRepository.existsByUserName(request.getUserName())) {
            throw new AppException(ErrorCode.USERNAME_ALREADY_EXISTS);
        }

        if (userRepository.existsByEmail(request.getEmail())) {
            throw new AppException(ErrorCode.EMAIL_ALREADY_EXISTS);
        }

        if (userRepository.existsByPhoneNumber(request.getPhoneNumber())) {
            throw new AppException(ErrorCode.PHONE_NUMBER_ALREADY_EXISTS);
        }

        if (!request.getPassword().equals(request.getConfirmPassword())) {
            throw new AppException(ErrorCode.PASSWORD_MISMATCH);
        }
    }

    private UserResponse toResponse(User user){
        return UserResponse.builder()
                .id(user.getId())
                .userName(user.getUserName())
                .email(user.getEmail())
                .phoneNumber(user.getPhoneNumber())
                .fullName(user.getFullName())
                .status(user.isActive())
                .roles(
                        user.getUserHasRoles().stream()
                                .map(userHasRole -> UserRole.valueOf(
                                        userHasRole.getRole().getName().trim().toUpperCase()
                                ))
                                .toList()
                )
                .build();
    }

    private void handleUserConstraintViolation(DataIntegrityViolationException e) {

        Throwable rootCause = e.getRootCause();

        if (rootCause == null) {
            throw new AppException(ErrorCode.INTERNAL_SERVER_ERROR);
        }

        String message = rootCause.getMessage();

        if (message.contains("uk_user_username")) {
            throw new AppException(ErrorCode.USERNAME_ALREADY_EXISTS);
        }

        if (message.contains("uk_user_email")) {
            throw new AppException(ErrorCode.EMAIL_ALREADY_EXISTS);
        }

        if (message.contains("uk_user_phone_number")) {
            throw new AppException(ErrorCode.PHONE_NUMBER_ALREADY_EXISTS);
        }

        throw new AppException(ErrorCode.INTERNAL_SERVER_ERROR);
    }


}

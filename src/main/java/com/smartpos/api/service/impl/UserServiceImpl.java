package com.smartpos.api.service.impl;

import com.smartpos.api.common.UserRole;
import com.smartpos.api.exception.AppException;
import com.smartpos.api.exception.ErrorCode;
import com.smartpos.api.model.Role;
import com.smartpos.api.model.User;
import com.smartpos.api.model.UserHasRole;
import com.smartpos.api.model.request.ChangePasswordRequest;
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
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Slf4j(topic = "USER-SERVICE")
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    private final RoleRepository roleRepository;

    private final PasswordEncoder passwordEncoder;

    @Override
    @Transactional(rollbackOn = Exception.class)
    public UserResponse createUser(CreateUserRequest request) {
        log.info("Creating user with username: {}", request.getUserName());

        validateCreateUserRequest(request);

        User user = User.builder()
                .userName(request.getUserName())
                .email(request.getEmail())
                .phoneNumber(request.getPhoneNumber())
                .password(passwordEncoder.encode(request.getPassword()))
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
    @Transactional(rollbackOn = Exception.class)
    public UserResponse updateUser(Long id, UpdateUserRequest request) {
        log.info("Updating user with id: {}", id);

        User user = getUserByIdOrThrow(id);

        validateUniqueFields(user.getUserName(), request.getEmail(), 
                           request.getPhoneNumber(), id);

        user.setFullName(request.getFullName());
        user.setEmail(request.getEmail());
        user.setPhoneNumber(request.getPhoneNumber());

        Set<UserHasRole> updatedRoles = new HashSet<>();
        for (Long roleId : request.getRoleIds()) {
            Role role = roleRepository.findById(roleId)
                    .orElseThrow(() -> new AppException(ErrorCode.ROLE_NOT_FOUND));
            updatedRoles.add(UserHasRole.builder()
                    .user(user)
                    .role(role)
                    .build());
        }
        user.setUserHasRoles(updatedRoles);

        try {
            user = userRepository.save(user);
        } catch (DataIntegrityViolationException e) {
            handleUserConstraintViolation(e);
        }

        log.info("User id: {} has been updated", id);
        return toResponse(user);
    }

    @Override
    @Transactional(rollbackOn = Exception.class)
    public void changePassword(Long id, ChangePasswordRequest request) {
        log.info("Changing password for user id: {}", id);

        User user = getUserByIdOrThrow(id);

        if (!passwordEncoder.matches(request.getOldPassword(), user.getPassword())) {
            log.warn("Old password mismatch for user id: {}", id);
            throw new AppException(ErrorCode.PASSWORD_MISMATCH);
        }

        validatePasswordMatch(request.getNewPassword(), request.getConfirmPassword());

        user.setPassword(passwordEncoder.encode(request.getNewPassword()));
        userRepository.save(user);

        log.info("Password changed successfully for user id: {}", id);
    }

    @Override
    public UserResponse getUserById(Long id) {
        log.info("Getting user with id: {}", id);
        User user = getUserByIdOrThrow(id);
        return toResponse(user);
    }

    @Override
    @Transactional(rollbackOn = Exception.class)
    public void deleteUser(Long id) {
        log.info("Deleting user with id: {}", id);

        User user = getUserByIdOrThrow(id);
        user.setActive(false);
        userRepository.save(user);

        log.info("User id: {} has been deleted", id);
    }

    private User getUserByIdOrThrow(Long id) {
        return userRepository.findByIdAndActiveTrue(id)
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND));
    }

    private void validateCreateUserRequest(CreateUserRequest request) {
        log.debug("Validating create user request for username: {}", request.getUserName());

        if (request.getRoleIds() == null || request.getRoleIds().length == 0) {
            throw new AppException(ErrorCode.INVALID_REQUEST);
        }

        validateUniqueFields(request.getUserName(), request.getEmail(),
                            request.getPhoneNumber(), null);

        validatePasswordMatch(request.getPassword(), request.getConfirmPassword());
    }


    private void validateUniqueFields(String username, String email,
                                     String phone, Long excludeUserId) {
        log.debug("Validating unique fields");

        if (excludeUserId == null) {

            if (userRepository.existsByUserName(username)) {
                throw new AppException(ErrorCode.USERNAME_ALREADY_EXISTS);
            }
            if (userRepository.existsByEmail(email)) {
                throw new AppException(ErrorCode.EMAIL_ALREADY_EXISTS);
            }
            if (userRepository.existsByPhoneNumber(phone)) {
                throw new AppException(ErrorCode.PHONE_NUMBER_ALREADY_EXISTS);
            }

        } else {

            if (userRepository.existsByUserNameAndIdNot(username, excludeUserId)) {
                throw new AppException(ErrorCode.USERNAME_ALREADY_EXISTS);
            }
            if (userRepository.existsByEmailAndIdNot(email, excludeUserId)) {
                throw new AppException(ErrorCode.EMAIL_ALREADY_EXISTS);
            }
            if (userRepository.existsByPhoneNumberAndIdNot(phone, excludeUserId)) {
                throw new AppException(ErrorCode.PHONE_NUMBER_ALREADY_EXISTS);
            }
        }
    }

    private void validatePasswordMatch(String password, String confirmPassword) {
        log.debug("Validating password match");

        if (password == null || confirmPassword == null) {
            throw new AppException(ErrorCode.INVALID_REQUEST);
        }

        if (!password.equals(confirmPassword)) {
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

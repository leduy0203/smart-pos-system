package com.smartpos.api.model.response;

import com.smartpos.api.common.UserRole;
import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserResponse {
    private Long id;
    private String userName;
    private String fullName;
    private String email;
    private String phoneNumber;
    private boolean status;
    private List<UserRole> roles;
}

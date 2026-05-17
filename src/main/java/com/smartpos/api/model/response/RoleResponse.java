package com.smartpos.api.model.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Builder
public class RoleResponse {
    private Long id;
    private String name;
    private String description;
    private boolean isActive;
    private List<PermissionResponse> permissions;
}

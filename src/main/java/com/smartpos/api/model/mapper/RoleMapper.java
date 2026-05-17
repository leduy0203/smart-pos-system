package com.smartpos.api.model.mapper;

import com.smartpos.api.model.Role;
import com.smartpos.api.model.response.PermissionResponse;
import com.smartpos.api.model.response.RoleResponse;

import java.util.HashSet;
import java.util.Set;
public class RoleMapper {

    private RoleMapper() {
        // Private constructor to prevent instantiation
    }
    public static RoleResponse toRoleResponse(Role role){
        Set<PermissionResponse> permissionResponses = new HashSet<>();
        role.getRoleHasPermission().forEach(roleHasPermission -> {
            PermissionResponse permissionResponse = new PermissionResponse();
            permissionResponse.setId(roleHasPermission.getPermission().getId());
            permissionResponse.setName(roleHasPermission.getPermission().getName());
            permissionResponse.setDescription(roleHasPermission.getPermission().getDescription());
            permissionResponses.add(permissionResponse);
        });
        return RoleResponse.builder()
                .id(role.getId())
                .name(role.getName())
                .description(role.getDescription())
                .isActive(role.isActive())
                .permissions(permissionResponses.stream().toList())
                .build();
    }
}

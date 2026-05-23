package com.smartpos.api.service.impl;

import com.smartpos.api.exception.AppException;
import com.smartpos.api.exception.ErrorCode;
import com.smartpos.api.model.Permission;
import com.smartpos.api.model.Role;
import com.smartpos.api.model.RoleHasPermission;
import com.smartpos.api.model.request.UpdateRoleRequest;
import com.smartpos.api.model.response.PermissionResponse;
import com.smartpos.api.model.response.RoleResponse;
import com.smartpos.api.repository.PermissionRepository;
import com.smartpos.api.repository.RoleRepository;
import com.smartpos.api.service.RoleService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
@RequiredArgsConstructor
@Slf4j(topic = "ROLE-SERVICE")
public class RoleServiceImpl implements RoleService {

    private final RoleRepository roleRepository;

    private final PermissionRepository permissionRepository;

    @Override
    public Role getRoleById(Long id) {
        return this.roleRepository
                .findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.ROLE_NOT_FOUND));
    }

    @Override
    @Transactional(rollbackOn = Exception.class)
    public RoleResponse updateRole(Long id, UpdateRoleRequest request) {
        log.info("Updating role with id: {}", id);
        Role role = this.roleRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.ROLE_NOT_FOUND));

        role.setDescription(request.getDescription());
        role.setActive(request.isActive());
        role.getRoleHasPermission().clear();

        HashSet<RoleHasPermission> roleHasPermissions = new HashSet<>();

        for (Long permissionId : request.getPermissionIds()) {

            Permission permission = this.permissionRepository
                    .findById(permissionId)
                    .orElseThrow(() -> new AppException(ErrorCode.PERMISSION_NOT_FOUND));

            roleHasPermissions.add(RoleHasPermission.builder()
                    .role(role)
                    .permission(permission)
                    .build());
        }

        if(!roleHasPermissions.isEmpty()){
            role.getRoleHasPermission().addAll(roleHasPermissions);
        }

        Role updatedRole = this.roleRepository.save(role);
        log.info("Role with id: {} updated successfully", id);
        return toRoleResponse(updatedRole);
    }


    private RoleResponse toRoleResponse(Role role){
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

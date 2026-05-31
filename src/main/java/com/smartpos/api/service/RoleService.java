package com.smartpos.api.service;

import com.smartpos.api.model.Role;
import com.smartpos.api.model.request.UpdateRoleRequest;
import com.smartpos.api.model.response.RoleResponse;

public interface RoleService {
    Role getRoleById(Long id);

    RoleResponse updateRole(Long id, UpdateRoleRequest request);
}

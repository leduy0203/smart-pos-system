package com.smartpos.api.service;

import com.smartpos.api.model.Permission;
import com.smartpos.api.model.response.PermissionResponse;

public interface PermissionService {

    PermissionResponse createPermission(String name);

    Permission getPermissionById(Long id);
}

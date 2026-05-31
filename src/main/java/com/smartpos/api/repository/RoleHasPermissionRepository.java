package com.smartpos.api.repository;

import com.smartpos.api.model.Permission;
import com.smartpos.api.model.Role;
import com.smartpos.api.model.RoleHasPermission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleHasPermissionRepository extends JpaRepository<RoleHasPermission, Long> {
    boolean existsByRoleAndPermission(Role role, Permission permission);
}


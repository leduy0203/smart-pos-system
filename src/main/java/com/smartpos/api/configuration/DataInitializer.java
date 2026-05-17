package com.smartpos.api.configuration;

import com.smartpos.api.common.UserRole;
import com.smartpos.api.model.Permission;
import com.smartpos.api.model.Role;
import com.smartpos.api.repository.PermissionRepository;
import com.smartpos.api.repository.RoleRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class DataInitializer implements CommandLineRunner {

    private final RoleRepository roleRepository;

    private final PermissionRepository permissionRepository;

    @Override
    public void run(String... args) {

        log.info("Initializing roles and permissions");

        // =========================
        // ROLES
        // =========================

        createRole(
                UserRole.ADMIN.name(),
                "System administrator"
        );

        createRole(
                UserRole.CASHIER.name(),
                "Cashier"
        );

        createRole(
                UserRole.KITCHEN.name(),
                "Kitchen staff"
        );

        // =========================
        // USER PERMISSIONS
        // =========================

        createPermission(
                "USER_VIEW",
                "View users"
        );

        createPermission(
                "USER_CREATE",
                "Create users"
        );

        createPermission(
                "USER_UPDATE",
                "Update users"
        );

        createPermission(
                "USER_DELETE",
                "Delete users"
        );

        // =========================
        // ROLE PERMISSIONS
        // =========================

        createPermission(
                "ROLE_VIEW",
                "View roles"
        );

        createPermission(
                "ROLE_UPDATE",
                "Update roles"
        );

        // =========================
        // PRODUCT PERMISSIONS
        // =========================

        createPermission(
                "PRODUCT_VIEW",
                "View products"
        );

        createPermission(
                "PRODUCT_CREATE",
                "Create products"
        );

        createPermission(
                "PRODUCT_UPDATE",
                "Update products"
        );

        createPermission(
                "PRODUCT_DELETE",
                "Delete products"
        );

        // =========================
        // ORDER PERMISSIONS
        // =========================

        createPermission(
                "ORDER_VIEW",
                "View orders"
        );

        createPermission(
                "ORDER_CREATE",
                "Create orders"
        );

        createPermission(
                "ORDER_UPDATE",
                "Update orders"
        );

        createPermission(
                "ORDER_CANCEL",
                "Cancel orders"
        );

        // =========================
        // INVENTORY PERMISSIONS
        // =========================

        createPermission(
                "INVENTORY_VIEW",
                "View inventory"
        );

        createPermission(
                "INVENTORY_IMPORT",
                "Import inventory"
        );

        createPermission(
                "INVENTORY_UPDATE",
                "Update inventory"
        );

        // =========================
        // REPORT PERMISSIONS
        // =========================

        createPermission(
                "REPORT_VIEW",
                "View reports"
        );

        createPermission(
                "REPORT_EXPORT",
                "Export reports"
        );

        log.info("Roles and permissions initialized");
    }

    private void createRole(
            String name,
            String description
    ) {

        boolean exists =
                roleRepository.existsByName(name);

        if (!exists) {

            Role role = Role.builder()
                    .name(name)
                    .description(description)
                    .isActive(true)
                    .build();

            roleRepository.save(role);

            log.info("Role created: {}", name);
        }
    }

    private void createPermission(
            String name,
            String description
    ) {

        boolean exists =
                permissionRepository.existsByName(name);

        if (!exists) {

            Permission permission = Permission.builder()
                    .name(name)
                    .description(description)
                    .isActive(true)
                    .build();

            permissionRepository.save(permission);

            log.info("Permission created: {}", name);
        }
    }
}

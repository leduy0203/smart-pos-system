package com.smartpos.api.configuration;

import com.smartpos.api.common.TableStatus;
import com.smartpos.api.common.UserRole;
import com.smartpos.api.model.*;
import com.smartpos.api.repository.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
@RequiredArgsConstructor
@Slf4j
public class DataInitializer implements CommandLineRunner {

    private final RoleRepository roleRepository;
    private final PermissionRepository permissionRepository;
    private final CategoryRepository categoryRepository;
    private final SupplierRepository supplierRepository;
    private final TableRepository tableRepository;
    private final RoleHasPermissionRepository roleHasPermissionRepository;

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

        // =========================
        // TABLE PERMISSIONS
        // =========================

        createPermission(
                "TABLE_VIEW",
                "View tables"
        );

        createPermission(
                "TABLE_CREATE",
                "Create tables"
        );

        createPermission(
                "TABLE_UPDATE",
                "Update tables"
        );

        createPermission(
                "TABLE_DELETE",
                "Delete tables"
        );

        // =========================
        // CATEGORIES (Sample Data)
        // =========================

        initializeCategories();

        // =========================
        // SUPPLIERS (Sample Data)
        // =========================

        initializeSuppliers();

        // =========================
        // RESTAURANT TABLES (Sample Data)
        // =========================

        initializeTables();

        // =========================
        // ROLE-PERMISSION MAPPINGS
        // =========================

        assignPermissionsToRoles();

        log.info("Roles, permissions, categories, suppliers and tables initialized");
    }

    private void createRole(String name, String description) {

        boolean exists = roleRepository.existsByName(name);

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

    private void createPermission(String name, String description) {

        boolean exists = permissionRepository.existsByName(name);

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

    private void initializeCategories() {
        log.info("Initializing sample categories");

        List<String[]> categories = Arrays.asList(
                new String[]{"Món chính", "Các món ăn chính trong menu"},
                new String[]{"Cafe", "Các món cafe nóng/lạnh"},
                new String[]{"Trà sữa", "Các loại trà sữa và topping đi kèm"},
                new String[]{"Trà trái cây", "Các loại trà kết hợp trái cây"},
                new String[]{"Sinh tố", "Các loại sinh tố xay"},
                new String[]{"Đồ ăn nhanh", "Các món ăn chế biến nhanh"},
                new String[]{"Món phụ", "Các món ăn kèm hoặc ăn nhẹ"},
                new String[]{"Topping", "Các món thêm cho sản phẩm"}
        );

        for (String[] catData : categories) {
            if (!categoryRepository.existsByName(catData[0])) {
                Category category = Category.builder()
                        .name(catData[0])
                        .description(catData[1])
                        .active(true)
                        .build();

                categoryRepository.save(category);
                log.info("Category created: {}", catData[0]);
            }
        }
    }

    private void initializeSuppliers() {
        log.info("Initializing sample suppliers");

        List<String[]> suppliers = Arrays.asList(
                new String[]{
                        "Công Ty TNHH Thực Phẩm Minh Phát",
                        "0912345678",
                        "12 Nguyễn Văn Linh, Quận 7, TP. Hồ Chí Minh"
                },
                new String[]{
                        "Nhà Phân Phối Nguyên Liệu Hoàng Gia",
                        "0987654321",
                        "45 Lê Trọng Tấn, Quận Tân Phú, TP. Hồ Chí Minh"
                },
                new String[]{
                        "Công Ty Cung Cấp Đồ Uống An Khang",
                        "0935123456",
                        "88 Phạm Văn Đồng, Thành phố Thủ Đức, TP. Hồ Chí Minh"
                }
        );

        for (String[] supData : suppliers) {
            if (!supplierRepository.existsByName(supData[0])) {
                Supplier supplier = Supplier.builder()
                        .name(supData[0])
                        .phone(supData[1])
                        .address(supData[2])
                        .active(true)
                        .build();

                supplierRepository.save(supplier);
                log.info("Supplier created: {}", supData[0]);
            }
        }
    }

    private void initializeTables() {
        log.info("Initializing sample restaurant tables");

        for (int i = 1; i <= 10; i++) {
            String tableNumber = "BÀN-" + String.format("%02d", i);

            if (!tableRepository.existsByTableNumber(tableNumber)) {
                RestaurantTable table = RestaurantTable.builder()
                        .tableNumber(tableNumber)
                        .capacity((i % 3) + 2)
                        .active(true)
                        .status(TableStatus.AVAILABLE)
                        .build();

                tableRepository.save(table);
                log.info("Table created: {} with capacity {}", tableNumber, table.getCapacity());
            }
        }
    }

    private void assignPermissionsToRoles() {
        log.info("Assigning permissions to roles");

        Map<String, List<String>> rolePermissions = new HashMap<>();

        // ADMIN: All permissions
        rolePermissions.put(UserRole.ADMIN.name(), Arrays.asList(
                "USER_VIEW", "USER_CREATE", "USER_UPDATE", "USER_DELETE",
                "ROLE_VIEW", "ROLE_UPDATE",
                "PRODUCT_VIEW", "PRODUCT_CREATE", "PRODUCT_UPDATE", "PRODUCT_DELETE",
                "ORDER_VIEW", "ORDER_CREATE", "ORDER_UPDATE", "ORDER_CANCEL",
                "INVENTORY_VIEW", "INVENTORY_IMPORT", "INVENTORY_UPDATE",
                "REPORT_VIEW", "REPORT_EXPORT",
                "TABLE_VIEW", "TABLE_CREATE", "TABLE_UPDATE", "TABLE_DELETE"
        ));

        rolePermissions.put(UserRole.CASHIER.name(), Arrays.asList(
                "PRODUCT_VIEW",
                "ORDER_VIEW", "ORDER_CREATE", "ORDER_UPDATE", "ORDER_CANCEL",
                "INVENTORY_VIEW",
                "REPORT_VIEW",
                "TABLE_VIEW"
        ));

        rolePermissions.put(UserRole.KITCHEN.name(), Arrays.asList(
                "PRODUCT_VIEW",
                "ORDER_VIEW", "ORDER_UPDATE",
                "TABLE_VIEW"
        ));

        for (Map.Entry<String, List<String>> entry : rolePermissions.entrySet()) {
            Role role = roleRepository.findByName(entry.getKey())
                    .orElse(null);

            if (role != null) {
                for (String permissionName : entry.getValue()) {
                    Permission permission = permissionRepository.findByName(permissionName)
                            .orElse(null);

                    if (permission != null) {
                        // Check if mapping already exists
                        boolean exists = roleHasPermissionRepository.existsByRoleAndPermission(role, permission);

                        if (!exists) {
                            RoleHasPermission mapping = RoleHasPermission.builder()
                                    .role(role)
                                    .permission(permission)
                                    .build();

                            roleHasPermissionRepository.save(mapping);
                            log.info("Permission assigned: {} -> {}", role.getName(), permission.getName());
                        }
                    }
                }
            }
        }
    }
}

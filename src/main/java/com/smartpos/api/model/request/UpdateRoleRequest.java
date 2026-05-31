package com.smartpos.api.model.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateRoleRequest {

    @NotBlank(message = "Role name cannot be blank")
    private String name;

    @NotBlank(message = "Role description cannot be blank")
    private String description;

    @NotEmpty(message = "Permission IDs cannot be empty")
    private Long[] permissionIds;

    private boolean isActive;
}

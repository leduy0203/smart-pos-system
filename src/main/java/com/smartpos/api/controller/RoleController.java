package com.smartpos.api.controller;

import com.smartpos.api.model.request.UpdateRoleRequest;
import com.smartpos.api.model.response.ResponseData;
import com.smartpos.api.model.response.RoleResponse;
import com.smartpos.api.service.RoleService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@Slf4j(topic = "ROLE-CONTROLLER")
@RequestMapping("/api/v1")
public class RoleController {

    private final RoleService roleService;
    @PutMapping("/roles/{id}")
    @Operation(summary = "Update Role", description = "Update role by id")
    public ResponseData<RoleResponse> updateRole(@PathVariable Long id ,
                                                 @Valid @RequestBody UpdateRoleRequest request){
        log.info("Received request to update role with id: {}", id);
        RoleResponse roleResponse = this.roleService.updateRole(id , request);
        return ResponseData.<RoleResponse>builder()
                .code(200)
                .message("Role updated successfully")
                .data(roleResponse)
                .build();
    }
}

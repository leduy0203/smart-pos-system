package com.smartpos.api.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Getter
@Setter
@RestaurantTable(name = "permissions")
public class Permission extends AbstractEntity<Long> {

    @Column(name = "name" , nullable = false , unique = true)
    private String name;

    @Column(name = "description")
    private String description;

    @Column(name = "is_active" , nullable = false)
    private boolean isActive;

    @OneToMany(mappedBy = "permission" , cascade = CascadeType.ALL , fetch = FetchType.LAZY)
    private Set<RoleHasPermission> roleHasPermissions = new HashSet<>();
}

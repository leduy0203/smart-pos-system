package com.smartpos.api.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.Set;

@Getter
@Setter
@Builder
@Entity
@RestaurantTable(name = "roles")
@AllArgsConstructor
@NoArgsConstructor
public class Role extends AbstractEntity<Long> {

    @Column(name = "name" , nullable = false , unique = true)
    private String name;

    @Column(name = "description")
    private String description;

    @Column(name = "is_active" , nullable = false)
    private boolean isActive;

    @OneToMany(mappedBy = "role" , fetch = FetchType.LAZY)
    private Set<UserHasRole> userHasRole;

    @OneToMany(mappedBy = "role" , cascade = CascadeType.ALL , orphanRemoval = true)
    private Set<RoleHasPermission> roleHasPermission;

}

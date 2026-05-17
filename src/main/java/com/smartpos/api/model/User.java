package com.smartpos.api.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@RestaurantTable(name = "users")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User extends AbstractEntity<Long> {

    @Column(name = "username" , unique = true , nullable = false)
    private String userName;

    @Column(name = "password" , nullable = false)
    private String password;

    @Column(name = "full_name" )
    private String fullName;

    @Column(name = "email" , unique = true , nullable = false)
    private String email;

    @Column(name = "phone_number" , unique = true , nullable = false)
    private String phoneNumber;

    @Column(name = "is_active" , nullable = false)
    private boolean isActive;

    @OneToMany(mappedBy = "user" , orphanRemoval = true , cascade = CascadeType.ALL)
    private Set<UserHasRole> userHasRoles = new HashSet<>();

}

package com.smartpos.api.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "users" , uniqueConstraints = {
        @UniqueConstraint(
                name = "uk_username",
                columnNames = "username"
        ),
        @UniqueConstraint(
                name = "uk_email",
                columnNames = "email"
        ),
        @UniqueConstraint(
                name = "uk_phone_number",
                columnNames = "phone_number"
        )
})
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
    private boolean active;

    @OneToMany(mappedBy = "user" , orphanRemoval = true , cascade = CascadeType.ALL)
    private Set<UserHasRole> userHasRoles = new HashSet<>();
}

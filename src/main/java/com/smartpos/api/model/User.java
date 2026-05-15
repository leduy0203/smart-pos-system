package com.smartpos.api.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.*;

@Entity
@Table(name = "users")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User extends AbstractEntity<Long> {

    private String userName;

    private String password;

    private String fullName;

    private String email;

    private String phoneNumber;
}

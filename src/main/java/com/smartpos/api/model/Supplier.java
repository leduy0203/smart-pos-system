package com.smartpos.api.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.*;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(
        name = "suppliers",
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "uk_supplier_name",
                        columnNames = "name"
                ),
                @UniqueConstraint(
                        name = "uk_supplier_phone",
                        columnNames = "phone"
                )
        }
)
public class Supplier extends AbstractEntity<Long> {

    @Column(name = "name", nullable = false , unique = true)
    private String name;

    @Column(name = "phone", nullable = false , unique = true)
    private String phone;

    @Column(name = "address", nullable = false)
    private String address;

    @Column(name = "is_active", nullable = false)
    private boolean active = true;
}

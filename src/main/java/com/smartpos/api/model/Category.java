package com.smartpos.api.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Table(name = "categories")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Category extends AbstractEntity<Long> {

    @Column(name = "name" , nullable = false , unique = true)
    private String name;

    @Column(name = "description")
    private String description;

    @OneToMany(mappedBy = "category" , fetch = FetchType.LAZY)
    private List<Product> products;

    @Column(name = "is_active" , nullable = false)
    private boolean active = true;

}

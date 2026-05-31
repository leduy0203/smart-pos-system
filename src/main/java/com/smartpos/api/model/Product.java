package com.smartpos.api.model;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.util.List;

@Entity
@Table(name = "products")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Product extends AbstractEntity<Long> {

    @Column(name = "name" , nullable = false)
    private String name;

    @Column(name = "description")
    private String description;

    @Column(name = "image_url")
    private String imageUrl;

    @Column(name = "selling_price" , nullable = false)
    private BigDecimal sellingPrice;

    @Column(name = "cost_price" , nullable = false)
    private BigDecimal costPrice;

    @Column(name = "target_food_cost_percent")
    private Double targetFoodCostPercent;

    @Column(name = "is_available" , nullable = false)
    private boolean isAvailable;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;

    @OneToMany(mappedBy = "product")
    private List<OrderItem> orderItems;
}

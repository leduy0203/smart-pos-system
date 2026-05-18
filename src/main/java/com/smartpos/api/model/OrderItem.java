package com.smartpos.api.model;

import com.smartpos.api.common.OrderItemStatus;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "order_items")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderItem extends AbstractEntity<Long> {

    @Column(name = "product_name" , nullable = false)
    private String productName;

    @Column(name = "unit_price" , nullable = false)
    private Double unitPrice;

    @Column(name = "cost_price" , nullable = false)
    private Double costPrice;

    @Column(name = "quantity" , nullable = false)
    private Integer quantity;

    @Column(name = "sub_total" , nullable = false)
    private Double subTotal;

    @Column(name = "status" , nullable = false)
    private OrderItemStatus status;

    @ManyToOne
    @JoinColumn(name = "order_id", nullable = false)
    private Order order;

    @ManyToOne
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;
}

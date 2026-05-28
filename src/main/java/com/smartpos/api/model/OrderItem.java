package com.smartpos.api.model;

import com.smartpos.api.common.OrderItemStatus;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

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
    private BigDecimal unitPrice;

    @Column(name = "cost_price" , nullable = false)
    private BigDecimal costPrice;

    @Column(name = "quantity" , nullable = false)
    private Integer quantity;

    @Column(name = "sub_total" , nullable = false)
    private BigDecimal subTotal;

    @Column(name = "status" , nullable = false)
    @Enumerated(EnumType.STRING)
    private OrderItemStatus status;

    @Column(name = "note", length = 255)
    private String note;

    @ManyToOne
    @JoinColumn(name = "order_id", nullable = false)
    private Order order;

    @ManyToOne
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;
}

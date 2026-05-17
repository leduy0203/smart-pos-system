package com.smartpos.api.model;


import com.smartpos.api.common.OrderStatus;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.*;

import java.util.List;

@Entity
@Table(name = "orders")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Order extends AbstractEntity<Long> {

    @Column(name = "order_code" , nullable = false)
    private String orderCode;

    @Column(name = "subtotal" , nullable = false)
    private Double subTotal;

    @Column(name = "total_price" , nullable = false)
    private Double totalPrice;

    @Column(name = "status" , nullable = false)
    private OrderStatus status;

    @OneToMany(mappedBy = "order")
    private List<OrderItem> orderItems;
}

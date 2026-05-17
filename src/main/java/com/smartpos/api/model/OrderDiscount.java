package com.smartpos.api.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.*;

@Entity
@Table(name = "order_discounts")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderDiscount extends AbstractEntity<Long> {
    private Long orderId;
    private Long discountId;
}

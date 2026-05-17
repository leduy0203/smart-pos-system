package com.smartpos.api.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "discounts")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Discounts extends AbstractEntity<Long> {

    @Column(name = "name" , nullable = false)
    private String name;

    @Column(name = "type" , nullable = false)
    private String type;

    @Column(name = "value" , nullable = false)
    private Double value;

    @Column(name = "start_date" , nullable = false)
    private LocalDateTime startDate;

    @Column(name = "end_date" , nullable = false)
    private LocalDateTime endDate;

    @Column(name = "is_active" , nullable = false)
    private boolean isActive;
}

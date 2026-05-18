package com.smartpos.api.model;


import com.smartpos.api.common.TableStatus;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.*;

@Entity
@Table(name = "restaurant_tables")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RestaurantTable extends AbstractEntity<Long> {

    @Column(name = "table_number" , nullable = false , unique = true)
    private String tableNumber;

    @Column(name = "capacity" , nullable = false)
    private int capacity;

    @Column(name = "status" , nullable = false)
    private TableStatus status;
}

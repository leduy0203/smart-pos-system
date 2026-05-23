package com.smartpos.api.model;


import com.smartpos.api.common.TableStatus;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.*;

@Entity
@Table(
        name = "restaurant_tables",
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "uk_table_number",
                        columnNames = "table_number"
                )
        }
)
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

    @Column(name = "is_active" , nullable = false)
    private boolean active;

}

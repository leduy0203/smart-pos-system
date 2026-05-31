package com.smartpos.api.repository;

import com.smartpos.api.model.RestaurantTable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TableRepository extends JpaRepository<RestaurantTable, Long> {
    Optional<RestaurantTable> findByTableNumber(String tableNumber);

    boolean existsByTableNumber(String tableNumber);

    boolean existsByTableNumberAndIdNot(String tableNumber, Long id);

    Optional<RestaurantTable> findByIdAndActiveTrue(Long id);
}



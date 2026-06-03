package com.smartpos.api.repository;

import com.smartpos.api.model.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OderItemRepository extends JpaRepository<OrderItem , Long> {
}

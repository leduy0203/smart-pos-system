package com.smartpos.api.repository;

import com.smartpos.api.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {
    boolean existsByName(String name);

    Optional<Category> findByIdAndActiveTrue(Long id);

    boolean existsByNameAndIdNot(String name, Long id);

}

package com.smartpos.api.repository;

import com.smartpos.api.model.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    Optional<Product> findByIdAndIsAvailableTrue(Long id);

    Page<Product> findByIsAvailableTrue(Pageable pageable);

    @Query("""
    select p
    from Product p
    where p.isAvailable = true
    and ( :categoryId is null or p.category.id = :categoryId)
    and ( :keyword is null or lower(p.name) like :keyword)
""")
    Page<Product> search(
            @Param("keyword") String keyword,
            @Param("categoryId") Long categoryId,
            Pageable pageable
    );
}

package com.smartpos.api.repository;

import com.smartpos.api.model.Supplier;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SupplierRepository extends JpaRepository<Supplier, Long> {
     boolean existsByName(String name);
     boolean existsByPhone(String phone);

     Optional<Supplier> findByIdAndActiveTrue(Long id);;

     boolean existsByNameAndIdNot(String name, Long id);

     boolean existsByPhoneAndIdNot(String phone, Long id);

     @Query("SELECT s " +
             "FROM Supplier s " +
             "WHERE s.active = true AND " +
             "LOWER(s.name) LIKE :searchKeyword OR " +
             "LOWER(s.phone) LIKE :searchKeyword OR " +
             "LOWER(s.address) LIKE :searchKeyword ")
     Page<Supplier> search(String searchKeyword, Pageable pageable);

     Page<Supplier> findByActiveTrue(Pageable pageable);
}

package com.smartpos.api.repository;

import com.smartpos.api.model.Supplier;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SupplierRepository extends JpaRepository<Supplier, Long> {
     boolean existsByName(String name);
     boolean existsByPhone(String phone);

     Optional<Supplier> findByIdAndActiveTrue(Long id);;

     boolean existsByNameAndIdNot(String name, Long id);

     boolean existsByPhoneAndIdNot(String phone, Long id);
}

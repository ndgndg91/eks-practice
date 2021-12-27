package com.ndgndg91.product.repository;

import com.ndgndg91.product.domain.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product, Long> {

    @Query("SELECT DISTINCT p FROM Product AS p JOIN FETCH p.variants AS pv JOIN FETCH pv.inventory WHERE p.id = :id")
    Optional<Product> findById(final long id);
}

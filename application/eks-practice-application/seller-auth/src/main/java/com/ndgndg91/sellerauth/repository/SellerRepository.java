package com.ndgndg91.sellerauth.repository;

import com.ndgndg91.sellerauth.domain.Seller;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SellerRepository extends JpaRepository<Seller, Long> {

    Optional<Seller> findByIdentifier(String identifier);
}

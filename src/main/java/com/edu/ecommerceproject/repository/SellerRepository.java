package com.edu.ecommerceproject.repository;


import com.edu.ecommerceproject.models.entities.Seller;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SellerRepository extends JpaRepository<Seller,Integer> {
    Optional<Seller> findSellerByEmail(String email);
}

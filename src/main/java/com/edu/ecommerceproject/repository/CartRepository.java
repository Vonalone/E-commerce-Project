package com.edu.ecommerceproject.repository;

import com.edu.ecommerceproject.models.entities.Cart;
import com.edu.ecommerceproject.models.entities.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CartRepository extends JpaRepository<Cart,Integer> {
    Optional<Cart> findByCustomer(Customer customer);
}

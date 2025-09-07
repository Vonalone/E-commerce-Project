package com.edu.ecommerceproject.repository;

import com.edu.ecommerceproject.models.entities.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Integer> {
}

package com.edu.ecommerceproject.repository;

import com.edu.ecommerceproject.models.entities.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CustomerRepository extends JpaRepository<Customer,Integer> {
    Optional<Customer> findCustomerByEmail(String Email);
}

package com.edu.ecommerceproject.service.impl;

import com.edu.ecommerceproject.exception.CustomerException;
import com.edu.ecommerceproject.models.entities.Customer;
import com.edu.ecommerceproject.repository.CustomerRepository;
import com.edu.ecommerceproject.service.IAuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class AuthServiceImpl implements IAuthService {
    private final CustomerRepository customerRepository;

    @Autowired
    public AuthServiceImpl(CustomerRepository customerRepository){
        this.customerRepository = customerRepository;
    }

    @Override
    public Customer registerCustomer(Customer customer) {
        customer.setCreatedOn(LocalDateTime.now());
        Optional<Customer> customerExist = customerRepository.findCustomerByEmail(customer.getEmail());
        if(customerExist.isPresent())
            throw new CustomerException("Customer already exists. Please try to login with your email ");

        return customerRepository.save(customer);


    }
}

package com.edu.ecommerceproject.controller;
import com.edu.ecommerceproject.models.entities.Customer;
import com.edu.ecommerceproject.service.IAuthService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthController {
    private final IAuthService authService;

    @Autowired
    public AuthController(IAuthService authService1){
        this.authService = authService1;
    }

    @PostMapping(value = "/register/customer",consumes = "application/json")
    public ResponseEntity<Customer> registerAccount(@Valid @RequestBody Customer customer){
     return new ResponseEntity<>(authService.registerCustomer(customer), HttpStatus.CREATED);

    }
}

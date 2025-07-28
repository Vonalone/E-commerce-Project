package com.edu.ecommerceproject.controller;
import com.edu.ecommerceproject.dto.request.CustomerDTO;
import com.edu.ecommerceproject.dto.request.SessionDTO;
import com.edu.ecommerceproject.models.entities.Customer;
import com.edu.ecommerceproject.models.entities.Session;
import com.edu.ecommerceproject.service.IAuthService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
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


    @PostMapping(value = "/login/customer",consumes = "application/json")
    public ResponseEntity<Session> loginCustomer(@Valid @RequestBody CustomerDTO customerDTO){
        return  new ResponseEntity<>(authService.loginCustomer(customerDTO),HttpStatus.ACCEPTED);
    }

   @PostMapping(value = "/logout/customer" ,consumes = "application/json")
    public ResponseEntity<SessionDTO> logoutCustomer(@Valid @RequestBody SessionDTO sessionDTO){
        return new ResponseEntity<>(authService.logoutCustomer(sessionDTO),HttpStatus.ACCEPTED);
   }


}

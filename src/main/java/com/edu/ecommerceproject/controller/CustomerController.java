package com.edu.ecommerceproject.controller;

import com.edu.ecommerceproject.dto.request.CustomerDTO;
import com.edu.ecommerceproject.dto.request.SessionDTO;
import com.edu.ecommerceproject.dto.request.UpdatePasswordDTO;
import com.edu.ecommerceproject.dto.response.CustomerResponse;
import com.edu.ecommerceproject.models.entities.Customer;
import com.edu.ecommerceproject.service.ICustomerService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/customers")
@RequiredArgsConstructor
public class CustomerController {
    private final ICustomerService customerService;


    @GetMapping
    public ResponseEntity<List<CustomerResponse>> getAllCustomers(@RequestHeader("token") String token){
        return new ResponseEntity<>(customerService.getAllCustomers(token), HttpStatus.OK);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<CustomerResponse> getCustomer(@PathVariable Integer id){
        return new ResponseEntity<>(customerService.getCustomer(id), HttpStatus.OK);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<SessionDTO> deleteCustomer(@PathVariable Integer id,@RequestHeader("token") String token){
        return new ResponseEntity<>(customerService.deleteCustomer(id,token),HttpStatus.OK);
    }
    @PutMapping(value = "/update")
    public ResponseEntity<CustomerResponse> updateCustomer(@Valid @RequestBody Customer customer,@RequestHeader("token") String token){
        return new ResponseEntity<>(customerService.updateCustomer(customer,token),HttpStatus.OK);
    }

    @PutMapping(value = "/update/{id}/contact")
    public ResponseEntity<CustomerResponse> updateCustomerEmailOrMobileNumber(@PathVariable Integer id,@Valid @RequestBody CustomerDTO customerDTO,@RequestHeader("token")  String token){
        return new ResponseEntity<>(customerService.updateCustomerEmailOrMobileNumber(id,customerDTO,token),HttpStatus.OK);
    }
    @PutMapping(value = "/update/{id}/password")
    public ResponseEntity<CustomerResponse> updateCustomerPassword(@PathVariable Integer id, @Valid @RequestBody UpdatePasswordDTO updatePasswordDTO,@RequestHeader("token") String token){
        return new ResponseEntity<>(customerService.updateCustomerPassword(id,updatePasswordDTO,token),HttpStatus.OK);
    }
}

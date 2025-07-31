package com.edu.ecommerceproject.service;

import com.edu.ecommerceproject.dto.request.CustomerDTO;
import com.edu.ecommerceproject.dto.request.SessionDTO;
import com.edu.ecommerceproject.dto.request.UpdatePasswordDTO;
import com.edu.ecommerceproject.dto.response.CustomerResponse;
import com.edu.ecommerceproject.models.entities.Customer;

import java.util.List;

public interface ICustomerService {
     List<CustomerResponse> getAllCustomers(String token);
     CustomerResponse getCustomer(Integer customerId);
     CustomerResponse updateCustomer(Customer customer,String token);
     CustomerResponse updateCustomerEmailOrMobileNumber(Integer id,CustomerDTO customerDTO,String token);
     CustomerResponse updateCustomerPassword(Integer id, UpdatePasswordDTO updatePasswordDTO, String token);
     SessionDTO deleteCustomer(Integer customerId,String token);
}

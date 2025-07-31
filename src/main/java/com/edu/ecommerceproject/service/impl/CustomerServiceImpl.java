package com.edu.ecommerceproject.service.impl;


import com.edu.ecommerceproject.dto.request.CustomerDTO;
import com.edu.ecommerceproject.dto.request.SessionDTO;
import com.edu.ecommerceproject.dto.request.UpdatePasswordDTO;
import com.edu.ecommerceproject.dto.response.CustomerResponse;
import com.edu.ecommerceproject.exception.CustomerNotFoundException;
import com.edu.ecommerceproject.exception.LoginException;
import com.edu.ecommerceproject.models.entities.Customer;
import com.edu.ecommerceproject.models.entities.Session;
import com.edu.ecommerceproject.repository.CustomerRepository;
import com.edu.ecommerceproject.repository.SessionRepository;
import com.edu.ecommerceproject.service.IAuthService;
import com.edu.ecommerceproject.service.ICustomerService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CustomerServiceImpl implements ICustomerService {
    private final CustomerRepository customerRepository;
    private final SessionRepository sessionRepository;
    private final IAuthService authService;
    public CustomerServiceImpl(CustomerRepository customerRepository1,SessionRepository sessionRepository1,IAuthService authService1){
      this.customerRepository = customerRepository1;
      this.sessionRepository = sessionRepository1;
      this.authService = authService1;
    }

    @Override
    public List<CustomerResponse> getAllCustomers(String token) {

        if(!token.contains("seller"))
            throw  new LoginException("Access denied: Only sellers can view all customers.");

        authService.checkStatusToken(token);
        List<Customer> customers = customerRepository.findAll();
        if(customers.isEmpty())
            throw new CustomerNotFoundException("Not Found Any Customer");
        List<CustomerResponse> customerResponses = new ArrayList<>();
        for(Customer customer : customers){
           customerResponses.add(mapToCustomerResponse(customer)) ;
        }

        return customerResponses;
    }

    @Override
 public CustomerResponse getCustomer(Integer customerId) {
        Optional<Customer> customerOptional = customerRepository.findById(customerId);
        if(customerOptional.isEmpty())
            throw new CustomerNotFoundException("Not Found Customer with this email");
           Customer customer = customerOptional.get();
        return mapToCustomerResponse(customer);

    }

    @Override
    public CustomerResponse updateCustomer(Customer customer, String token) {
         if(!token.contains("customer"))
             throw new LoginException("Access Denied ,Just Customer Can Update Their Data");
         Optional<Customer> customerOptional = customerRepository.findById(customer.getCustomerId());
         if(customerOptional.isEmpty())
            throw new CustomerNotFoundException("Not Found Customer With This ID");
         authService.checkStatusToken(token);
         Optional<Session> sessionOptional = sessionRepository.findByToken(token);
         if(sessionOptional.isEmpty())
             throw new LoginException("session token invalid,please log in first");
         Customer customer1 = customerOptional.get();
         Session session = sessionOptional.get();
         if(!(customer1.getCustomerId().equals(session.getUserId()) && customer1.getPassword().equals(customer.getPassword())))
             throw new LoginException("Access Denied ,You can only update your own account with valid password.");

         if(customer.getFirstName() != null) {
             customer1.setFirstName(customer.getFirstName());
         }
         if(customer.getLastName() != null){
             customer1.setLastName(customer.getLastName());
         }
         if(customer.getEmail() != null){
             customer1.setEmail(customer.getEmail());
         }
         if(customer.getMobileNo() != null){
             customer1.setMobileNo(customer.getMobileNo());
         }

         CustomerResponse customerResponse = mapToCustomerResponse(customer1);
         customerRepository.save(customer1);
        return customerResponse;
    }

    @Override
    public CustomerResponse updateCustomerEmailOrMobileNumber(Integer id,CustomerDTO customerDTO, String token) {
       if(!token.contains("customer"))
           throw new LoginException("Access Denied: just customer can update their data");
       authService.checkStatusToken(token);
       Optional<Customer> customerOptional = customerRepository.findById(id);
       if(customerOptional.isEmpty())
           throw new CustomerNotFoundException("customer with this id not found");

       Customer customer = customerOptional.get();
       Optional<Session> sessionOptional = sessionRepository.findByToken(token);
       if(sessionOptional.isEmpty())
           throw new LoginException("Invalid Session Token, please log in first");
       Session session = sessionOptional.get();
       if(!(customer.getCustomerId().equals(session.getUserId()) && customer.getPassword().equals(customerDTO.getPassword())))
           throw new LoginException("Access Denied,You can only update your own account with valid password");

       if(customerDTO.getEmail() != null){
           customer.setEmail(customerDTO.getEmail());
       }
       if(customerDTO.getMobileNo() !=null){
           customer.setMobileNo(customerDTO.getMobileNo());
       }
       CustomerResponse customerResponse = mapToCustomerResponse(customer);
       customerRepository.save(customer);
        return customerResponse;
    }

    @Override
    public CustomerResponse updateCustomerPassword(Integer id, UpdatePasswordDTO updatePasswordDTO, String token) {
        if(!token.contains("customer"))
            throw new LoginException("Access Denied: just customer can update their data");
        authService.checkStatusToken(token);
        Optional<Customer> customerOptional = customerRepository.findById(id);
        if(customerOptional.isEmpty())
            throw new CustomerNotFoundException("customer with this id not found");

        Customer customer = customerOptional.get();
        Optional<Session> sessionOptional = sessionRepository.findByToken(token);
        if(sessionOptional.isEmpty())
            throw new LoginException("Invalid Session Token, please log in first");
        Session session = sessionOptional.get();
        if(!(customer.getCustomerId().equals(session.getUserId()) && customer.getPassword().equals(updatePasswordDTO.getOldPassword())))
            throw new LoginException("Access Denied,You can only update your own account with valid password");
        if(!customer.getEmail().equals(updatePasswordDTO.getEmail()) || !customer.getMobileNo().equals(updatePasswordDTO.getMobileNo()))
            throw new LoginException("Access Denied: Email or mobile number doesn't match our records");
        if(updatePasswordDTO.getNewPassword()!= null){
            customer.setPassword(updatePasswordDTO.getNewPassword());
        }

        CustomerResponse customerResponse = mapToCustomerResponse(customer);
        customerRepository.save(customer);
        return customerResponse;
    }


    @Override
    public SessionDTO deleteCustomer(Integer customerId,String token) {
        if(!token.contains("customer"))
            throw new LoginException("Access Denied ,Just Customer Can Delete his Account");
        authService.checkStatusToken(token);
        Optional<Customer> customerOptional = customerRepository.findById(customerId);
        if(customerOptional.isEmpty())
            throw new CustomerNotFoundException("customer with this id not exist ");
       Optional<Session> sessionOptional = sessionRepository.findByToken(token);

       if (sessionOptional.isEmpty())
           throw  new LoginException("this customer not have any session");
       Session sessionExist = sessionOptional.get();
       if(!sessionExist.getUserId().equals(customerId))
          throw new LoginException("Access Denied: You can only delete your own account.");
        Customer customer = customerOptional.get();
        customerRepository.delete(customer);
        sessionRepository.delete(sessionExist);

        return new SessionDTO(token,"Account has been deleted successfully.");
    }

    public CustomerResponse mapToCustomerResponse(Customer customer){
        return new CustomerResponse(
                customer.getFirstName(),
                customer.getLastName(),
                customer.getMobileNo(),
                customer.getEmail()
        );

    }
}

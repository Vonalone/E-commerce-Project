package com.edu.ecommerceproject.service.impl;

import com.edu.ecommerceproject.dto.request.CustomerDTO;
import com.edu.ecommerceproject.dto.request.SessionDTO;
import com.edu.ecommerceproject.exception.CustomerException;
import com.edu.ecommerceproject.exception.CustomerNotFoundException;
import com.edu.ecommerceproject.exception.LoginException;
import com.edu.ecommerceproject.models.entities.Customer;
import com.edu.ecommerceproject.models.entities.Session;
import com.edu.ecommerceproject.repository.CustomerRepository;
import com.edu.ecommerceproject.repository.SessionRepository;
import com.edu.ecommerceproject.service.IAuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Service
public class AuthServiceImpl implements IAuthService {
    private final CustomerRepository customerRepository;
    private final SessionRepository sessionRepository;

    @Autowired
    public AuthServiceImpl(CustomerRepository customerRepository,SessionRepository sessionRepository){
        this.customerRepository = customerRepository;
        this.sessionRepository = sessionRepository;
    }



    @Override
    public Customer registerCustomer(Customer customer) {
        customer.setCreatedOn(LocalDateTime.now());
        Optional<Customer> customerExist = customerRepository.findCustomerByEmail(customer.getEmail());
        if(customerExist.isPresent())
            throw new CustomerException("Customer already exists. Please try to login with your email ");

        return customerRepository.save(customer);


    }

    @Override
    public Session loginCustomer(CustomerDTO customerDTO) {
         Optional<Customer> customerRes = customerRepository.findCustomerByEmail(customerDTO.getEmail());
         if(customerRes.isEmpty())
             throw new CustomerNotFoundException("Customer Not Exist ");

         Customer customerExist = customerRes.get();
         Optional<Session> sessionRes = sessionRepository.findByUserId(customerExist.getCustomerId());

         if(sessionRes.isPresent()) {
             Session userSession = sessionRes.get();
             if (userSession.getDateEndSession().isBefore(LocalDateTime.now())) {
                 sessionRepository.delete(userSession);
             } else {
                 throw new LoginException("User already logged in");
             }
         }
             if(customerExist.getPassword().equals(customerDTO.getPassword())){

                Session newSession = new Session();
                 newSession.setUserId(customerExist.getCustomerId());
                 newSession.setUserType("customer");
                 newSession.setToken("customer_"+ UUID.randomUUID().toString().split("-")[0]);
                 newSession.setDateStartSession(LocalDateTime.now());
                 newSession.setDateEndSession(LocalDateTime.now().plusHours(1));

                 return sessionRepository.save(newSession);

         }else{
                 throw new LoginException("password incorrect,Try again");
             }
    }

    @Override
    public SessionDTO logoutCustomer(SessionDTO sessionDTO) {
        Optional<Session> sessionRes = sessionRepository.findByToken(sessionDTO.getToken());

        if(sessionRes.isEmpty())
            throw new LoginException("Invalid session token, please login first");

        Session session = sessionRes.get();
        if(session.getDateEndSession().isBefore(LocalDateTime.now())){
            sessionRepository.delete(session);
            throw new LoginException("Session already expired. Please login again.");
        }
        sessionRepository.delete(session);
        SessionDTO responseSession = new SessionDTO();
        responseSession.setMessage("Logout successful");
        responseSession.setToken(sessionDTO.getToken());
        return responseSession;
    }
}

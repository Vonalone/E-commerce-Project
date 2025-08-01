package com.edu.ecommerceproject.service.impl;

import com.edu.ecommerceproject.dto.request.CustomerDTO;
import com.edu.ecommerceproject.dto.request.SellerDTO;
import com.edu.ecommerceproject.dto.request.SessionDTO;
import com.edu.ecommerceproject.exception.*;
import com.edu.ecommerceproject.models.entities.Customer;
import com.edu.ecommerceproject.models.entities.Seller;
import com.edu.ecommerceproject.models.entities.Session;
import com.edu.ecommerceproject.repository.CustomerRepository;
import com.edu.ecommerceproject.repository.SellerRepository;
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
    private final SellerRepository sellerRepository;

    @Autowired
    public AuthServiceImpl(CustomerRepository customerRepository,SessionRepository sessionRepository,SellerRepository sellerRepository){
        this.customerRepository = customerRepository;
        this.sessionRepository = sessionRepository;
        this.sellerRepository = sellerRepository;
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

    // handle seller login system
    @Override
    public Seller registerSeller(Seller seller) {
        Optional<Seller> sellerRes = sellerRepository.findSellerByEmail(seller.getEmail());
        if(sellerRes.isPresent())
            throw new SellerException("Seller already exists ,please try to login  with your email");
        return sellerRepository.save(seller);
    }

    @Override
    public Session loginSeller(SellerDTO sellerDTO) {
        Optional<Seller> sellerOptional = sellerRepository.findSellerByEmail(sellerDTO.getEmail());
        if(sellerOptional.isEmpty())
            throw new SellerNotFoundException("Seller Not Exist");
        Seller seller = sellerOptional.get();
        Optional<Session> sellerSession = sessionRepository.findByUserId(seller.getSellerId());
        if(sellerSession.isPresent()) {
            Session userSession = sellerSession.get();
            if (userSession.getDateEndSession().isBefore(LocalDateTime.now())) {
                sessionRepository.delete(userSession);

            } else {
                throw new LoginException("Seller already logged in");
            }
        }
            if(sellerDTO.getPassword().equals(seller.getPassword())){
                Session newSession = new Session();
                newSession.setUserId(seller.getSellerId());
                newSession.setUserType("seller");
                newSession.setDateStartSession(LocalDateTime.now());
                newSession.setDateEndSession(LocalDateTime.now().plusHours(1));
                newSession.setToken("seller_"+UUID.randomUUID().toString().split("-")[0]);
                return sessionRepository.save(newSession);

            }else{
                throw new LoginException("Incorrect Password ,Try Again");
            }
        }




    @Override
    public SessionDTO logoutSeller(SessionDTO sessionDTO) {
        Optional<Session> sessionOptional = sessionRepository.findByToken(sessionDTO.getToken());
        if(sessionOptional.isEmpty())
            throw new LoginException("Invalid session token, please login first");

        Session userSession = sessionOptional.get();
        if(userSession.getDateEndSession().isBefore(LocalDateTime.now())) {
            sessionRepository.delete(userSession);
            throw new LoginException("Session is Expired , Please Login Again");
        }
        sessionRepository.delete(userSession);
        SessionDTO sessionDTOResponse = new SessionDTO();
        sessionDTOResponse.setToken(sessionDTO.getToken());
        sessionDTOResponse.setMessage("Logout successful");
        return sessionDTOResponse;
    }

    @Override
    public void checkStatusToken(String token) {
      Optional<Session> session = sessionRepository.findByToken(token);
      if(session.isEmpty())
          throw new LoginException("Not Found session , Please log in first");

      Session sessionExist = session.get();
      if((sessionExist.getDateEndSession().isBefore(LocalDateTime.now()))){
          sessionRepository.delete(sessionExist);
          throw new LoginException("Session is Expired,Please log in again");
      }

    }

}

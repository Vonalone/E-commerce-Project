package com.edu.ecommerceproject.service;

import com.edu.ecommerceproject.dto.request.CustomerDTO;
import com.edu.ecommerceproject.dto.request.SellerDTO;
import com.edu.ecommerceproject.dto.request.SessionDTO;
import com.edu.ecommerceproject.models.entities.Customer;
import com.edu.ecommerceproject.models.entities.Seller;
import com.edu.ecommerceproject.models.entities.Session;

public interface IAuthService {
//    handle customer login system
    Customer registerCustomer(Customer customer);
    Session loginCustomer(CustomerDTO customerDTO);
    SessionDTO logoutCustomer(SessionDTO sessionDTO);

// handle seller login system

    Seller registerSeller(Seller seller);
    Session loginSeller(SellerDTO sellerDTO);
    SessionDTO logoutSeller(SessionDTO sessionDTO);

}

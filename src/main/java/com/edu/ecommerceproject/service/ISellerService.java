package com.edu.ecommerceproject.service;

import com.edu.ecommerceproject.dto.request.SellerDTO;
import com.edu.ecommerceproject.dto.request.SessionDTO;
import com.edu.ecommerceproject.dto.request.UpdatePasswordDTO;
import com.edu.ecommerceproject.dto.response.SellerResponse;
import com.edu.ecommerceproject.models.entities.Seller;
import java.util.List;

public interface ISellerService {
    List<SellerResponse> getAllSellers();
    SellerResponse getSeller(Integer id);
    SellerResponse updateSeller(Seller seller,String token);
    SellerResponse updateSellerEmailOrMobileNumber(Integer id,SellerDTO sellerDTO,String token);
    SellerResponse updateSellerPassword(Integer id,UpdatePasswordDTO updatePasswordDTO,String token);
    SessionDTO deleteSeller(Integer id, String token);
}

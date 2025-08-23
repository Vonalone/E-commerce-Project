package com.edu.ecommerceproject.service.impl;

import com.edu.ecommerceproject.dto.request.SellerDTO;
import com.edu.ecommerceproject.dto.request.SessionDTO;
import com.edu.ecommerceproject.dto.request.UpdatePasswordDTO;
import com.edu.ecommerceproject.dto.response.SellerResponse;
import com.edu.ecommerceproject.exception.LoginException;
import com.edu.ecommerceproject.exception.SellerNotFoundException;
import com.edu.ecommerceproject.models.entities.Seller;
import com.edu.ecommerceproject.models.entities.Session;
import com.edu.ecommerceproject.repository.SellerRepository;
import com.edu.ecommerceproject.repository.SessionRepository;
import com.edu.ecommerceproject.service.IAuthService;
import com.edu.ecommerceproject.service.ISellerService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class SellerServiceImpl implements ISellerService {
    private final SellerRepository sellerRepository;
    private  final SessionRepository sessionRepository;
    private final IAuthService authService;


    @Override
    public List<SellerResponse> getAllSellers() {
        List<Seller> sellers = sellerRepository.findAll();
        if(sellers.isEmpty())
            throw new SellerNotFoundException("Not Found Any Seller");
        List<SellerResponse> sellerResponseList = new ArrayList<>();
        for(Seller seller:sellers){
            sellerResponseList.add(mapToSellerResponse(seller));
        }
        return sellerResponseList;
    }

    @Override
    public SellerResponse getSeller(Integer id) {
        Optional<Seller> sellerOptional = sellerRepository.findById(id);
        if(sellerOptional.isEmpty())
            throw new SellerNotFoundException("Seller with this id not exist");
        Seller seller = sellerOptional.get();
        return mapToSellerResponse(seller);
    }

    @Override
    public SellerResponse updateSeller(Seller seller, String token) {
        if(!token.contains("seller"))
            throw new LoginException("just sellers can update their accounts");
        authService.checkStatusToken(token);
        Optional<Seller> sellerOptional = sellerRepository.findSellerByEmail(seller.getEmail());
        if(sellerOptional.isEmpty())
            throw new SellerNotFoundException("seller with email not found ");
        Optional<Session> sessionOptional = sessionRepository.findByToken(token);
        if(sessionOptional.isEmpty())
            throw new LoginException("this seller not have any seller");
        Session session = sessionOptional.get();
        Seller seller1 = sellerOptional.get();
        
        if(!(session.getUserId().equals(seller1.getSellerId())) && seller.getPassword().equals(seller1.getPassword()))
            throw new LoginException("Access Denied ,You can only update your own account with valid password.");
        
        if(seller.getFirstname() != null) {
            seller1.setFirstname(seller.getFirstname());
        }
        if(seller.getLastname() != null) {
            seller1.setLastname(seller.getLastname());
        }
        if(seller.getEmail() != null){
        seller1.setEmail(seller.getEmail());
        }
        if(seller.getNumberPhone() != null) {
            seller1.setNumberPhone(seller.getNumberPhone());
        }
        sellerRepository.save(seller1);
        return mapToSellerResponse(seller1);
    }

    @Override
    public SellerResponse updateSellerEmailOrMobileNumber(Integer id, SellerDTO sellerDTO, String token) {
        if(!token.contains("seller"))
            throw new LoginException("Access Denied: just seller can update their data");
        authService.checkStatusToken(token);
        Optional<Seller> sellerOptional = sellerRepository.findById(id);
        if(sellerOptional.isEmpty())
            throw new SellerNotFoundException("seller with this id not found");

        Seller seller = sellerOptional.get();
        Optional<Session> sessionOptional = sessionRepository.findByToken(token);
        if(sessionOptional.isEmpty())
            throw new LoginException("Invalid Session Token, please log in first");
        Session session = sessionOptional.get();
        if(!(seller.getSellerId().equals(session.getUserId()) && seller.getPassword().equals(sellerDTO.getPassword())))
            throw new LoginException("Access Denied,You can only update your own account with valid password");

        if(sellerDTO.getEmail() != null){
            seller.setEmail(sellerDTO.getEmail());
        }
        if(sellerDTO.getNumberPhone() !=null){
            seller.setNumberPhone(sellerDTO.getNumberPhone());
        }
       SellerResponse sellerResponse = mapToSellerResponse(seller);
       sellerRepository.save(seller);
        return sellerResponse;
    }

    @Override
    public SellerResponse updateSellerPassword(Integer id, UpdatePasswordDTO updatePasswordDTO, String token) {
        if(!token.contains("seller"))
            throw new LoginException("Access Denied: just seller can update their data");
        authService.checkStatusToken(token);
        Optional<Seller> sellerOptional = sellerRepository.findById(id);
        if(sellerOptional.isEmpty())
            throw new SellerNotFoundException("seller with this id not found");

        Seller seller = sellerOptional.get();
        Optional<Session> sessionOptional = sessionRepository.findByToken(token);
        if(sessionOptional.isEmpty())
            throw new LoginException("Invalid Session Token, please log in first");
        Session session = sessionOptional.get();
        if(!(seller.getSellerId().equals(session.getUserId()) && seller.getPassword().equals(updatePasswordDTO.getOldPassword())))
            throw new LoginException("Access Denied,You can only update your own account with valid password");
        if(!seller.getEmail().equals(updatePasswordDTO.getEmail()) || !seller.getNumberPhone().equals(updatePasswordDTO.getMobileNo()))
            throw new LoginException("Access Denied: Email or mobile number doesn't match our records");
        if(updatePasswordDTO.getNewPassword()!= null){
            seller.setPassword(updatePasswordDTO.getNewPassword());
        }

        SellerResponse sellerResponse = mapToSellerResponse(seller);
        sellerRepository.save(seller);
        return sellerResponse;
    }

    @Override
    public SessionDTO deleteSeller(Integer id, String token) {
        if(!token.contains("seller"))
            throw new LoginException("just sellers can delete their account");
        authService.checkStatusToken(token);
    Optional<Seller> sellerOptional = sellerRepository.findById(id);
    if(sellerOptional.isEmpty())
       throw new SellerNotFoundException("seller with this id not found");
    Optional<Session> sessionOptional = sessionRepository.findByToken(token);
    if(sessionOptional.isEmpty())
        throw new LoginException("this seller don't have any session");
    Session session = sessionOptional.get();
    if(!session.getUserId().equals(id))
        throw new LoginException("Access Denied: You can only delete your own account.");
    Seller seller = sellerOptional.get();
    SessionDTO sessionDTO = new SessionDTO();
    sellerRepository.delete(seller);
    sessionRepository.delete(session);
    sessionDTO.setToken(token);
    sessionDTO.setMessage("Account has been deleted successfully.");
        return sessionDTO;
    }

    public SellerResponse mapToSellerResponse(Seller seller){
        return new SellerResponse(
                seller.getFirstname(),
                seller.getLastname(),
                seller.getEmail(),
                seller.getNumberPhone()
        );
    }
}

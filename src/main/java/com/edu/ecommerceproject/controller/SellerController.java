package com.edu.ecommerceproject.controller;

import com.edu.ecommerceproject.dto.request.SellerDTO;
import com.edu.ecommerceproject.dto.request.SessionDTO;
import com.edu.ecommerceproject.dto.request.UpdatePasswordDTO;
import com.edu.ecommerceproject.dto.response.SellerResponse;
import com.edu.ecommerceproject.models.entities.Seller;
import com.edu.ecommerceproject.service.ISellerService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/sellers")
public class SellerController {
    private final ISellerService sellerService;

  @GetMapping
  public ResponseEntity<List<SellerResponse>> getAllSellers(){
      return new ResponseEntity<>(sellerService.getAllSellers(), HttpStatus.OK);
  }
  @GetMapping(value = "/{id}")
    public ResponseEntity<SellerResponse> getSeller(@PathVariable Integer id){
      return new ResponseEntity<>(sellerService.getSeller(id),HttpStatus.OK);
  }
  @DeleteMapping(value = "/{id}")
    public ResponseEntity<SessionDTO> deleteSeller(@PathVariable Integer id ,@RequestHeader String token){
      return new ResponseEntity<>(sellerService.deleteSeller(id,token),HttpStatus.OK);
  }
  @PutMapping("/update")
    public ResponseEntity<SellerResponse> updateSeller(@Valid @RequestBody Seller seller,@RequestHeader String token){
      return new ResponseEntity<>(sellerService.updateSeller(seller,token),HttpStatus.OK);
  }
    @PutMapping(value = "/update/{id}/contact")
    public ResponseEntity<SellerResponse> updateSellerEmailOrMobileNumber(@PathVariable Integer id, @Valid @RequestBody SellerDTO sellerDTO, @RequestHeader("token")  String token){
        return new ResponseEntity<>(sellerService.updateSellerEmailOrMobileNumber(id,sellerDTO,token),HttpStatus.OK);
    }
    @PutMapping(value = "/update/{id}/password")
    public ResponseEntity<SellerResponse> updateSellerPassword(@PathVariable Integer id, @Valid @RequestBody UpdatePasswordDTO updatePasswordDTO, @RequestHeader("token") String token){
        return new ResponseEntity<>(sellerService.updateSellerPassword(id,updatePasswordDTO,token),HttpStatus.OK);
    }

}

package com.edu.ecommerceproject.controller;

import com.edu.ecommerceproject.dto.request.CartProductDTO;
import com.edu.ecommerceproject.models.entities.Cart;
import com.edu.ecommerceproject.service.ICartService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/cart")
public class CartController {
    private final ICartService cartService;

    @GetMapping
    public ResponseEntity<Cart> getCartProduct(@RequestHeader("token") String token){
        return new ResponseEntity<>(cartService.getCartProduct(token), HttpStatus.OK);
    }
    @PostMapping
    public ResponseEntity<Cart> addProductToCart(@Valid @RequestBody CartProductDTO productDTO,@RequestHeader("token") String token){
        return new ResponseEntity<>(cartService.addProductToCart(productDTO,token),HttpStatus.OK);
    }
    @DeleteMapping
    public ResponseEntity<Cart> deleteProductFromCart(@Valid @RequestBody CartProductDTO productDTO,@RequestHeader("token") String token){
        return new ResponseEntity<>(cartService.removeProductFromCart(productDTO,token),HttpStatus.OK);
    }
    @DeleteMapping("/clear")
    public ResponseEntity<Cart> clearCartProduct(@RequestHeader("token") String token){
        return new ResponseEntity<>(cartService.clearCart(token),HttpStatus.OK);
    }
}

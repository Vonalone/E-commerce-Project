package com.edu.ecommerceproject.service;

import com.edu.ecommerceproject.dto.request.CartProductDTO;
import com.edu.ecommerceproject.models.entities.Cart;

public interface ICartService {
    Cart getCartProduct(String token);
    Cart addProductToCart(CartProductDTO productDTO,String token);
    Cart removeProductFromCart(CartProductDTO productDTO,String token);
    Cart clearCart(String token);
}

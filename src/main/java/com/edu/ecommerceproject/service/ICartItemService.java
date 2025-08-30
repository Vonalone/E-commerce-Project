package com.edu.ecommerceproject.service;

import com.edu.ecommerceproject.dto.request.CartProductDTO;
import com.edu.ecommerceproject.models.entities.CartItem;

public interface ICartItemService {
    CartItem createItemForCart(CartProductDTO cartProductDTO);
}

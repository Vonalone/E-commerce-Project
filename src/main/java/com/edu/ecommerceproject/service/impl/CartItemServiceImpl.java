package com.edu.ecommerceproject.service.impl;

import com.edu.ecommerceproject.dto.request.CartProductDTO;
import com.edu.ecommerceproject.exception.ProductNotFoundException;
import com.edu.ecommerceproject.models.entities.CartItem;
import com.edu.ecommerceproject.models.entities.Product;
import com.edu.ecommerceproject.models.enums.ProductStatusEnum;
import com.edu.ecommerceproject.repository.ProductRepository;
import com.edu.ecommerceproject.service.ICartItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CartItemServiceImpl implements ICartItemService {
    private final ProductRepository productRepository;

    @Autowired
    public CartItemServiceImpl(ProductRepository productRepository){
        this.productRepository = productRepository;
    }

    @Override
    public CartItem createItemForCart(CartProductDTO cartProductDTO) {
        Optional<Product> productOptional = productRepository.findById(cartProductDTO.getProductId());
        if(productOptional.isEmpty())
            throw new ProductNotFoundException("not found product");
        Product product = productOptional.get();
        if(product.getProductStatusEnum().equals(ProductStatusEnum.OUTOFSTOCK) || product.getQuantity() == 0)
            throw  new ProductNotFoundException("product out of stock");
        CartItem cartItem = new CartItem();
        cartItem.setProduct(product);
        cartItem.setCartItemQuantity(cartProductDTO.getQuantity());
        return cartItem;
    }
}

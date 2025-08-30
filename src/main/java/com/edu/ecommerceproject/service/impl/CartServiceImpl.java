package com.edu.ecommerceproject.service.impl;

import com.edu.ecommerceproject.dto.request.CartProductDTO;
import com.edu.ecommerceproject.exception.CartNotFoundException;
import com.edu.ecommerceproject.exception.CustomerNotFoundException;
import com.edu.ecommerceproject.exception.LoginException;
import com.edu.ecommerceproject.models.entities.Cart;
import com.edu.ecommerceproject.models.entities.CartItem;
import com.edu.ecommerceproject.models.entities.Customer;
import com.edu.ecommerceproject.models.entities.Session;
import com.edu.ecommerceproject.repository.CartRepository;
import com.edu.ecommerceproject.repository.CustomerRepository;
import com.edu.ecommerceproject.repository.SessionRepository;
import com.edu.ecommerceproject.service.ICartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CartServiceImpl implements ICartService {
    private final CartRepository cartRepository;
    private final AuthServiceImpl authServiceImpl;
    private final SessionRepository sessionRepository;
    private final CustomerRepository customerRepository;
    private final CartItemServiceImpl cartItemServiceImpl;

    @Autowired
    public CartServiceImpl(CartRepository cartRepository, AuthServiceImpl authServiceImpl, SessionRepository sessionRepository, CustomerRepository customerRepository, CartItemServiceImpl cartItemServiceImpl){
        this.cartRepository = cartRepository;
        this.authServiceImpl = authServiceImpl;
        this.sessionRepository = sessionRepository;
        this.customerRepository = customerRepository;
        this.cartItemServiceImpl = cartItemServiceImpl;
    }

    @Override
    public Cart getCartProduct(String token) {
        if(!token.contains("customer"))
            throw new LoginException("invalid session token");
        authServiceImpl.checkStatusToken(token);
        Optional<Session> sessionOptional = sessionRepository.findByToken(token);
        if(sessionOptional.isEmpty())
            throw new LoginException("not found any session");
        Session session = sessionOptional.get();
        Optional<Customer> customerOptional = customerRepository.findById(session.getUserId());
        if (customerOptional.isEmpty())
            throw new CustomerNotFoundException("not found customer with this id");
        Customer customer = customerOptional.get();
        Optional<Cart> cartOptional = cartRepository.findByCustomer(customer);
        if (cartOptional.isEmpty())
            throw new CartNotFoundException("Not found cart ");
        return cartOptional.get();
    }

    @Override
    public Cart addProductToCart(CartProductDTO productDTO, String token) {
        if(!token.contains("customer"))
            throw new LoginException("invalid token ,just customer can add new product to cart");
        authServiceImpl.checkStatusToken(token);
        Optional<Session> sessionOptional = sessionRepository.findByToken(token);
        if(sessionOptional.isEmpty())
            throw new LoginException("not found valid session");
        Optional<Customer> customerOptional = customerRepository.findById(sessionOptional.get().getUserId());
        if(customerOptional.isEmpty())
            throw new CustomerNotFoundException("not found customer");
        Customer customer = customerOptional.get();
        Cart cart = customer.getCart();
        if(cart == null){
            cart = new Cart();
            cart.setCustomer(customer);
            cart.setCartItems(new ArrayList<>());
            customer.setCart(cart);
        }

        List<CartItem> cartItem = cart.getCartItems();
        if(cartItem == null){
            cartItem = new ArrayList<>();
            cart.setCartItems(cartItem);
        }
        CartItem item = cartItemServiceImpl.createItemForCart(productDTO);
        item.setCart(cart);
        cartItem.add(item);
        cart.calculateTotal();
        return cartRepository.save(cart);
    }

    @Override
    public Cart removeProductFromCart(CartProductDTO productDTO, String token) {
        if(!token.contains("customer"))
            throw new LoginException("token not valid , please login in first");
        authServiceImpl.checkStatusToken(token);
        Optional<Session> sessionOptional = sessionRepository.findByToken(token);
        if(sessionOptional.isEmpty())
            throw new LoginException("not found any session valid , please login first");
        Session session = sessionOptional.get();
        Optional<Customer> customerOptional = customerRepository.findById(session.getUserId());
        if(customerOptional.isEmpty())
            throw new CustomerNotFoundException("customer with this id not found");
        Customer customer = customerOptional.get();
        Cart cart = customer.getCart();
        if(cart == null || cart.getCartItems().isEmpty())
            throw new CartNotFoundException("cart is empty");
        cart.getCartItems().removeIf(item -> item.getProduct().getProductId().equals(productDTO.getProductId()));
        cart.calculateTotal();
        return cartRepository.save(cart);
    }

    @Override
    public Cart clearCart(String token) {
        if(!token.contains("customer"))
            throw new LoginException("token not valid,please login in first");
        authServiceImpl.checkStatusToken(token);
        Optional<Session> sessionOptional = sessionRepository.findByToken(token);
        if(sessionOptional.isEmpty())
            throw new LoginException("not found any session valid , please login first");
        Session session = sessionOptional.get();
        Optional<Customer> customerOptional = customerRepository.findById(session.getUserId());
        if(customerOptional.isEmpty())
            throw new CustomerNotFoundException("customer with this id not found");
        Customer customer = customerOptional.get();
        Cart cart = customer.getCart();
        if(cart == null || cart.getCartItems().isEmpty())
            throw new CartNotFoundException("cart is empty");
        cart.getCartItems().clear();
        cart.calculateTotal();
        return cartRepository.save(cart);
    }
}

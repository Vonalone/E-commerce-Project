package com.edu.ecommerceproject.service.impl;

import com.edu.ecommerceproject.dto.request.OrderDTO;
import com.edu.ecommerceproject.exception.*;
import com.edu.ecommerceproject.models.entities.*;
import com.edu.ecommerceproject.models.enums.OrderStatusEnum;
import com.edu.ecommerceproject.repository.CustomerRepository;
import com.edu.ecommerceproject.repository.OrderRepository;
import com.edu.ecommerceproject.repository.SessionRepository;
import com.edu.ecommerceproject.service.IOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class OrderServiceImpl implements IOrderService {
    private final OrderRepository orderRepository;
    private final AuthServiceImpl authServiceImpl;
    private final SessionRepository sessionRepository;
    private final CustomerRepository customerRepository;

    @Autowired
    public OrderServiceImpl(OrderRepository orderRepository, AuthServiceImpl authServiceImpl, SessionRepository sessionRepository, CustomerRepository customerRepository){
        this.orderRepository = orderRepository;
        this.authServiceImpl = authServiceImpl;
        this.sessionRepository = sessionRepository;
        this.customerRepository = customerRepository;
    }

    @Override
    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }

    @Override
    public Order getOrderById(Integer orderId) {
        Optional<Order> orderOptional = orderRepository.findById(orderId);
        if(orderOptional.isEmpty())
            throw new OrderNotFoundException("not found any order have this id");
        return orderOptional.get();
    }

    @Override
    public Order saveOrder(OrderDTO orderDTO, String token) {
        if(!token.contains("customer"))
            throw new LoginException("just customer can save order");
        authServiceImpl.checkStatusToken(token);
        Optional<Session> sessionOptional = sessionRepository.findByToken(token);
        if(sessionOptional.isEmpty())
            throw new LoginException("not found valid session,please login again");
        Session session = sessionOptional.get();
        Optional<Customer> customerOptional = customerRepository.findById(session.getUserId());
        if(customerOptional.isEmpty())
            throw new CustomerNotFoundException("not found customer");
        Customer customer = customerOptional.get();
        List<CartItem> cartItems = customer.getCart().getCartItems();
        if(cartItems == null || cartItems.isEmpty())
            throw new CartNotFoundException("no products in cart");
        Order newOrder = new Order();
        newOrder.setCustomer(customer);
        newOrder.setCartItemList(new ArrayList<>(cartItems));
        newOrder.setTotal(customer.getCart().getCartTotal());
        List<Address> addressList = customer.getAddressList();
        if (addressList == null || addressList.isEmpty())
            throw new AddressNotFoundException("Customer has no saved address. Please add one before ordering.");
        newOrder.setOrderAddress(addressList.get(0));
        newOrder.setOrderDate(LocalDateTime.now());
        boolean isvalidPayment = isValidPayment(customer,orderDTO);
        if(isvalidPayment){
            newOrder.setCreditCardNumber(orderDTO.getCard().getCardNumber());
            newOrder.setOrderStatus(OrderStatusEnum.SUCCESS);
        }else{
            newOrder.setCreditCardNumber(null);
            newOrder.setOrderStatus(OrderStatusEnum.PENDING);
        }
        cartItems.clear();
        return orderRepository.save(newOrder);
    }

    @Override
    public Order cancelOrder(Integer orderId, String token) {
        if(!token.contains("customer"))
            throw new LoginException("just customer can cancel their orders");
        authServiceImpl.checkStatusToken(token);
        Optional<Session> sessionOptional = sessionRepository.findByToken(token);
        if(sessionOptional.isEmpty())
            throw new LoginException("not found valid session,please login again");
        Session session = sessionOptional.get();
        Optional<Customer> customerOptional = customerRepository.findById(session.getUserId());
        if(customerOptional.isEmpty())
            throw new CustomerNotFoundException("not found customer");
        Customer customer = customerOptional.get();
        Optional<Order> orderOptional = orderRepository.findById(orderId);
        if(orderOptional.isEmpty())
            throw new OrderNotFoundException("order with this id not found");
        Order order = orderOptional.get();
        if(!order.getCustomer().getCustomerId().equals(customer.getCustomerId()))
            throw new LoginException("You cannot cancel someone else's order");
        if(order.getOrderStatus().equals(OrderStatusEnum.SUCCESS))
            throw new OrderNotFoundException("Completed orders cannot be canceled");
        order.setOrderStatus(OrderStatusEnum.CANCELLED);
        return orderRepository.save(order);
    }

    @Override
    public Order updateOrder(Integer orderId, OrderDTO orderDTO) {
        Optional<Order> orderOptional = orderRepository.findById(orderId);
        if(orderOptional.isEmpty())
            throw new OrderNotFoundException("order have this id not found");
        Order order = orderOptional.get();
        if(orderDTO.getCard() != null)
            order.setCreditCardNumber(orderDTO.getCard().getCardNumber());
        if(orderDTO.getAddress() != null)
            order.setOrderAddress(orderDTO.getAddress());
        return orderRepository.save(order);
    }

    private boolean isValidPayment(Customer customer, OrderDTO orderDTO) {
        return customer.getCreditCard().getCardNumber().equals(orderDTO.getCard().getCardNumber()) &&
                customer.getCreditCard().getCardValidity().equals(orderDTO.getCard().getCardValidity()) &&
                customer.getCreditCard().getCardCVV().equals(orderDTO.getCard().getCardCVV());
    }
}

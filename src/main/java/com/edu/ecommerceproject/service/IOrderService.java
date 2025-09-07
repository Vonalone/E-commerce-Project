package com.edu.ecommerceproject.service;

import com.edu.ecommerceproject.dto.request.OrderDTO;
import com.edu.ecommerceproject.models.entities.Order;

import java.util.List;

public interface IOrderService {
    List<Order> getAllOrders();
    Order getOrderById(Integer orderId);
    Order saveOrder(OrderDTO orderDTO,String token);
    Order cancelOrder(Integer orderId,String token);
    Order updateOrder(Integer orderId,OrderDTO orderDTO);
}

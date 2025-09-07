package com.edu.ecommerceproject.controller;

import com.edu.ecommerceproject.dto.request.OrderDTO;
import com.edu.ecommerceproject.models.entities.Order;
import com.edu.ecommerceproject.service.impl.OrderServiceImpl;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/orders")
@RequiredArgsConstructor
public class OrderController {
    private final OrderServiceImpl orderServiceImpl;

    @GetMapping
    public ResponseEntity<List<Order>> getAllOrders(){
        return new ResponseEntity<>(orderServiceImpl.getAllOrders(), HttpStatus.OK);
    }
    @GetMapping("/{id}")
    public ResponseEntity<Order> getOrderById(@PathVariable Integer id){
        return new ResponseEntity<>(orderServiceImpl.getOrderById(id),HttpStatus.OK);
    }
    @PostMapping
    public ResponseEntity<Order> addOrder(@Valid @RequestBody OrderDTO orderDTO, @RequestHeader("token") String token){
        return new ResponseEntity<>(orderServiceImpl.saveOrder(orderDTO,token),HttpStatus.OK);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Order> cancelOrderById(@PathVariable Integer id,@RequestHeader("token") String token){
        return new ResponseEntity<>(orderServiceImpl.cancelOrder(id,token),HttpStatus.OK);
    }
    @PutMapping("/{id}")
    public ResponseEntity<Order> updateOrder(@PathVariable Integer id,@Valid @RequestBody OrderDTO orderDTO){
        return new ResponseEntity<>(orderServiceImpl.updateOrder(id,orderDTO),HttpStatus.OK);
    }
}

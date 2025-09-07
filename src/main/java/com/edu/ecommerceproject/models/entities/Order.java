package com.edu.ecommerceproject.models.entities;

import com.edu.ecommerceproject.models.enums.OrderStatusEnum;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer orderId;

    private LocalDateTime orderDate;

    @NotNull
    @Enumerated(EnumType.STRING)
    private OrderStatusEnum orderStatus;

    private Double total;

    private String creditCardNumber;

    @ManyToOne
    @JsonIgnore
    @JoinColumn(referencedColumnName= "customerId")
    private Customer customer;

    @OneToMany(mappedBy = "order" ,cascade = CascadeType.ALL,orphanRemoval = true)
    private List<CartItem> cartItemList;

    @OneToOne
    @JoinColumn(referencedColumnName = "addressId")
    private Address orderAddress;
}

package com.edu.ecommerceproject.models.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Cart {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private Double cartTotal ;

    @OneToMany(mappedBy = "cart",cascade = CascadeType.ALL,orphanRemoval = true)
    @JsonIgnoreProperties(value = "cart")
    private List<CartItem> cartItems;

    @OneToOne
    @JsonIgnore
    @JoinColumn(referencedColumnName = "customerId")
    private Customer customer;


    public void calculateTotal(){
        this.cartTotal = cartItems.stream().mapToDouble(item -> item.getProduct().getPrice()*item.getCartItemQuantity()).sum();

    }
}


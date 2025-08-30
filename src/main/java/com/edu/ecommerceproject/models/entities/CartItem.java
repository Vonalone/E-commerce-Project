package com.edu.ecommerceproject.models.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class CartItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @OneToOne
    @JoinColumn(referencedColumnName = "productId")
    @JsonIgnoreProperties(value = {
            "productId",
            "seller",
            "quantity"
    })
    private Product product;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(referencedColumnName = "id")
    @JsonIgnoreProperties(value = "cartItems")
    private Cart cart;

    private Integer cartItemQuantity;
}

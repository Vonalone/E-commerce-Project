package com.edu.ecommerceproject.models.entities;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreditCard {
  private String cardNumber;
  private String cardValidity;
  private String cardCVV;
}

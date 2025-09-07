package com.edu.ecommerceproject.dto.request;

import com.edu.ecommerceproject.models.entities.Address;
import com.edu.ecommerceproject.models.entities.CreditCard;
import jakarta.persistence.Embedded;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderDTO {
    @NotNull
    @Embedded
    private CreditCard card;
    @NotNull
    private Address address;
}

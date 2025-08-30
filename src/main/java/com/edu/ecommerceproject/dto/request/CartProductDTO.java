package com.edu.ecommerceproject.dto.request;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CartProductDTO {

    @NotNull
    private Integer productId;

    @NotNull
    @Size(min = 3,max = 30,message = "product name size should be between 3-30")
    private String productName;

    @DecimalMin(value = "0.00")
    private Double price;

    @NotNull
    @Min(value = 0)
    private Integer quantity;
}

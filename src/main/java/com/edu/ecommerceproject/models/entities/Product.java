package com.edu.ecommerceproject.models.entities;

import com.edu.ecommerceproject.models.enums.CategoryEnum;
import com.edu.ecommerceproject.models.enums.ProductStatusEnum;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
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
@Entity
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer productId;

    @NotNull
    @Size(min = 3,max = 30,message = "product name size should be between 3-30")
    private String productName;

    @DecimalMin(value = "0.00")
    private Double price;

    private String description;

    @NotNull
    private String manufacturer;

    @NotNull
    @Min(value = 0)
    private Integer quantity ;

    @Enumerated(EnumType.STRING)
    private CategoryEnum categoryEnum;

    @Enumerated(EnumType.STRING)
    private ProductStatusEnum productStatusEnum;

    @ManyToOne
    @JsonIgnore
    private Seller seller;
}

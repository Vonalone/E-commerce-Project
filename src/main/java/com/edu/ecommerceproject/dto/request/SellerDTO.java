package com.edu.ecommerceproject.dto.request;


import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SellerDTO {

    @NotNull(message = "Email cannot be null")
    @Email
    private String email;

    @NotNull(message = "number phone cannot be null")
    @Pattern(regexp = "\\+212[0-9]{9}",message = "Enter correct number phone format ,start with +212")
    private String numberPhone;

    @NotNull(message = "password cannot be null")
    @Pattern(regexp = "[a-zA-Z0-9!@#$%^&*_]{8,15}",message = "password must be enter 8-15 characters in length, and include A-Z,a-z,0-9 and special characters !@#$%^&*_")
    private  String password;
}

package com.edu.ecommerceproject.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CustomerDTO {


    @NotNull(message = "Please enter the email")
    @Email
    private String email;
    @NotNull(message = "Please Enter Password")
    @Pattern(regexp = "[A-Za-z0-9!@#$%^&*_]{8,15}", message = "Password must be 8-15 characters in length and can include A-Z, a-z, 0-9, or special characters !@#$%^&*_")
    private String password;
    @NotNull(message = "Please enter the number phone")
    @Pattern(regexp = "\\+212[0-9]{9}",message = "Please enter 10 digit number phone")
    private String mobileNo;


}

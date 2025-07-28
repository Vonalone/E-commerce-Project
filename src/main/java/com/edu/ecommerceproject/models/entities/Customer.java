package com.edu.ecommerceproject.models.entities;


import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.*;

import java.time.LocalDateTime;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer customerId;

    @NotNull(message = "first name cannot be null")
    @Pattern(regexp = "[A-Za-z.\\s]+", message = "Enter valid characters in first name")
    private String firstName;

    @NotNull(message = "last name cannot be null")
    @Pattern(regexp = "[A-Za-z.\\s]+", message = "Enter valid characters in last name")
    private String lastName;

    @NotNull(message = "Please enter the number phone")
    @Column(unique = true)
    @Pattern(regexp = "\\+212[0-9]{9}",message = "Please enter 10 digit number phone")
    private String mobileNo;


    @NotNull(message = "Please enter the email")
    @Column(unique = true)
    @Email
    private String email;

    @NotNull(message = "Please Enter Password")
    @Pattern(regexp = "[A-Za-z0-9!@#$%^&*_]{8,15}", message = "Password must be 8-15 characters in length and can include A-Z, a-z, 0-9, or special characters !@#$%^&*_")
    private String password;
    private LocalDateTime CreatedOn;

    @Embedded
    private CreditCard creditCard;

}
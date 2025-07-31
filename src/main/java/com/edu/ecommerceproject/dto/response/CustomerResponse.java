package com.edu.ecommerceproject.dto.response;
import lombok.*;



@Data
@AllArgsConstructor
@NoArgsConstructor
public class CustomerResponse {


    private String firstName;
    private String lastName;
    private String mobileNo;
    private String email;



}
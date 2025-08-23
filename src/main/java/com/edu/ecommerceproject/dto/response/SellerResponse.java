package com.edu.ecommerceproject.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SellerResponse {
    private String firstName;
    private String lastName;
    private String email;
    private String mobileNumber;

}

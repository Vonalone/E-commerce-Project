package com.edu.ecommerceproject.models.entities;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Session {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer sessionId;

    @Column(unique = true)
    private Integer userId;

    @Column(unique = true)
    private String token;

    private String userType;
    private LocalDateTime dateStartSession;
    private LocalDateTime dateEndSession;

}
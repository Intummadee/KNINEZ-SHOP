package com.example.demo.data;

import lombok.Data;

@Data
public class LoginRequest {
    private String email;
    private String password;

    // Getters and setters
}
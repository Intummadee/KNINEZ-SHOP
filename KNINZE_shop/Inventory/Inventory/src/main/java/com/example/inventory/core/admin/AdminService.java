package com.example.inventory.core;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AdminService {
    @Autowired
    private AdminRepository adminRepository;
    public AdminService() {
        System.out.println("InventoryService bean initialized");
    }

    public AdminService(AdminRepository adminRepository){
        this.adminRepository = adminRepository;
    }
    public boolean isValidLogin(String enteredUsername, String enteredPassword) {
        // Check if either the username or password is empty
        if (enteredUsername.isEmpty() || enteredPassword.isEmpty()) {
            return false; // Invalid login if either field is empty
        }

        // Determine if the entered username is an email or a regular username
        Optional<AdminEntity> adminOptional;
        if (enteredUsername.contains("@")) {
            // It's an email, use findByEmail
            adminOptional = adminRepository.findByEmail(enteredUsername);
        } else {
            // It's a regular username, use findByUsername
            adminOptional = adminRepository.findByUsername(enteredUsername);
        }

        // Check if the user exists and the password matches
        return adminOptional
                .filter(adminEntity -> enteredPassword.equals(adminEntity.getPassword()))
                .isPresent();
    }


}


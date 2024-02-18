package com.example.demo.controllers;

import com.example.demo.data.*;
import com.example.demo.repository.UserRepository;
import com.example.demo.services.JwtUtil;
import com.example.demo.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/login")
public class LoginController {

    @Autowired
    private UserService userService;

    private final JwtUtil jwt;
    private final UserRepository userRepository;

    @Autowired
    public LoginController(final JwtUtil jwt, final UserRepository userRepository){
        this.jwt =jwt;
        this.userRepository = userRepository;
    }

    @PostMapping
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {
        // Assuming LoginRequest is a DTO containing email and password fields
        String email = loginRequest.getEmail();
        String password = loginRequest.getPassword();
        User user = userRepository.findByEmail(loginRequest.getEmail());

        if (userService.authenticateUser(email, password)) {
            // Authentication successful, you can generate a token here if using JWT
            String accessToken = jwt.generate(user, "ACCESS");
            String refreshToken = jwt.generate(user, "REFRESH");
            return ResponseEntity.ok(new AuthResponse(accessToken, refreshToken));
//            return ResponseEntity.ok("wow");
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid credentials");
        }
    }
    @PostMapping("/admin")
    public ResponseEntity<?> admin(@RequestBody AdminRequest adminRequest) {
        // Assuming LoginRequest is a DTO containing email and password fields
        String username = adminRequest.getUsername();
        String password = adminRequest.getPassword();
        User user = userRepository.findByUsername(adminRequest.getUsername());

        if (userService.authenticateAdmin(username, password)) {
            // Authentication successful, you can generate a token here if using JWT
            String accessToken = jwt.usernameGenerate(user, "ACCESS");
            String refreshToken = jwt.usernameGenerate(user, "REFRESH");
            return ResponseEntity.ok(new AuthResponse(accessToken, refreshToken));
//            return ResponseEntity.ok("wow");
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid credentials. You are not admin");
        }
    }
    @PostMapping("/owner")
    public ResponseEntity<?> owner(@RequestBody OwnerRequest ownerRequest) {
        // Assuming LoginRequest is a DTO containing email and password fields
        String username = ownerRequest.getUsername();
        String password = ownerRequest.getPassword();
        User user = userRepository.findByUsername(ownerRequest.getUsername());

        if (userService.authenticateOnwer(username, password)) {
            // Authentication successful, you can generate a token here if using JWT
            String accessToken = jwt.usernameGenerate(user, "ACCESS");
            String refreshToken = jwt.usernameGenerate(user, "REFRESH");
            return ResponseEntity.ok(new AuthResponse(accessToken, refreshToken));
//            return ResponseEntity.ok("wow");
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid credentials. You are not owner");
        }
    }
}
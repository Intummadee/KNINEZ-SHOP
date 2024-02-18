package com.example.demo.services;

import com.example.demo.data.User;
import com.example.demo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.List;
import java.util.Optional;


@Service
public class UserService {
    @Autowired
    private UserRepository repository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;  // เพิ่ม passwordEncoder

    public UserService(UserRepository repository, BCryptPasswordEncoder passwordEncoder) {
        this.repository = repository;
        this.passwordEncoder = passwordEncoder;
    }

    public String register(User user) {
        // ใส่ username email หรือ password หรือไม่
        if (user.getUsername() == null || user.getEmail() == null || user.getPassword() == null) {
            return "Please provide username, email, or password";
        }
        // ตรวจสอบว่าอีเมลซ้ำหรือไม่
        if (repository.findByEmail(user.getEmail()) != null) {
            return "Email is already registered";
        }

        // Encrypt พาสเวิร์ด
        String encryptedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(encryptedPassword);

        user.setRole("user");
        // บันทึกข้อมูลลงในดาต้าเบส
        repository.save(user);
        return "Added Successfully";
    }
    public boolean authenticateUser(String email, String password) {
        User user = repository.findByEmail(email);

        if (user != null && passwordEncoder.matches(password, user.getPassword())) {
            return true; // Authentication successful
        }

        return false; // Authentication failed
    }
    public boolean authenticateAdmin(String username, String password) {
        User user = repository.findByUsername(username);

        if (user != null && user.getUsername().equals("admin") && passwordEncoder.matches(password, user.getPassword())) {
            return true; // Authentication successful
        }

        return false; // Authentication failed
    }
    public boolean authenticateOnwer(String username, String password) {
        User user = repository.findByUsername(username);

        if (user != null && user.getUsername().equals("owner") && passwordEncoder.matches(password, user.getPassword())) {
            return true; // Authentication successful
        }

        return false; // Authentication failed
    }

}



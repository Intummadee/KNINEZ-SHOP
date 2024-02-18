package com.example.inventory.core;

import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface AdminRepository extends MongoRepository<AdminEntity, String> {
    Optional<AdminEntity> findByUsernameOrEmail(String username, String email);

    Optional<AdminEntity> findByUsername(String username);

    Optional<AdminEntity> findByEmail(String email);
}

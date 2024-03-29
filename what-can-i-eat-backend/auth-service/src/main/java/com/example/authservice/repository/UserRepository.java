package com.example.authservice.repository;

import com.example.authservice.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<UserEntity,Integer> {
    Optional<UserEntity> findByName(String username);
    Optional<UserEntity> findByEmail(String email);
}
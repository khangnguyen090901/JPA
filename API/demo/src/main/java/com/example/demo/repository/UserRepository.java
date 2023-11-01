package com.example.demo.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.models.User;

public interface UserRepository extends JpaRepository<User, Integer> {
    Optional<User> findByName(String name);

    Boolean existsByName(String name);
}

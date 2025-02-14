package com.example.laptopshop.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.laptopshop.domain.User;

public interface UserRepository extends JpaRepository<User, Long> {
    User save(User user);
}

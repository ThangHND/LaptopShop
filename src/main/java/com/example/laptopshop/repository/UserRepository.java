package com.example.laptopshop.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.laptopshop.domain.User;

public interface UserRepository extends JpaRepository<User, Long> {
    User save(User user);

    List<User> findAll();

    User findById(long id);

    User getById(long id);

}

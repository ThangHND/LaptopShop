package com.example.laptopshop.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.laptopshop.domain.User;
import com.example.laptopshop.repository.UserRepository;

@Service
public class UserService {
    private UserRepository repository;

    public UserService(UserRepository repository) {
        this.repository = repository;
    }

    public User saveUser(User user) {
        return repository.save(user);
    }

    public List<User> getAllUser() {
        return this.repository.findAll();
    }

    public User getById(long id) {
        return this.repository.getById(id);
    }

}

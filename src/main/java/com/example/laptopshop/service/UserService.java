package com.example.laptopshop.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.laptopshop.domain.Role;
import com.example.laptopshop.domain.User;
import com.example.laptopshop.domain.dto.RegisterDTO;
import com.example.laptopshop.repository.RoleRepository;
import com.example.laptopshop.repository.UserRepository;

@Service
public class UserService {
    private final UserRepository repository;
    private final RoleRepository roleRepository;

    public UserService(UserRepository repository, RoleRepository roleRepository) {
        this.repository = repository;
        this.roleRepository = roleRepository;
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

    public void deleteById(long id) {
        this.repository.deleteById(id);
    }

    public Role getRoleByName(String name) {
        return roleRepository.findByName(name);
    }

    public User registerDTOtoUser(RegisterDTO registerDTO) {
        User user = new User();
        user.setFullName(registerDTO.getFirstName() + " " + registerDTO.getLastName());
        user.setEmail(registerDTO.getEmail());
        user.setPassword(registerDTO.getPassword());
        return user;
    }

    public boolean checkEmailExist(String email) {
        return this.repository.existsByEmail(email);
    }

    public User getUserByEmail(String email) {
        return this.repository.findByEmail(email);
    }
}

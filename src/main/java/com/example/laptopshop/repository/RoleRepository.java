package com.example.laptopshop.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.laptopshop.domain.Role;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Role findByName(String name);
}

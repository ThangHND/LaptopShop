package com.example.laptopshop.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.laptopshop.domain.Product;

public interface ProductRepository extends JpaRepository<Product, Long> {
    List<Product> findAll();

    Product findById(long id);
}

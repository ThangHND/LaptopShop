package com.example.laptopshop.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.laptopshop.domain.Product;

public interface ProductRepository extends JpaRepository<Product, Long> {
    // Product saveProduct(Product product);

}

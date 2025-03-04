package com.example.laptopshop.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.laptopshop.domain.CartDetail;
import com.example.laptopshop.domain.Product;

public interface ProductRepository extends JpaRepository<Product, Long> {
    List<Product> findAll();

    Optional<Product> findById(long id);

}

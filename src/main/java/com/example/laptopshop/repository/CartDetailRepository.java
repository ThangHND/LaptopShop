package com.example.laptopshop.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.laptopshop.domain.Cart;
import com.example.laptopshop.domain.CartDetail;
import com.example.laptopshop.domain.Product;
import java.util.List;

@Repository
public interface CartDetailRepository extends JpaRepository<CartDetail, Long> {
    boolean existsByCartAndProduct(Cart cart, Product product);

    CartDetail findByCartAndProduct(Cart cart, Product product);

    Optional<CartDetail> findById(long id);
}

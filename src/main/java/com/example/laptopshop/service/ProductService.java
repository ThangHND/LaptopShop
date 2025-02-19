package com.example.laptopshop.service;

import org.springframework.stereotype.Service;

import com.example.laptopshop.controller.admin.ProductController;
import com.example.laptopshop.domain.Product;

@Service
public class ProductService {
    private ProductController controller;

    public ProductService(ProductController controller) {
        this.controller = controller;
    }

    public void handleSaveProduct(Product product) {
        // this.controller.saveProduct(product);
    }
}
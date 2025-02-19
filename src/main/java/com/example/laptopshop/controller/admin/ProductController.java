package com.example.laptopshop.controller.admin;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.example.laptopshop.domain.Product;

@Controller
public class ProductController {

    @GetMapping("/admin/product")
    public String showProduct() {
        return "admin/product/show";
    }

    @GetMapping("admin/createProduct")
    public String pageCreateProduct(Model model) {
        model.addAttribute("newProduct", new Product());
        return "admin/product/createProduct";
    }

    @PostMapping("/admin/create/createProduct")
    public String createProduct(Model model, @ModelAttribute("newProduct") Product product
    // @RequestParam("getFileImage") MultipartFile file
    ) {
        // model.addAttribute(null, product);
        System.out.println(product);
        return "redirect:/admin/product";

    }

    public void saveProduct(Product product) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'saveProduct'");
    }
}

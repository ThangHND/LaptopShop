package com.example.laptopshop.controller.client;

import java.util.List;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.example.laptopshop.domain.Product;
import com.example.laptopshop.domain.User;
import com.example.laptopshop.domain.dto.RegisterDTO;
import com.example.laptopshop.service.ProductService;
import com.example.laptopshop.service.UserService;

import jakarta.validation.Valid;

@Controller
public class HomePageController {
    private final ProductService productService;
    private final UserService userService;
    private PasswordEncoder passwordEncoder;

    public HomePageController(ProductService productService, UserService userService, PasswordEncoder passwordEncoder) {
        this.productService = productService;
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping("/")
    public String showHomePage(Model model) {
        List<Product> list = this.productService.getAllProduct();
        model.addAttribute("products", list);
        return "client/HomePage/show";
    }

    @GetMapping("/register")
    public String showPageRegister(Model model) {
        model.addAttribute("registerUser", new RegisterDTO());
        return "client/auth/register";
    }

    @PostMapping("/register")
    public String handleRegister(@ModelAttribute("registerUser") @Valid RegisterDTO dto,
            BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            return "client/auth/register";
        }

        User user = this.userService.registerDTOtoUser(dto);

        String hashPassword = passwordEncoder.encode(user.getPassword());

        user.setPassword(hashPassword);

        user.setRole(this.userService.getRoleByName("USER"));

        this.userService.saveUser(user);

        return "redirect:/login";

    }

    @GetMapping("/login")
    public String showPageLogin(Model model) {
        return "client/auth/login";
    }

    @PostMapping("/login")
    public String loginUser() {

        return "/";
    }

}

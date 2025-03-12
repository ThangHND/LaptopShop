package com.example.laptopshop.controller.admin;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.example.laptopshop.service.ProductService;
import com.example.laptopshop.service.UserService;

@Controller
public class DashBoardController {
    private final ProductService productService;
    private final UserService userService;

    public DashBoardController(ProductService productService, UserService userService) {
        this.productService = productService;
        this.userService = userService;
    }

    @GetMapping("/admin")
    public String dashboard(Model model) {
        model.addAttribute("countUsers", userService.countUsers());
        model.addAttribute("countProducts", userService.countProducts());
        model.addAttribute("countOrders", userService.countOrders());
        return "admin/dashboard/index";
    }

}

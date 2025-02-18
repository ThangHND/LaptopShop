package com.example.laptopshop.controller.client;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class ItemController {

    @GetMapping("/detail/{id}")
    public String showDetailPage(Model model, @PathVariable long id) {
        return "client/product/detail";
    }
}

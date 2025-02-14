package com.example.laptopshop.controller.admin;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class DashBoardController {

    @GetMapping("/admin")
    public String dashboard() {
        return "admin/user/index";
    }

}

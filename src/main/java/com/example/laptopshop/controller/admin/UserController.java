package com.example.laptopshop.controller.admin;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.example.laptopshop.domain.User;
import com.example.laptopshop.repository.UserRepository;
import com.example.laptopshop.service.UserService;

@Controller
public class UserController {
    private final UserService service;
    private final UserRepository userRepository;

    public UserController(UserService service, UserRepository userRepository) {
        this.service = service;
        this.userRepository = userRepository;
    }

    @RequestMapping("/admin/user")
    public String userDashboard(Model model) {
        model.addAttribute("newUser", new User());
        return "admin/dashboard/createUser";
    }

    @RequestMapping(value = "admin/dashboard/createUser", method = RequestMethod.POST)
    public String saveUser(Model model, @ModelAttribute("newUser") User user) {
        model.addAttribute("newUser", user);
        System.out.println(user);
        this.userRepository.save(user);
        return "redirect:/admin";
    }
}

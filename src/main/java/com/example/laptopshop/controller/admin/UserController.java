package com.example.laptopshop.controller.admin;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.example.laptopshop.domain.User;
import com.example.laptopshop.repository.UserRepository;
import com.example.laptopshop.service.UserService;
import com.fasterxml.jackson.annotation.JsonCreator.Mode;

@Controller
public class UserController {
    private final UserService service;
    private final UserRepository userRepository;

    public UserController(UserService service, UserRepository userRepository) {
        this.service = service;
        this.userRepository = userRepository;
    }

    // show list user
    @RequestMapping("/admin/userDashboard")
    public String getAllUser(Model model) {
        List<User> users = this.service.getAllUser();
        model.addAttribute("showUser", users);
        return "admin/user/userDashboard";
    }

    @RequestMapping("/admin/detail/{id}")
    public String userDetail(Model model, @PathVariable long id) {
        User detail = this.service.getById(id);
        model.addAttribute("detail", detail);
        return "admin/user/userDetail";
    }

    // function
    // create user
    @RequestMapping("/admin/user") // GET
    public String userDashboard(Model model) {
        model.addAttribute("newUser", new User());
        return "admin/user/createUser";
    }

    // create user
    @RequestMapping(value = "admin/user/createUser", method = RequestMethod.POST) // POST
    public String saveUser(Model model, @ModelAttribute("newUser") User user) {
        model.addAttribute("newUser", user);
        System.out.println(user);
        this.userRepository.save(user);
        return "redirect:/admin/userDashboard";
    }

    // update
    @RequestMapping("/admin/updateUser/{id}")
    public String showUserbeforeUpdate(Model model, @PathVariable long id) {
        User users = this.service.getById(id);
        model.addAttribute("newUser", users);
        System.out.println(users);
        return "admin/user/updateUser";
    }

    @RequestMapping(value = "admin/updateUser", method = RequestMethod.POST)
    public String UpdateUser(Model model, @ModelAttribute User user) {
        User users = this.service.getById(user.getId());
        if (users != null) {
            users.setFullName(user.getFullName());
            users.setAddress(user.getAddress());
            users.setPhone(user.getPhone());
            this.service.saveUser(users);
        }
        return "redirect:/admin/userDashboard";
    }

    @RequestMapping("/admin/delete/{id}")
    public String getDeleteUser(Model model, @PathVariable long id) {
        User user = this.service.getById(id);
        model.addAttribute("newUser", user);
        return "admin/user/deleteUser";
    }

    // delete
    @RequestMapping(value = "admin/dashboard/delete", method = RequestMethod.POST)
    public String deleteUser(Model model, @ModelAttribute("newUser") User user) {
        this.service.deleteById(user.getId());
        System.out.println(user.getId() + "asdasdasdasdas");
        return "redirect:/admin/userDashboard";
    }
}

package com.example.laptopshop.controller.admin;

import java.util.List;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.example.laptopshop.domain.User;
import com.example.laptopshop.service.UploadService;
import com.example.laptopshop.service.UserService;

@Controller
public class UserController {
    private final UserService service;
    private final UploadService uploadService;
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    public UserController(UserService service, UploadService uploadService,
            BCryptPasswordEncoder bCryptPasswordEncode) {
        this.service = service;
        this.uploadService = uploadService;
        this.bCryptPasswordEncoder = bCryptPasswordEncode;
    }

    // show list user
    @GetMapping("/admin/userDashboard")
    public String getAllUser(Model model) {
        List<User> users = this.service.getAllUser();
        model.addAttribute("showUser", users);
        return "admin/user/userDashboard";
    }

    @GetMapping("/admin/detail/{id}")
    public String userDetail(Model model, @PathVariable long id) {
        User detail = this.service.getById(id);
        model.addAttribute("detail", detail);
        return "admin/user/userDetail";
    }

    // function
    // create user
    @GetMapping("/admin/create") // GET
    public String userDashboard(Model model) {
        model.addAttribute("newUser", new User());
        return "admin/user/createUser";
    }

    // create user
    @PostMapping(value = "admin/create/createUser") // POST
    public String saveUser(Model model,
            @ModelAttribute("newUser") User user,
            @RequestParam("getFileImage") MultipartFile file) {
        String avatar = this.uploadService.handleSaveUploadFile(file, "avatar");
        // this.userRepository.save(user);

        return "redirect:/admin/userDashboard";
    }

    // update
    @GetMapping("/admin/updateUser/{id}")
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

    @GetMapping("/admin/delete/{id}")
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

package com.example.laptopshop.controller.admin;

import java.util.List;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.example.laptopshop.domain.Role;
import com.example.laptopshop.domain.User;
import com.example.laptopshop.service.UploadService;
import com.example.laptopshop.service.UserService;

import jakarta.validation.Valid;

@Controller
public class UserController {
    private final UserService service;
    private final UploadService uploadService;
    private PasswordEncoder passwordEncoder;

    public UserController(UserService service, UploadService uploadService,
            PasswordEncoder passwordEncoder) {
        this.service = service;
        this.uploadService = uploadService;
        this.passwordEncoder = passwordEncoder;
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
            @ModelAttribute("newUser") @Valid User user,
            BindingResult bindingResult,
            @RequestParam("getFileImage") MultipartFile file) {

        // validate
        List<FieldError> errors = bindingResult.getFieldErrors();
        for (FieldError error : errors) {
            System.out.println(error.getObjectName() + "_" + error.getDefaultMessage());
        }

        // avatar
        String avatar = this.uploadService.handleSaveUploadFile(file, "avatar");

        String hashPassword = passwordEncoder.encode(user.getPassword());

        user.setAvatar(avatar);
        user.setPassword(hashPassword);

        user.setRole(this.service.getRoleByName(user.getRole().getName()));
        this.service.saveUser(user);

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
    public String UpdateUser(Model model, @ModelAttribute User user, @RequestParam("getFileImage") MultipartFile file) {
        User users = this.service.getById(user.getId());
        if (users != null) {
            users.setFullName(user.getFullName());
            users.setAddress(user.getAddress());
            users.setPhone(user.getPhone());

            Role role = this.service.getRoleByName(user.getRole().getName());
            users.setRole(role);

            if (!file.isEmpty()) {
                String avatar = this.uploadService.handleSaveUploadFile(file, "avatar");
                users.setAvatar(avatar);
            } else {
                users.setAvatar(users.getAvatar());
            }

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

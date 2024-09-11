package com.foodlink.controller;

import com.foodlink.entity.UserEntity;
import com.foodlink.permissions.UserTypeEnum;
import com.foodlink.repository.RoleRepository;
import com.foodlink.roles.Role;
import com.foodlink.service.UserService;
import org.hibernate.usertype.UserType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private UserService userService;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @GetMapping("/login")
    public String exibirIndex() {
        return "index";
    }

    @GetMapping("/register")
    public String displayRegister(Model model) {
        return "auth/displayRegister";
    }

    @PostMapping("/register")
    public String registerUser(@RequestParam String cnpj,
                             @RequestParam String password,
                             @RequestParam UserTypeEnum type) {
        try {
            userService.registerUser(cnpj, password, type);
            return "redirect:/auth/login";
        } catch (Exception e) {
            return "auth/displayRegister";
        }

    }

}

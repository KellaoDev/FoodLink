package com.foodlink.controller;

import com.foodlink.entity.UserEntity;
import com.foodlink.permissions.UserTypeEnum;
import com.foodlink.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private UserService userService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @GetMapping("/displayRegister")
    public String displayRegister(Model model) {
        model.addAttribute("user", new UserEntity());
        return "/auth/displayRegister";
    }

    @PostMapping("/register")
    public String registerUser(String cnpj, String username, String password, byte[] profile_picture, String userTypeEnum, Model model) {
        try {
            UserTypeEnum type = UserTypeEnum.valueOf(userTypeEnum);
            userService.createUser(cnpj, username, password, profile_picture, type);
            model.addAttribute("message", "Registro bem-sucedido. Você pode agora fazer login.");
            return "redirect:/index";
        } catch (Exception e) {
            model.addAttribute("message", "Erro ao registrar: " + e.getMessage());
            return "/auth/displayRegister";
        }
    }
}

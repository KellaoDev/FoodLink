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

import java.util.Optional;
import java.util.Set;

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

    @GetMapping("/displayRegister")
    public String displayRegister(Model model) {
        model.addAttribute("user", new UserEntity());
        model.addAttribute("userTypes", UserTypeEnum.values());
        return "/auth/displayRegister";
    }

    @PostMapping("/register")
    public String registerUser(@RequestParam String cnpj,
                               @RequestParam String password,
                               @RequestParam String role, Model model) {

        if (userService.cnpjExists(cnpj)) {
            model.addAttribute("error", "Nome de usuário já existe");
            return "redirect:/auth/displayRegister";
        }

        UserEntity user = new UserEntity();
        user.setCnpj(cnpj);
        user.setPassword(password);

        UserTypeEnum userType;
        try {
            userType = UserType.valueOf(role); // 'role' deve corresponder ao nome do enum
        } catch (IllegalArgumentException e) {
            model.addAttribute("error", "Tipo de conta inválido");
            return "redirect:/auth/displayRegister";
        }

        userService.registerUser(user, userType);

        return "redirect:/login";
    }
}

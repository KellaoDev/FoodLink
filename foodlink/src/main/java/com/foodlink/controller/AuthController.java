package com.foodlink.controller;

import com.foodlink.dto.UserRegistrationDTO;
import com.foodlink.entity.UserEntity;
import com.foodlink.permissions.UserTypeEnum;
import com.foodlink.repository.RoleRepository;
import com.foodlink.roles.Role;
import com.foodlink.service.UserService;
import org.hibernate.usertype.UserType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@Controller
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private UserService userService;

    @GetMapping("/login")
    public String exibirLogin(Model model) {
        return "index";
    }

    @PostMapping("/login")
    public String loginUser(@ModelAttribute("user") UserRegistrationDTO userDto, Model model) {
        try {
            userService.loginUser(userDto);
            return "redirect:/menu/painel";
        } catch (BadCredentialsException e) {
            model.addAttribute("error", e.getMessage());
            return "index";
        }
    }

    @GetMapping("/displayRegister")
    public String displayRegister(Model model) {
        model.addAttribute("user", new UserEntity());
        return "auth/displayRegister";
    }

    @PostMapping("/register")
    public String registerUser(@ModelAttribute("user") UserRegistrationDTO userDto, Model model) {
        try {
            userService.registerUser(userDto);
            return "redirect:/auth/login";
        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
            return "auth/displayRegister";
        }
    }

}

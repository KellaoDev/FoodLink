package com.foodlink.service;

import com.foodlink.dto.UserRegistrationDTO;
import com.foodlink.entity.UserEntity;
import com.foodlink.permissions.UserTypeEnum;
import com.foodlink.repository.RoleRepository;
import com.foodlink.repository.UserRepository;
import com.foodlink.roles.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class UserService  {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    public void registerUser(UserRegistrationDTO userDto) {
        if (!userDto.getPassword().equals(userDto.getConfirmPassword())) {
            throw new IllegalArgumentException("Passwords do not match");
        }

        if (this.userRepository.findByCnpj(userDto.getCnpj()) != null) {
            throw new DataIntegrityViolationException("User exists");
        }

        UserEntity user = new UserEntity();
        user.setCnpj(userDto.getCnpj());
        user.setUsername(userDto.getUsername());
        user.setPassword(passwordEncoder.encode(userDto.getPassword()));
        user.setUserTypeEnum(userDto.getUserTypeEnum());

        if (user.getUserTypeEnum() == UserTypeEnum.RESTAURANTE) {
            Role roleRestaurante = roleRepository.findByName("ROLE_RESTAURANTE");
            user.setRoles(Set.of(roleRestaurante));
        } else if (user.getUserTypeEnum() == UserTypeEnum.ONG) {
            Role roleOng = roleRepository.findByName("ROLE_ONG");
            user.setRoles(Set.of(roleOng));
        }
        userRepository.save(user);
    }
}

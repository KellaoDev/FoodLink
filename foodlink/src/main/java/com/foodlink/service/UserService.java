package com.foodlink.service;

import com.foodlink.entity.UserEntity;
import com.foodlink.permissions.UserTypeEnum;
import com.foodlink.repository.RoleRepository;
import com.foodlink.repository.UserRepository;
import com.foodlink.roles.Role;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class UserService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    public UserEntity createUser(String cnpj, String username, String password, byte[] profile_picture, UserTypeEnum userTypeEnum) {
        if (userRepository.findByUsername(username).isPresent()) {
            throw new IllegalStateException("Username already exists");
        }
        UserEntity user = new UserEntity();
        user.setCnpj(cnpj);
        user.setUsername(username);
        user.setPassword(passwordEncoder.encode(password));
        user.setProfile_picture(profile_picture);
        user.setUserTypeEnum(userTypeEnum);

        Role role;
        if (userTypeEnum == UserTypeEnum.ONG) {
            role = roleRepository.findByName("ROLE_ONG")
                    .orElseThrow(() -> new IllegalStateException("Role ONG not found"));
        } else {
            role = roleRepository.findByName("ROLE_RESTAURANTE")
                    .orElseThrow(() -> new IllegalStateException("Role RESTAURANTE not found"));
        }

        user.getRoles().add(role);

        System.out.println("Salvando o usuário com username: " + username);


        return this.userRepository.save(user);
    }

    public Optional<UserEntity> findUserByUsername(String username) {
        return this.userRepository.findByUsername(username);
    }

    public List<UserEntity> findAll() {
        return this.userRepository.findAll();
    }
}

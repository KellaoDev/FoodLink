package com.foodlink.service;

import com.foodlink.entity.UserEntity;
import com.foodlink.permissions.UserTypeEnum;
import com.foodlink.repository.RoleRepository;
import com.foodlink.repository.UserRepository;
import com.foodlink.roles.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    public void registerUser(UserEntity user, UserTypeEnum type) {
        // Codifica a senha
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        // Encontra o papel correspondente ao tipo
        Role role = roleRepository.findByName(type.name())
                .orElseThrow(() -> new RuntimeException("Role not found"));

        // Adiciona o papel ao usuário
        user.setRoles(Set.of(role));

        // Salva o usuário no repositório
        userRepository.save(user);
    }

    public boolean cnpjExists(String cnpj) {
        return userRepository.findByCnpj(cnpj).isPresent();
    }

    public Optional<UserEntity> findUserByUsername(String username) {
        return this.userRepository.findByUsername(username);
    }

    public List<UserEntity> findAll() {
        return this.userRepository.findAll();
    }
}

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

   public void registerUser(String cnpj, String password, UserTypeEnum type) {
       UserEntity user = new UserEntity();
       user.setCnpj(cnpj);
       user.setPassword(passwordEncoder.encode(password));
       user.setUserTypeEnum(type);

       Role role;
       if(type == UserTypeEnum.RESTAURANTE) {
           role = roleRepository.findByName("ROLE_RESTAURANTE");
       } else {
           role = roleRepository.findByName("ROLE_ONG");
       }

       if (role == null) {
           throw new RuntimeException("Role não encontrada: " + (type == UserTypeEnum.RESTAURANTE ? "ROLE_RESTAURANTE" : "ROLE_ONG"));
       }

       user.setRoles(Set.of(role));
       userRepository.save(user);
   }

    public List<UserEntity> findAll() {
        return this.userRepository.findAll();
    }
}

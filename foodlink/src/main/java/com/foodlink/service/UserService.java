package com.foodlink.service;

import com.foodlink.entity.UserEntity;
import com.foodlink.permissions.UserTypeEnum;
import com.foodlink.repository.RoleRepository;
import com.foodlink.repository.UserRepository;
import com.foodlink.roles.Role;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

   public void registerUser(UserEntity user) {

       user.setPassword(passwordEncoder.encode(user.getPassword()));

       if(user.getUserTypeEnum() == UserTypeEnum.RESTAURANTE) {
           Role roleRestaurante = roleRepository.findByName("ROLE_RESTAURANTE");
           user.setRoles(Set.of(roleRestaurante));
       } else if (user.getUserTypeEnum() == UserTypeEnum.ONG) {
           Role roleOng = roleRepository.findByName("ROLE_ONG");
           user.setRoles(Set.of(roleOng));
       }

       userRepository.save(user);
   }
}

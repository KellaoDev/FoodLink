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

    private static final Logger logger = LoggerFactory.getLogger(UserService.class);

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

   public void registerUser(String cnpj, String password, UserTypeEnum type) {

       //LOG
       logger.debug("Registering user with CNPJ: {}", cnpj);

       if (userRepository.findByCnpj(cnpj) != null) {

           //LOG
           logger.error("User with CNPJ {} already exists", cnpj);
           throw new DataIntegrityViolationException("User exists");
       }

       UserEntity user = new UserEntity();
       user.setCnpj(cnpj);
       user.setPassword(passwordEncoder.encode(password));
       user.setUserTypeEnum(type);

       Role role = roleRepository.findByName(type == UserTypeEnum.RESTAURANTE ? "ROLE_RESTAURANTE" : "ROLE_ONG");

       if (role == null) {
           //LOG
           logger.error("Role not found for type: {}", type.name());
           throw new RuntimeException("Role não encontrada: " + (type == UserTypeEnum.RESTAURANTE ? "ROLE_RESTAURANTE" : "ROLE_ONG"));
       }

       user.setRoles(Set.of(role));
       userRepository.save(user);

       logger.info("User registered successfully with CNPJ: {}", cnpj);
   }
}

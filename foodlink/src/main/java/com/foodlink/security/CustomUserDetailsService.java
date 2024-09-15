package com.foodlink.security;

import com.foodlink.entity.UserEntity;
import com.foodlink.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    //LOG
    private static final Logger logger = LoggerFactory.getLogger(CustomUserDetailsService.class);


    @Autowired
    private UserRepository userRepository;

   @Override
    public UserDetails loadUserByUsername(String cnpj) throws UsernameNotFoundException {

       //LOG
       logger.debug("Attempting to load user by CNPJ: {}", cnpj);

       UserEntity user = userRepository.findByCnpj(cnpj);
       if(user == null) {
           System.out.println("User not found with CNPJ: " + cnpj); // Log para depuração
           throw new UsernameNotFoundException("User not found");
       }

       //LOG
       logger.debug("User found: {}", user.getCnpj());

       return new CustomUserDetails(user);
   }

}

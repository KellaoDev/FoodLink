package com.foodlink.security;

import com.foodlink.entity.UserEntity;
import com.foodlink.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String cnpj) throws UsernameNotFoundException {

        UserEntity user = userRepository.findByCnpj(cnpj);
        if (user == null) {
            throw new UsernameNotFoundException("User not found");
        }
        if (user.getRoles().isEmpty()) {
            throw new UsernameNotFoundException("Usuário não tem roles atribuídas");
        }
        return new CustomUserDetails(user);
    }
}

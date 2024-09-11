package com.foodlink.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        return http.csrf(c -> c.disable())
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers("/index", "/auth/displayRegister").permitAll()
                        .requestMatchers("/admin/**").hasRole("ADMIN")
                        .requestMatchers("/ong/**").hasRole("ONG")
                        .requestMatchers("/restaurante/**").hasRole("RESTAURANTE")
                        .anyRequest().authenticated()
                )
                .formLogin(f -> f
                        .loginPage("/index")
                        .loginProcessingUrl("/auth/login")
                        .defaultSuccessUrl("/menu")
                        .failureUrl("/index")
                        .permitAll()
                )
                .sessionManagement(s -> s
                        .maximumSessions(1)
                ).build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}

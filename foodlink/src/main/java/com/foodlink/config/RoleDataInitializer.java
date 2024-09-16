package com.foodlink.config;

import com.foodlink.repository.RoleRepository;
import com.foodlink.roles.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class RoleDataInitializer implements CommandLineRunner {

    @Autowired
    private RoleRepository roleRepository;

    @Override
    public void run(String... args) throws Exception {

        if (roleRepository.findByName("ROLE_RESTAURANTE") == null) {
            Role restauranteRole = new Role();
            restauranteRole.setName("ROLE_RESTAURANTE");
            roleRepository.save(restauranteRole);
        }

        if (roleRepository.findByName("ROLE_ONG") == null) {
            Role ongRole = new Role();
            ongRole.setName("ROLE_ONG");
            roleRepository.save(ongRole);
        }
    }
}

package com.foodlink.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class DataBaseInitializer implements CommandLineRunner {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public void run(String... args) throws Exception {
        String sql = "CREATE TABLE IF NOT EXISTS persistent_logins (" +
                "username VARCHAR(64) NOT NULL, " +
                "series VARCHAR(64) PRIMARY KEY, " +
                "token VARCHAR(64) NOT NULL, " +
                "last_used TIMESTAMP NOT NULL" +
                ")";
        try {
            jdbcTemplate.execute(sql);
            System.out.println("Table 'persistent_logins' created or already exists.");
        } catch (Exception e) {
            System.err.println("Error creating table 'persistent_logins': " + e.getMessage());
        }
    }
}

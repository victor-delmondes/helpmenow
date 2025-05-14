package com.br.helpmenow.config;

import com.br.helpmenow.model.*;
import com.br.helpmenow.repository.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
public class DataInitializer {

    @Bean
    public CommandLineRunner initData(UserRepository userRepository,
                                      DepartmentRepository departmentRepository,
                                      BCryptPasswordEncoder passwordEncoder) {
        return args -> {
            if (userRepository.findByEmail("admin@example.com").isEmpty()) {
                Department ti = new Department("IT", "101", "Building A");
                departmentRepository.save(ti);

                UserApp admin = new UserApp();
                admin.setName("Admin");
                admin.setEmail("admin@example.com");
                admin.setPassword(passwordEncoder.encode("123456"));
                admin.setType(UserType.ADMIN);
                admin.setActive(true);
                admin.setDepartment(ti);

                userRepository.save(admin);
                System.out.println("✅ Admin user created: admin@example.com / 123456");
            }
        };
    }
}

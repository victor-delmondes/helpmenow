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
                                      CategoryRepository categoryRepository,
                                      BCryptPasswordEncoder passwordEncoder) {
        return args -> {
            if (userRepository.findByEmail("admin@example.com").isEmpty()) {
                // Departamentos
                Department ti = new Department("IT", "101", "Building A");
                Department financeiro = new Department("Finance", "202", "Building B");
                Department atendimento = new Department("Support", "303", "Building C");

                departmentRepository.save(ti);
                departmentRepository.save(financeiro);
                departmentRepository.save(atendimento);

                // Categorias
                Category acesso = new Category("Problemas de Acesso");
                Category rede = new Category("Rede e Internet");
                Category sistema = new Category("Erro no Sistema");

                categoryRepository.save(acesso);
                categoryRepository.save(rede);
                categoryRepository.save(sistema);

                // Usuários
                UserApp admin = new UserApp("Admin", "admin@example.com", passwordEncoder.encode("123456"), UserType.ADMIN, true, ti);
                UserApp user1 = new UserApp("Carlos Financeiro", "carlos@empresa.com", passwordEncoder.encode("123456"), UserType.CLIENT, true, financeiro);
                UserApp user2 = new UserApp("Fernanda Suporte", "fernanda@empresa.com", passwordEncoder.encode("123456"), UserType.CLIENT, true, atendimento);

                userRepository.save(admin);
                userRepository.save(user1);
                userRepository.save(user2);

                System.out.println("✅ Dados de exemplo inseridos com sucesso!");
            }
        };
    }

}

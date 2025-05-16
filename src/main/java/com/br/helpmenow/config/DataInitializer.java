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
                                      TicketRepository ticketRepository,
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

                // Chamados
                Ticket ticket1 = new Ticket();
                ticket1.setTitle("Erro ao acessar o sistema bancário");
                ticket1.setDescription("Não consigo fazer login no sistema do banco interno.");
                ticket1.setPriority(TicketPriority.HIGH);
                ticket1.setStatus(TicketStatus.OPEN);
                ticket1.setCreatedBy(user1);
                ticket1.setAssignedTechnician(admin);
                ticket1.setCategory(acesso);

                Ticket ticket2 = new Ticket();
                ticket2.setTitle("Internet intermitente no setor de atendimento");
                ticket2.setDescription("A conexão cai a cada 10 minutos, prejudicando o atendimento.");
                ticket2.setPriority(TicketPriority.MEDIUM);
                ticket2.setStatus(TicketStatus.OPEN);
                ticket2.setCreatedBy(user2);
                ticket2.setAssignedTechnician(admin);
                ticket2.setCategory(rede);

                Ticket ticket3 = new Ticket();
                ticket3.setTitle("Sistema trava ao gerar relatórios financeiros");
                ticket3.setDescription("Ao gerar o relatório mensal, o sistema congela.");
                ticket3.setPriority(TicketPriority.HIGH);
                ticket3.setStatus(TicketStatus.IN_PROGRESS);
                ticket3.setCreatedBy(user1);
                ticket3.setAssignedTechnician(admin);
                ticket3.setCategory(sistema);

                ticketRepository.save(ticket1);
                ticketRepository.save(ticket2);
                ticketRepository.save(ticket3);

                System.out.println("✅ Dados de exemplo inseridos com sucesso!");
            }
        };
    }
}

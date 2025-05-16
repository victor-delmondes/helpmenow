package com.br.helpmenow.controller;

import com.br.helpmenow.model.*;
import com.br.helpmenow.repository.CategoryRepository;
import com.br.helpmenow.repository.DepartmentRepository;
import com.br.helpmenow.repository.UserRepository;
import com.br.helpmenow.service.TicketService;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/client/index")
public class ClientDashboardController {

    private final TicketService ticketService;
    private final UserRepository userRepository;
    private final DepartmentRepository departmentRepository;
    private final CategoryRepository categoryRepository;

    public ClientDashboardController(TicketService ticketService, UserRepository userRepository, DepartmentRepository departmentRepository, CategoryRepository categoryRepository) {
        this.ticketService = ticketService;
        this.userRepository = userRepository;
        this.departmentRepository = departmentRepository;
        this.categoryRepository = categoryRepository;
    }

    @GetMapping

    public String showDashboard(Model model) {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        UserApp user = userRepository.findByEmail(email).orElseThrow(() -> new RuntimeException("Usuário não encontrado"));
        List<Ticket> tickets = ticketService.GetAllByUser(user);

        model.addAttribute("tickets", tickets);
        model.addAttribute("countOpen", ticketService.countByStatus(TicketStatus.OPEN));
        model.addAttribute("countInProgress", ticketService.countByStatus(TicketStatus.IN_PROGRESS));
        model.addAttribute("countResolved", ticketService.countByStatus(TicketStatus.RESOLVED));
        model.addAttribute("categories", categoryRepository.findAll());

        return "index";
    }

    @PostMapping("/create")
    public String createTicket(
            @RequestParam("ticket_title_input") String title,
            @RequestParam("ticket_description_input") String description,
            @RequestParam("ticket_priority_select") String priority,
            @RequestParam("ticket_category_select") Category category,
           RedirectAttributes redirectAttributes){
        try {
            Ticket ticket = new Ticket();
            ticket.setTitle(title);
            ticket.setDescription(description);
            ticket.setPriority(TicketPriority.valueOf(priority));
            ticket.setStatus(TicketStatus.OPEN);
            ticket.setCategory(category);

            String email = SecurityContextHolder.getContext().getAuthentication().getName();
            UserApp user = userRepository.findByEmail(email).orElseThrow(() -> new RuntimeException("Usuário não encontrado"));
            ticket.setCreatedBy(user);

            ticketService.createTicket(ticket);
            redirectAttributes.addFlashAttribute("success", "Chamado criado com sucesso!");
        }catch (Exception e){
            redirectAttributes.addFlashAttribute("error", "Erro ao criar chamado: " + e.getMessage());
        }
        return "redirect:/client/index";
    }

}

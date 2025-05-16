package com.br.helpmenow.controller;

import com.br.helpmenow.model.Ticket;
import com.br.helpmenow.model.TicketStatus;
import com.br.helpmenow.model.UserApp;
import com.br.helpmenow.repository.UserRepository;
import com.br.helpmenow.service.TicketService;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class ClientTicketController {

    private final TicketService ticketService;
    private final UserRepository userRepository;

    public ClientTicketController(TicketService ticketService, UserRepository userRepository) {
        this.ticketService = ticketService;
        this.userRepository = userRepository;
    }

    @GetMapping("/client/index")

    public String showDashboard(Model model) {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        UserApp user = userRepository.findByEmail(email).orElseThrow(() -> new RuntimeException("Usuário não encontrado"));
        List<Ticket> tickets = ticketService.GetAllByUser(user);

        model.addAttribute("tickets", tickets);
        model.addAttribute("countOpen", ticketService.countByStatus(TicketStatus.OPEN));
        model.addAttribute("countInProgress", ticketService.countByStatus(TicketStatus.IN_PROGRESS));
        model.addAttribute("countResolved", ticketService.countByStatus(TicketStatus.RESOLVED));

        return "index";
    }

}

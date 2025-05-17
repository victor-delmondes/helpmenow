package com.br.helpmenow.controller;

import com.br.helpmenow.model.*;
import com.br.helpmenow.service.TicketService;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Map;

@Controller
@RequestMapping("/client/index")
public class ClientDashboardController {

    private final TicketService ticketService;

    public ClientDashboardController(TicketService ticketService) {
        this.ticketService = ticketService;
    }

    @GetMapping
    public String showDashboard(Model model) {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        Map<String, Object> data = ticketService.getDashboardData(email);
        model.addAllAttributes(data);
        return "index";
    }

    @PostMapping("/create")
    public String createTicket(
            @RequestParam("ticket_title_input") String title,
            @RequestParam("ticket_description_input") String description,
            @RequestParam("ticket_priority_select") String priority,
            @RequestParam("ticket_category_select") Category category,
            RedirectAttributes redirectAttributes
    ) {
        try {
            String email = SecurityContextHolder.getContext().getAuthentication().getName();
            ticketService.createNewTicket(title, description, priority, category, email);
            redirectAttributes.addFlashAttribute("success", "Chamado criado com sucesso!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Erro ao criar chamado: " + e.getMessage());
        }
        return "redirect:/client/index";
    }
}

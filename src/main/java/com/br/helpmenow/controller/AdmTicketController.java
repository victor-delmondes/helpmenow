package com.br.helpmenow.controller;

import com.br.helpmenow.model.Ticket;
import com.br.helpmenow.model.TicketStatus;
import com.br.helpmenow.service.TicketService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class AdmTicketController {

    private final TicketService ticketService;

    public AdmTicketController(TicketService ticketService) {
        this.ticketService = ticketService;
    }

    @GetMapping("/admin/index")
    public String showDashboard(Model model) {
        List<Ticket> tickets = ticketService.getAllTickets();

        model.addAttribute("tickets", tickets);
        model.addAttribute("countOpen", ticketService.countByStatus(TicketStatus.OPEN));
        model.addAttribute("countInProgress", ticketService.countByStatus(TicketStatus.IN_PROGRESS));
        model.addAttribute("countResolved", ticketService.countByStatus(TicketStatus.RESOLVED));

        return "index";
    }
}

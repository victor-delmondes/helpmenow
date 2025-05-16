package com.br.helpmenow.controller;

import com.br.helpmenow.model.Ticket;
import com.br.helpmenow.service.TicketService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping({"/client/ticket", "/admin/ticket"})
public class TicketController {

    private final TicketService ticketService;

    public TicketController(TicketService ticketService) {
        this.ticketService = ticketService;
    }

    @GetMapping("/{id}")
    public String getTicketById(@PathVariable Long id, Model model){
        Ticket ticket = ticketService.findById(id);
        model.addAttribute("ticket", ticket);
        model.addAttribute("assignedTechnicianId", ticket.getAssignedTechnician().getId());
        return "ticket";
    }

}

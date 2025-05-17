package com.br.helpmenow.security;

import com.br.helpmenow.model.Ticket;
import com.br.helpmenow.model.UserType;
import com.br.helpmenow.service.TicketService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component("ticketSecurity")
public class TicketSecurity {

    private final TicketService ticketService;

    public TicketSecurity(TicketService ticketService) {
        this.ticketService = ticketService;
    }

    public boolean canAccessTicket(Long ticketId) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();

        if (userDetails.getUserType() == UserType.ADMIN) {
            return true;
        }else {
            Ticket ticket = ticketService.findById(ticketId);
            return ticket.getCreatedBy().getEmail().equals(userDetails.getUsername());
        }
    }

}

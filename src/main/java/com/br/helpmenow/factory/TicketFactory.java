package com.br.helpmenow.factory;

import com.br.helpmenow.model.*;

public class TicketFactory {

    public static Ticket create(String title, String description, String priority, Category category, UserApp user) {
        Ticket ticket = new Ticket();
        ticket.setTitle(title);
        ticket.setDescription(description);
        ticket.setPriority(TicketPriority.valueOf(priority));
        ticket.setStatus(TicketStatus.OPEN);
        ticket.setCategory(category);
        ticket.setCreatedBy(user);
        return ticket;
    }
}
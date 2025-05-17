package com.br.helpmenow.service;

import com.br.helpmenow.model.Ticket;
import com.br.helpmenow.model.TicketStatus;
import com.br.helpmenow.model.UserApp;
import com.br.helpmenow.repository.TicketRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TicketService {

    private final TicketRepository ticketRepository;

    public TicketService(TicketRepository ticketRepository) {
        this.ticketRepository = ticketRepository;
    }

    public List<Ticket> getAllTickets() {
        return ticketRepository.findAll();
    }

    public List<Ticket> getTicketsByStatus(TicketStatus status) {
        return ticketRepository.findByStatus(status);
    }

    public long countByStatus(TicketStatus status) {
        return ticketRepository.countByStatus(status);
    }

    public Ticket findById(Long id) {
        return ticketRepository.findById(id).orElse(null);
    }

    public List<Ticket>GetAllByUser(UserApp user) {
        return ticketRepository.findByCreatedBy(user);
    }

    public Ticket createTicket(Ticket ticket) {
        return ticketRepository.save(ticket);
    }

    public Ticket updateTicket(Ticket ticket) {
        return ticketRepository.save(ticket);
    }

}

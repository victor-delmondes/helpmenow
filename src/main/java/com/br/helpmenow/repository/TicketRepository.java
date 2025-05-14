package com.br.helpmenow.repository;

import com.br.helpmenow.model.Ticket;
import com.br.helpmenow.model.TicketStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TicketRepository extends JpaRepository<Ticket, Long> {
    List<Ticket> findByStatus(TicketStatus status);
    long countByStatus(TicketStatus status);
}

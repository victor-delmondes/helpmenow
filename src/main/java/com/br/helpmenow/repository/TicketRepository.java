package com.br.helpmenow.repository;

import com.br.helpmenow.model.Ticket;
import com.br.helpmenow.model.TicketStatus;
import com.br.helpmenow.model.UserApp;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TicketRepository extends JpaRepository<Ticket, Long> {
    long countByStatus(TicketStatus status);
    List<Ticket> findByCreatedBy(UserApp createdBy);
}

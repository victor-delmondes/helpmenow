package com.br.helpmenow.service;

import com.br.helpmenow.factory.CommentFactory;
import com.br.helpmenow.factory.TicketFactory;
import com.br.helpmenow.model.*;
import com.br.helpmenow.repository.TicketRepository;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class TicketService {

    private final TicketRepository ticketRepository;
    private final CommentService commentService;
    private final UserAppService userAppService;
    private final CategoryService categoryService;

    public TicketService(TicketRepository ticketRepository, CommentService commentService, UserAppService userAppService, CategoryService categoryService) {
        this.ticketRepository = ticketRepository;
        this.commentService = commentService;
        this.userAppService = userAppService;
        this.categoryService = categoryService;
    }

    public Ticket findById(Long id) {
        return ticketRepository.findById(id).orElseThrow(() -> new RuntimeException("Chamado não encontrado"));
    }

    public List<Ticket> getAllTickets() {
        return ticketRepository.findAll();
    }

    public long countByStatus(TicketStatus status) {
        return ticketRepository.countByStatus(status);
    }

    public void addCommentToTicket(UUID authorId, Long ticketId, String text) {
        Ticket ticket = findById(ticketId);
        UserApp userApp = userAppService.findById(authorId);

        Comment comment = CommentFactory.create(text, userApp, ticket);
        commentService.createComment(comment);
    }

    @PreAuthorize("@ticketSecurity.canAccessTicket(#ticketId)")
    public Map<String, Object> getTicketWithComments(Long ticketId) {
        Ticket ticket = findById(ticketId);
        List<Comment> comments = commentService.findByTicketId(ticketId);
        comments.sort(Comparator.comparing(Comment::getCreatedAt).reversed());

        Map<String, Object> data = new HashMap<>();
        data.put("ticket", ticket);
        data.put("comments", comments);
        return data;
    }

    public void assignTechnicianAndStartTicket(UUID userId, Long ticketId) {
        Ticket ticket = findById(ticketId);
        UserApp userApp = userAppService.findById(userId);
        ticket.setAssignedTechnician(userApp);
        ticket.setStatus(TicketStatus.IN_PROGRESS);
        ticketRepository.save(ticket);
    }

    public void finishTicket(Long ticketId) {
        Ticket ticket = findById(ticketId);
        ticket.setStatus(TicketStatus.RESOLVED);
        ticketRepository.save(ticket);
    }

    public Map<String, Object> getDashboardData(String email) {
        UserApp user = userAppService.findByEmail(email);

        List<Ticket> tickets = ticketRepository.findByCreatedBy(user);
        List<Category> categories = categoryService.findAll();

        Map<String, Object> data = new HashMap<>();
        data.put("tickets", tickets);
        data.put("categories", categories);
        return data;
    }

    public void createNewTicket(String title, String description, String priority, Category category, String email) {
        UserApp user = userAppService.findByEmail(email);
        Ticket ticket = TicketFactory.create(title, description, priority, category, user);
        ticketRepository.save(ticket);
    }




}

package com.br.helpmenow.controller;

import com.br.helpmenow.model.Comment;
import com.br.helpmenow.model.Ticket;
import com.br.helpmenow.model.TicketStatus;
import com.br.helpmenow.model.UserApp;
import com.br.helpmenow.service.CommentService;
import com.br.helpmenow.service.TicketService;
import com.br.helpmenow.service.UserAppService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Comparator;
import java.util.List;

@Controller
@RequestMapping({"/client/ticket", "/admin/ticket"})
public class TicketController {

    private final TicketService ticketService;
    private final CommentService commentService;
    private final UserAppService userAppService;

    public TicketController(TicketService ticketService, CommentService commentService, UserAppService userAppService) {
        this.ticketService = ticketService;
        this.commentService = commentService;
        this.userAppService = userAppService;
    }

    @GetMapping("/{id}")
    public String getTicketWithComments(@PathVariable Long id, Model model) {
        Ticket ticket = ticketService.findById(id);
        List<Comment> comments = commentService.findByTicketId(id);

        comments.sort(Comparator.comparing(Comment::getCreatedAt).reversed());

        model.addAttribute("ticket", ticket);
        model.addAttribute("comments", comments);

        return "ticket";
    }

    @PostMapping("/{userType}-make-comment")
    public String makeComment(
            @PathVariable String userType,
            @RequestParam("user_id_comment") Long authorId,
            @RequestParam("ticket_id_comment") Long ticketId,
            @RequestParam("text_input") String text,
            RedirectAttributes redirectAttributes
    ) {
        try {
            Ticket ticket = ticketService.findById(ticketId);
            UserApp userApp = userAppService.findById(authorId);

            Comment comment = new Comment();
            comment.setAuthor(userApp);
            comment.setText(text);
            comment.setTicket(ticket);

            commentService.createComment(comment);
            redirectAttributes.addFlashAttribute("success", "Comentário criado com sucesso!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Erro ao criar comentário: " + e.getMessage());
        }
        return "redirect:/" + userType.toLowerCase() + "/ticket/" + ticketId;
    }

    @PostMapping("/get-ticket")
    public String getTicket(
            @RequestParam("start_button_user_id") Long userId,
            @RequestParam("start_button_ticket_id") Long ticketId,
            RedirectAttributes redirectAttributes
    ) {
        try {
            Ticket ticket = ticketService.findById(ticketId);
            UserApp userApp = userAppService.findById(userId);

            ticket.setAssignedTechnician(userApp);
            ticket.setStatus(TicketStatus.IN_PROGRESS);
            ticketService.updateTicket(ticket);

            redirectAttributes.addFlashAttribute("success", "Ticket atualizado com sucesso!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Erro ao atualizar o Ticket!" + e.getMessage());
        }
        return "redirect:/admin/ticket/" + ticketId;
    }

    @PostMapping("/finish-ticket")
    public String finishTicket(
            @RequestParam("finish_button_ticket_id") Long ticketId,
            RedirectAttributes redirectAttributes
    ) {
        try {
            Ticket ticket = ticketService.findById(ticketId);

            ticket.setStatus(TicketStatus.RESOLVED);
            ticketService.updateTicket(ticket);

            redirectAttributes.addFlashAttribute("success", "Ticket atualizado com sucesso!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Erro ao atualizar o Ticket!" + e.getMessage());
        }
        return "redirect:/admin/ticket/" + ticketId;
    }

}

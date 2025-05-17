package com.br.helpmenow.controller;

import com.br.helpmenow.service.TicketService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Map;
import java.util.UUID;

@Controller
@RequestMapping({"/client/ticket", "/admin/ticket"})
public class TicketController {

    private final TicketService ticketService;

    public TicketController(TicketService ticketService) {
        this.ticketService = ticketService;
    }

    @GetMapping("/{id}")
    public String getTicketWithComments(@PathVariable Long id, Model model) {
        Map<String, Object> data = ticketService.getTicketWithComments(id);
        model.addAllAttributes(data);
        return "ticket";
    }

    @PostMapping("/{userType}-make-comment")
    public String makeComment(
            @PathVariable String userType,
            @RequestParam("user_id_comment") UUID authorId,
            @RequestParam("ticket_id_comment") Long ticketId,
            @RequestParam("text_input") String text,
            RedirectAttributes redirectAttributes
    ) {
        try {
            ticketService.addCommentToTicket(authorId, ticketId, text);
            redirectAttributes.addFlashAttribute("success", "Comentário criado com sucesso!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Erro ao criar comentário: " + e.getMessage());
        }
        return "redirect:/" + userType.toLowerCase() + "/ticket/" + ticketId;
    }

    @PostMapping("/get-ticket")
    public String getTicket(
            @RequestParam("start_button_user_id") UUID userId,
            @RequestParam("start_button_ticket_id") Long ticketId,
            RedirectAttributes redirectAttributes
    ) {
        try {
            ticketService.assignTechnicianAndStartTicket(userId, ticketId);
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Erro ao iniciar chamado: " + e.getMessage());
        }
        return "redirect:/admin/ticket/" + ticketId;
    }

    @PostMapping("/finish-ticket")
    public String finishTicket(
            @RequestParam("finish_button_ticket_id") Long ticketId,
            RedirectAttributes redirectAttributes
    ) {
        try {
            ticketService.finishTicket(ticketId);
            redirectAttributes.addFlashAttribute("success", "Ticket atualizado com sucesso!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Erro ao atualizar o Ticket!" + e.getMessage());
        }
        return "redirect:/admin/ticket/" + ticketId;
    }

}

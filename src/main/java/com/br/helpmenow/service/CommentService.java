package com.br.helpmenow.service;

import com.br.helpmenow.model.Comment;
import com.br.helpmenow.model.Ticket;
import com.br.helpmenow.repository.CommentRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommentService {

    private final CommentRepository commentRepository;

    public CommentService(CommentRepository commentRepository) {
        this.commentRepository = commentRepository;
    }

    public List<Comment> findAll() {
        return commentRepository.findAll();
    }

    public Comment findById(Long id) {
        return commentRepository.findById(id).orElse(null);
    }

    public List<Comment> findByTicketId(Long ticketId) {
        return commentRepository.findByTicketId(ticketId);
    }

    public Comment createComment(Comment comment) {
        return commentRepository.save(comment);
    }

}

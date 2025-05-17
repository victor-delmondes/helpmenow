package com.br.helpmenow.factory;

import com.br.helpmenow.model.Comment;
import com.br.helpmenow.model.Ticket;
import com.br.helpmenow.model.UserApp;

public class CommentFactory {

    public static Comment create(String text, UserApp author, Ticket ticket) {
        Comment comment = new Comment();
        comment.setText(text);
        comment.setAuthor(author);
        comment.setTicket(ticket);
        return comment;
    }
}
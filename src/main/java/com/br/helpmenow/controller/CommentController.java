package com.br.helpmenow.controller;

import com.br.helpmenow.model.Comment;
import com.br.helpmenow.service.CommentService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/admin/comments")
public class CommentController {

    private final CommentService commentService;

    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @GetMapping
    public String listComments(Model model) {
        model.addAttribute("comments", commentService.findAll());
        return "comments";
    }

    @PostMapping("/add")
    public String addComment(@ModelAttribute Comment comment) {
        commentService.save(comment);
        return "redirect:/admin/comments";
    }

    @GetMapping("/{id}")
    @ResponseBody
    public Comment getById(@PathVariable Long id) {
        return commentService.findById(id);
    }
}

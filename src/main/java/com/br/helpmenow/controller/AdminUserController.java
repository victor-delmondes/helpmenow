package com.br.helpmenow.controller;

import com.br.helpmenow.model.UserApp;
import com.br.helpmenow.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Controller
public class AdminUserController {

    private final UserService userService;

    public AdminUserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/admin/users")
    public String showUsers(Model model) {
        List<UserApp> users = userService.getAllUsers();
        model.addAttribute("users", users);
        return "users";
    }

    @PutMapping("/admin/users/{id}/toggle")
    @ResponseBody
    public ResponseEntity<Void> toggleUserStatus(@PathVariable Long id, @RequestBody Map<String, Boolean> payload) {
        boolean active = payload.get("active");
        userService.updateUserStatus(id, active);
        return ResponseEntity.ok().build();
    }

}

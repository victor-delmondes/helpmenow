package com.br.helpmenow.controller;


import com.br.helpmenow.service.DepartmentService;
import com.br.helpmenow.service.UserAppService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.UUID;

@Controller
@RequestMapping("/admin/users")
public class UserAppController {

    private final UserAppService userAppService;
    private final DepartmentService departmentService;

    public UserAppController(UserAppService userAppService, DepartmentService departmentService) {
        this.userAppService = userAppService;
        this.departmentService = departmentService;

    }

    @GetMapping
    public String listUsers(Model model) {
        model.addAttribute("users", userAppService.findAll());
        model.addAttribute("departments", departmentService.findAll());
        return "users";
    }

    @PostMapping("/create")
    public String createUser(
            @RequestParam("user_email_input") String email,
            @RequestParam("password_name_input") String password,
            @RequestParam("user_name_input") String name,
            @RequestParam("department_user_select") Long departmentId,
            @RequestParam("type_user_select") String userType,
            RedirectAttributes redirectAttributes
    ) {
        try {
            userAppService.createNewUser(email, password, name, departmentId, userType);
            redirectAttributes.addAttribute("success", "Usuário criado com sucesso!");
        } catch (Exception e) {
            redirectAttributes.addAttribute("error", "Erro ao criar usuário: " + e.getMessage());
        }
        return "redirect:/admin/users";
    }


    @PostMapping("/update")
    public String updateUser(
            @RequestParam("edit_user_id_input") UUID id,
            @RequestParam("edit_user_email_input") String email,
            @RequestParam("edit_user_name_input") String name,
            @RequestParam("edit_department_user_select") Long departmentId,
            @RequestParam("edit_type_user_select") String userType,
            RedirectAttributes redirectAttributes
    ) {
        try {
            userAppService.updateUserDetails(id, email, name, departmentId, userType);
            redirectAttributes.addAttribute("success", "Usuário atualizado com sucesso!");
        } catch (Exception e) {
            redirectAttributes.addAttribute("error", "Erro ao atualizar usuário: " + e.getMessage());
        }
        return "redirect:/admin/users";
    }


    @PostMapping("/change-password")
    public String changePassword
            (@RequestParam("change_password_user_id") UUID userId,
             @RequestParam("edit_password_input") String newPassword,
             RedirectAttributes redirectAttributes) {
        try {
            userAppService.updateUserPassword(userId, newPassword);
            redirectAttributes.addAttribute("success", "Senha alterada com sucesso!");
        } catch (Exception e) {
            redirectAttributes.addAttribute("error", "Erro ao alterar senha: " + e.getMessage());
        }
        return "redirect:/admin/users";
    }

    @PostMapping("/toggle-status")
    @ResponseBody
    public ResponseEntity<String> toggleUserStatus(@RequestParam UUID userId) {
        try {
            boolean active = userAppService.toggleUserStatus(userId);
            return ResponseEntity.ok(active ? "Ativo" : "Inativo");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Erro ao atualizar: " + e.getMessage());
        }
    }

}

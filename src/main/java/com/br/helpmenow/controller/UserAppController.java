package com.br.helpmenow.controller;

import com.br.helpmenow.model.Department;
import com.br.helpmenow.model.UserApp;
import com.br.helpmenow.model.UserType;
import com.br.helpmenow.service.DepartmentService;
import com.br.helpmenow.service.UserAppService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/admin/users")
public class UserAppController {

    private final UserAppService userAppService;
    private final DepartmentService departmentService;
    private final BCryptPasswordEncoder passwordEncoder;

    public UserAppController(UserAppService userAppService, DepartmentService departmentService, BCryptPasswordEncoder passwordEncoder) {
        this.userAppService = userAppService;
        this.departmentService = departmentService;
        this.passwordEncoder = passwordEncoder;
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
            Model model
    ) {
        try {
            Department department = departmentService.findById(departmentId);

            UserApp user = new UserApp();
            user.setEmail(email);
            user.setPassword(passwordEncoder.encode(password));
            user.setName(name);
            user.setType(UserType.valueOf(userType));
            user.setDepartment(department);
            user.setActive(true); // Ativo por padrão

            userAppService.createUser(user);
            model.addAttribute("success", "Usuário criado com sucesso!");
        } catch (Exception e) {
            model.addAttribute("error", "Erro ao criar usuário: " + e.getMessage());
        }

        return "redirect:/admin/users";
    }


    @PostMapping("/update")
    public String updateUser(
            @RequestParam("edit_user_id_input") Long id,
            @RequestParam("edit_user_email_input") String email,
            @RequestParam("edit_user_name_input") String name,
            @RequestParam("edit_department_user_select") Long departmentId,
            @RequestParam("edit_type_user_select") String userType,
            Model model
    ) {
        try {
            UserApp user = userAppService.findById(id);
            Department department = departmentService.findById(departmentId);

            user.setEmail(email);
            user.setName(name);
            user.setType(UserType.valueOf(userType));
            user.setDepartment(department);

            userAppService.updateUser(user);
            model.addAttribute("success", "Usuário atualizado com sucesso!");
        } catch (Exception e) {
            model.addAttribute("error", "Erro ao atualizar usuário: " + e.getMessage());
        }

        return "redirect:/admin/users";
    }

    @PostMapping("/change-password")
    public String changePassword(
            @RequestParam("user_id") Long userId,
            @RequestParam("edit_password_input") String newPassword,
            Model model
    ) {
        try {
            UserApp user = userAppService.findById(userId);
            user.setPassword(passwordEncoder.encode(newPassword));
            userAppService.updateUser(user);

            model.addAttribute("success", "Senha alterada com sucesso!");
        } catch (Exception e) {
            model.addAttribute("error", "Erro ao alterar senha: " + e.getMessage());
        }

        return "redirect:/admin/users";
    }

    @PostMapping("/toggle-status")
    @ResponseBody
    public ResponseEntity<String> toggleUserStatus(@RequestParam Long userId) {
        try {
            UserApp user = userAppService.findById(userId);
            user.setActive(!user.isActive());
            userAppService.updateUser(user);

            return ResponseEntity.ok(user.isActive() ? "Ativo" : "Inativo");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Erro ao atualizar: " + e.getMessage());
        }
    }

    @GetMapping("/{id}")
    @ResponseBody
    public UserApp getUserById(@PathVariable Long id) {
        return userAppService.findById(id);
    }
}

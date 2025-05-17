package com.br.helpmenow.controller;

import com.br.helpmenow.model.Department;
import com.br.helpmenow.service.DepartmentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/admin/departments")
public class DepartmentController {

    private final DepartmentService departmentService;

    public DepartmentController(DepartmentService departmentService) {
        this.departmentService = departmentService;
    }

    @GetMapping
    public String listDepartments(Model model) {
        model.addAttribute("departments", departmentService.findAll());
        return "departments";
    }

    @PostMapping("/create")
    public String createDepartment(
            @RequestParam("department_name_input") String name,
            @RequestParam("department_ramal_input") String extension,
            @RequestParam("department_location_input") String location,
            RedirectAttributes redirectAttributes) {
        try {
            Department dep = new Department(name, extension, location);
            departmentService.create(dep);
            redirectAttributes.addFlashAttribute("success", "Departamento criado com sucesso!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Erro ao criar departamento: " + e.getMessage());
        }
        return "redirect:/admin/departments";
    }

    @PostMapping("/update")
    public String updateDepartment(
            @RequestParam("edit_department_id_input") Long id,
            @RequestParam("edit_department_name_input") String name,
            @RequestParam("edit_department_ramal_input") String extension,
            @RequestParam("edit_department_location_input") String location,
            RedirectAttributes redirectAttributes) {
        try {
            Department dep = departmentService.findById(id);
            dep.setName(name);
            dep.setExtension(extension);
            dep.setLocation(location);
            departmentService.update(dep);
            redirectAttributes.addFlashAttribute("success", "Departamento atualizado com sucesso!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Erro ao atualizar departamento: " + e.getMessage());
        }
        return "redirect:/admin/departments";
    }

    @PostMapping("/toggle-status")
    @ResponseBody
    public ResponseEntity<String> toggleDepartmentStatus(@RequestParam Long departmentId) {
        try {
            Department dep = departmentService.findById(departmentId);
            dep.setStatus(!dep.isStatus());
            departmentService.update(dep);

            return ResponseEntity.ok(dep.isStatus() ? "Ativo" : "Inativo");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Erro ao atualizar: " + e.getMessage());
        }
    }

}

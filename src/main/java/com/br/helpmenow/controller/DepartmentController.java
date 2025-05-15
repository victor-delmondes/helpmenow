package com.br.helpmenow.controller;

import com.br.helpmenow.model.Department;
import com.br.helpmenow.service.DepartmentService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

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

    @PostMapping("/add")
    public String addDepartment(@ModelAttribute Department department) {
        departmentService.save(department);
        return "redirect:/admin/departments";
    }

    @PostMapping("/edit")
    public String editDepartment(@ModelAttribute Department department) {
        departmentService.update(department);
        return "redirect:/admin/departments";
    }

    @PostMapping("/toggle/{id}")
    public String toggleStatus(@PathVariable Long id) {
        departmentService.toggleStatus(id);
        return "redirect:/admin/departments";
    }

    @GetMapping("/{id}")
    @ResponseBody
    public Department getById(@PathVariable Long id) {
        return departmentService.findById(id);
    }
}

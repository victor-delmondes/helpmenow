package com.br.helpmenow.controller;

import com.br.helpmenow.model.Category;
import com.br.helpmenow.service.CategoryService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/admin/categories")
public class CategoryController {

    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping
    public String listCategories(Model model) {
        model.addAttribute("categories", categoryService.findAll());
        return "categories";
    }

    @GetMapping("/{id}")
    @ResponseBody
    public Category getById(@PathVariable Long id) {
        return categoryService.findById(id);
    }

    @PostMapping("/create")
    public String createCategory(
            @RequestParam("category_name_input") String name,
            RedirectAttributes redirectAttributes) {
        try {
            Category cat = new Category();
            cat.setName(name);
            cat.setStatus(true);
            categoryService.create(cat);
            redirectAttributes.addFlashAttribute("success", "Categoria criada com sucesso!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Erro ao criar categoria: " + e.getMessage());
        }
        return "redirect:/admin/categories";
    }

    @PostMapping("/update")
    public String updateCategory(
            @RequestParam("edit_category_id_input") Long id,
            @RequestParam("edit_category_name_input") String name,
            RedirectAttributes redirectAttributes) {
        try {
            Category cat = categoryService.findById(id);
            cat.setName(name);
            categoryService.update(cat);
            redirectAttributes.addFlashAttribute("success", "Categoria atualizada com sucesso!");
        }catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Erro ao atualizar categoria: " + e.getMessage());
        }
        return "redirect:/admin/categories";
    }

    @PostMapping("/toggle-status")
    @ResponseBody
    public ResponseEntity<String> toggleCategoryStatus(@RequestParam Long categoryId) {
        try {
            Category cat = categoryService.findById(categoryId);
            cat.setStatus(!cat.isStatus());
            categoryService.update(cat);
            return ResponseEntity.ok(cat.isStatus() ? "Ativo" : "Inativo");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Erro ao atualizar: " + e.getMessage());
        }
    }

}

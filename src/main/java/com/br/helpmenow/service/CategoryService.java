package com.br.helpmenow.service;

import com.br.helpmenow.model.Category;
import com.br.helpmenow.repository.CategoryRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CategoryService {

    private final CategoryRepository categoryRepository;

    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    public List<Category> findAll() {
        return categoryRepository.findAll();
    }

    public Category findById(Long id) {
        return categoryRepository.findById(id).orElseThrow(() -> new RuntimeException("Categoria não encontrada"));
    }

    @Transactional
    public void createNewCategory(String name) {
        Category category = new Category();
        category.setName(name);
        category.setStatus(true);
        categoryRepository.save(category);
    }

    @Transactional
    public void updateCategory(Long id, String name) {
        Category category = findById(id);
        category.setName(name);
        categoryRepository.save(category);
    }

    @Transactional
    public boolean toggleStatus(Long id) {
        Category category = findById(id);
        category.setStatus(!category.isStatus());
        categoryRepository.save(category);
        return category.isStatus();
    }

}

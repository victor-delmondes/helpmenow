package com.br.helpmenow.service;

import com.br.helpmenow.factory.CategoryFactory;
import com.br.helpmenow.model.Category;
import com.br.helpmenow.repository.CategoryRepository;
import org.springframework.stereotype.Service;

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

    public void createNewCategory(String name) {
        Category category = CategoryFactory.create(name);
        categoryRepository.save(category);
    }

    public void updateCategory(Long id, String name) {
        Category category = findById(id);
        category.setName(name);
        categoryRepository.save(category);
    }

    public boolean toggleStatus(Long id) {
        Category category = findById(id);
        category.setStatus(!category.isStatus());
        categoryRepository.save(category);
        return category.isStatus();
    }

}

package com.br.helpmenow.factory;

import com.br.helpmenow.model.Category;

public class CategoryFactory {

    public static Category create(String name) {
        Category category = new Category();
        category.setName(name);
        category.setStatus(true);
        return category;
    }
}
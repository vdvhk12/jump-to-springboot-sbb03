package org.example.backend.domain.util;

import org.example.backend.domain.category.entity.Category;
import org.example.backend.domain.category.form.CategoryForm;

public class CategoryUtils {

    public static CategoryForm createTestCategoryForm(String name) {
        CategoryForm form = new CategoryForm();
        form.setName(name);
        return form;
    }

    public static Category createTestCategory(Long questionId, CategoryForm categoryForm) {
        return Category.builder()
            .id(questionId)
            .name(categoryForm.getName())
            .build();
    }
}

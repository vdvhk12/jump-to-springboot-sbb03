package org.example.backend.domain.util;

import java.time.LocalDateTime;
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

    public static Category updateTestCategory(Category category, CategoryForm categoryForm) {
        return category.toBuilder()
            .name(categoryForm.getName())
            .updatedAt(LocalDateTime.now())
            .build();
    }
}

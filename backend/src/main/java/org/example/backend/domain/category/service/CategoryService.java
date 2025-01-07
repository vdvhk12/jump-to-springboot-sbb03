package org.example.backend.domain.category.service;

import java.time.LocalDateTime;
import lombok.RequiredArgsConstructor;
import org.example.backend.domain.category.dto.CategoryDto;
import org.example.backend.domain.category.entity.Category;
import org.example.backend.domain.category.form.CategoryForm;
import org.example.backend.domain.category.repository.CategoryRepository;
import org.example.backend.global.exception.DataNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CategoryService {

    private final CategoryRepository categoryRepository;

    public CategoryDto createCategory(CategoryForm categoryForm) {
        return CategoryDto.fromCategory(categoryRepository.save(Category.of(categoryForm)));
    }

    public CategoryDto updateCategory(Long categoryId, CategoryForm categoryForm) {
        Category category = getCategoryOrThrow(categoryId);
        return CategoryDto.fromCategory(categoryRepository.save(category.toBuilder()
            .name(categoryForm.getName())
            .updatedAt(LocalDateTime.now())
            .build()));
    }

    private Category getCategoryOrThrow(Long categoryId) {
        return categoryRepository.findById(categoryId)
            .orElseThrow(() -> new DataNotFoundException("Category not found"));
    }
}

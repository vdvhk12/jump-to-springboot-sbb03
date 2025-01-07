package org.example.backend.domain.category.service;

import lombok.RequiredArgsConstructor;
import org.example.backend.domain.category.dto.CategoryDto;
import org.example.backend.domain.category.entity.Category;
import org.example.backend.domain.category.form.CategoryForm;
import org.example.backend.domain.category.repository.CategoryRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CategoryService {

    private final CategoryRepository categoryRepository;

    public CategoryDto createCategory(CategoryForm categoryForm) {
        return CategoryDto.fromCategory(categoryRepository.save(Category.of(categoryForm)));
    }
}

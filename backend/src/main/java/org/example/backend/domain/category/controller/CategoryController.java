package org.example.backend.domain.category.controller;

import lombok.RequiredArgsConstructor;
import org.example.backend.domain.category.dto.CategoryDto;
import org.example.backend.domain.category.form.CategoryForm;
import org.example.backend.domain.category.service.CategoryService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/category")
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;

    @PostMapping("/create")
    public ResponseEntity<CategoryDto> createCategory(@RequestBody CategoryForm categoryForm) {
        CategoryDto category = categoryService.createCategory(categoryForm);
        return ResponseEntity.status(HttpStatus.CREATED).body(category);
    }

}

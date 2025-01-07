package org.example.backend.domain.category.service;

import static org.assertj.core.api.Assertions.*;
import static org.example.backend.domain.util.CategoryUtils.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import org.example.backend.domain.category.dto.CategoryDto;
import org.example.backend.domain.category.entity.Category;
import org.example.backend.domain.category.form.CategoryForm;
import org.example.backend.domain.category.repository.CategoryRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class CategoryServiceTest {

    @Mock
    private CategoryRepository categoryRepository;

    @InjectMocks
    private CategoryService categoryService;

    @Test
    @DisplayName("create category")
    void t1() {
        //given
        CategoryForm categoryForm = createTestCategoryForm("category1");
        Category category = createTestCategory(1L, categoryForm);

        when(categoryRepository.save(any(Category.class))).thenReturn(category);

        //when
        CategoryDto result = categoryService.createCategory(categoryForm);

        //then
        assertThat(result).isNotNull();
        assertThat(result.getId()).isEqualTo(category.getId());
        assertThat(result.getName()).isEqualTo(category.getName());
    }

}
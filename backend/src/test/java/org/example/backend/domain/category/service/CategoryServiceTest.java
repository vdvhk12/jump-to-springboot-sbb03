package org.example.backend.domain.category.service;

import static org.assertj.core.api.Assertions.*;
import static org.example.backend.domain.util.CategoryUtils.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;
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
    @DisplayName("category create")
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

    @Test
    @DisplayName("category update")
    void t2() {
        //given
        CategoryForm createForm = createTestCategoryForm("category1");
        CategoryForm updateForm = createTestCategoryForm("updateCategory1");

        Category category = createTestCategory(1L, createForm);
        Category updateCategory = updateTestCategory(category, updateForm);

        when(categoryRepository.findById(any(Long.class))).thenReturn(Optional.of(category));
        when(categoryRepository.save(any(Category.class))).thenReturn(updateCategory);

        //when
        CategoryDto result = categoryService.updateCategory(category.getId(), updateForm);

        //then
        assertThat(result).isNotNull();
        assertThat(result.getName()).isEqualTo(updateForm.getName());
    }

    @Test
    @DisplayName("category delete")
    void t3() {
        //given
        CategoryForm createForm = createTestCategoryForm("category1");
        Category category = createTestCategory(1L, createForm);

        when(categoryRepository.findById(any(Long.class))).thenReturn(Optional.of(category));

        //when
        categoryService.deleteCategory(category.getId());

        //then
        verify(categoryRepository, times(1)).delete(category);
    }

}
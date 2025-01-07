package org.example.backend.domain.category.repository;

import static org.assertj.core.api.Assertions.*;
import static org.example.backend.domain.util.CategoryUtils.*;
import static org.mockito.Mockito.when;

import java.util.List;
import org.example.backend.domain.category.dto.CategoryDto;
import org.example.backend.domain.category.entity.Category;
import org.example.backend.domain.category.form.CategoryForm;
import org.example.backend.global.exception.DataNotFoundException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
class CategoryRepositoryTest {

    @Autowired
    private CategoryRepository categoryRepository;

    @Test
    @DisplayName("category create")
    void t1() {
        //given
        CategoryForm categoryFrom = createTestCategoryForm("test category1");

        //when
        Category result = categoryRepository.save(Category.of(categoryFrom));

        //then
        assertThat(result.getId()).isNotNull();
        assertThat(result.getName()).isEqualTo(categoryFrom.getName());
    }

    @Test
    @DisplayName("category update")
    void t2() {
        //given
        CategoryForm categoryFrom = createTestCategoryForm("test category1");

        //when
        Category result = categoryRepository.save(Category.of(categoryFrom));

        //then
        assertThat(result.getId()).isNotNull();
        assertThat(result.getName()).isEqualTo(categoryFrom.getName());
    }

    @Test
    @DisplayName("category delete")
    void t3() {
        //given
        CategoryForm categoryFrom = createTestCategoryForm("test category1");
        Category category = categoryRepository.save(Category.of(categoryFrom));

        //when
        categoryRepository.findById(category.getId()).ifPresent(c -> categoryRepository.delete(c));

        //then
        assertThatThrownBy(() -> categoryRepository.findById(category.getId())
            .orElseThrow(() -> new DataNotFoundException("Question not found")))
            .isInstanceOf(DataNotFoundException.class).hasMessageContaining("Question not found");
    }

    @Test
    @DisplayName("get all categories")
    void t4() {
        //given
        CategoryForm categoryFrom = createTestCategoryForm("test category1");
        CategoryForm categoryFrom2 = createTestCategoryForm("test category2");

        Category category = categoryRepository.save(Category.of(categoryFrom));
        Category category2 = categoryRepository.save(Category.of(categoryFrom2));

        //when
        List<Category> result = categoryRepository.findAll();

        //then
        assertThat(result).hasSize(2);
        assertThat(result.get(0).getName()).isEqualTo(categoryFrom.getName());
        assertThat(result.get(1).getName()).isEqualTo(categoryFrom2.getName());
    }


}
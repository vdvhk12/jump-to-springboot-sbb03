package org.example.backend.domain.category.repository;

import static org.assertj.core.api.Assertions.*;
import static org.example.backend.domain.util.CategoryUtils.*;

import org.example.backend.domain.category.entity.Category;
import org.example.backend.domain.category.form.CategoryForm;
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

}
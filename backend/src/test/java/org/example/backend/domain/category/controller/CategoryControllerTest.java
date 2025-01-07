package org.example.backend.domain.category.controller;

import static org.example.backend.domain.util.CategoryUtils.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.backend.domain.category.dto.CategoryDto;
import org.example.backend.domain.category.entity.Category;
import org.example.backend.domain.category.form.CategoryForm;
import org.example.backend.domain.category.service.CategoryService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

@WebMvcTest(CategoryController.class)
class CategoryControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private CategoryService categoryService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @DisplayName("POST /api/category/create")
    void t1() throws Exception {
        //given
        String url = "/api/category/create";
        CategoryForm categoryForm = createTestCategoryForm("category1");
        Category category = createTestCategory(1L, categoryForm);
        CategoryDto categoryDto = CategoryDto.fromCategory(category);

        when(categoryService.createCategory(any(CategoryForm.class))).thenReturn(categoryDto);

        //when
        ResultActions resultActions = mockMvc.perform(post(url)
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(categoryForm)));

        //then
        resultActions
            .andExpect(status().isCreated())
            .andExpect(jsonPath("$.id").value(categoryDto.getId()))
            .andExpect(jsonPath("$.name").value(categoryDto.getName()));
    }
}
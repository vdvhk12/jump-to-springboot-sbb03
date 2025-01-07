package org.example.backend.domain.question.controller;

import static org.example.backend.domain.util.CategoryUtils.createTestCategory;
import static org.example.backend.domain.util.CategoryUtils.createTestCategoryForm;
import static org.example.backend.domain.util.QuestionUtils.createTestQuestion;
import static org.example.backend.domain.util.QuestionUtils.createTestQuestionForm;
import static org.example.backend.domain.util.QuestionUtils.updateTestQuestion;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.backend.domain.category.entity.Category;
import org.example.backend.domain.category.form.CategoryForm;
import org.example.backend.domain.question.dto.QuestionDto;
import org.example.backend.domain.question.entity.Question;
import org.example.backend.domain.question.form.QuestionForm;
import org.example.backend.domain.question.service.QuestionService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

@WebMvcTest(QuestionController.class)
class QuestionControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private QuestionService questionService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @DisplayName("POST /api/question/create")
    void createQuestion() throws Exception {
        //given
        String url = "/api/question/create";
        CategoryForm categoryForm = createTestCategoryForm("category");
        Category category = createTestCategory(1L, categoryForm);

        QuestionForm questionForm = createTestQuestionForm(category.getId(), "test subject", "test content");
        Question question = createTestQuestion(1L, questionForm, category);
        QuestionDto questionDto = QuestionDto.fromQuestion(question);

        when(questionService.createQuestion(any(QuestionForm.class))).thenReturn(questionDto);

        //when
        ResultActions resultActions = mockMvc.perform(post(url)
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(questionForm)));

        //then
        resultActions
            .andExpect(status().isCreated())
            .andExpect(jsonPath("$.id").value(questionDto.getId()))
            .andExpect(jsonPath("$.subject").value(questionDto.getSubject()))
            .andExpect(jsonPath("$.content").value(questionDto.getContent()));
    }

    @Test
    @DisplayName("PATCH /api/question/{id}")
    void updateQuestion() throws Exception {
        //given
        String url = "/api/question/1";
        CategoryForm categoryForm = createTestCategoryForm("category");
        Category category = createTestCategory(1L, categoryForm);

        QuestionForm questionForm = createTestQuestionForm(category.getId(), "test subject", "test content");
        QuestionForm updateForm = createTestQuestionForm(category.getId(), "update subject", "update content");

        Question question = createTestQuestion(1L, questionForm, category);
        Question updateQuestion = updateTestQuestion(question, updateForm, category);

        QuestionDto questionDto = QuestionDto.fromQuestion(updateQuestion);

        when(questionService.updateQuestion(any(Long.class), any(QuestionForm.class))).thenReturn(questionDto);

        //when
        ResultActions resultActions = mockMvc.perform(patch(url)
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(questionForm)));

        //then
        resultActions
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.id").value(questionDto.getId()))
            .andExpect(jsonPath("$.subject").value(questionDto.getSubject()))
            .andExpect(jsonPath("$.content").value(questionDto.getContent()));
    }

    @Test
    @DisplayName("DELETE /api/question/{id}")
    void deleteQuestion() throws Exception {
        //given
        String url = "/api/question/1";
        CategoryForm categoryForm = createTestCategoryForm("category");
        Category category = createTestCategory(1L, categoryForm);

        QuestionForm questionForm = createTestQuestionForm(category.getId(), "test subject", "test content");
        createTestQuestion(1L, questionForm, category);

        //when
        ResultActions resultActions = mockMvc.perform(delete(url));

        //then
        resultActions.andExpect(status().isNoContent());
    }
}
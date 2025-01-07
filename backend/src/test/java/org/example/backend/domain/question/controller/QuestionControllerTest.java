package org.example.backend.domain.question.controller;

import static org.example.backend.domain.util.CategoryUtils.createTestCategory;
import static org.example.backend.domain.util.CategoryUtils.createTestCategoryForm;
import static org.example.backend.domain.util.QuestionUtils.createTestQuestion;
import static org.example.backend.domain.util.QuestionUtils.createTestQuestionForm;
import static org.example.backend.domain.util.QuestionUtils.updateTestQuestion;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.List;
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
    void t1() throws Exception {
        //given
        String url = "/api/question/create";
        CategoryForm categoryForm = createTestCategoryForm("category");
        Category category = createTestCategory(1L, categoryForm);

        QuestionForm questionForm = createTestQuestionForm(category.getId(), "subject", "content");
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
    @DisplayName("GET /api/question/list")
    void t2() throws Exception {
        //given
        String url = "/api/question/list";
        CategoryForm categoryForm = createTestCategoryForm("category");
        Category category = createTestCategory(1L, categoryForm);

        QuestionForm questionForm1 = createTestQuestionForm(category.getId(), "subject1", "content1");
        Question question1 = createTestQuestion(1L, questionForm1, category);
        QuestionDto questionDto1 = QuestionDto.fromQuestion(question1);

        QuestionForm questionForm2 = createTestQuestionForm(category.getId(), "subject2", "content2");
        Question question2 = createTestQuestion(2L, questionForm2, category);
        QuestionDto questionDto2 = QuestionDto.fromQuestion(question2);

        List<QuestionDto> questions = List.of(questionDto1, questionDto2);
        when(questionService.getAllQuestions()).thenReturn(questions);

        //when
        ResultActions resultActions = mockMvc.perform(get(url));

        //then
        resultActions
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.length()").value(2))
            .andExpect(jsonPath("$[0].id").value(question1.getId()))
            .andExpect(jsonPath("$[0].category.name").value(category.getName()))
            .andExpect(jsonPath("$[0].subject").value(question1.getSubject()))
            .andExpect(jsonPath("$[0].content").value(question1.getContent()))
            .andExpect(jsonPath("$[1].subject").value(question2.getSubject()))
            .andExpect(jsonPath("$[1].content").value(question2.getContent()));

    }

    @Test
    @DisplayName("GET /api/question/{id}")
    void t3() throws Exception {
        //given
        String url = "/api/question/1";
        CategoryForm categoryForm = createTestCategoryForm("category");
        Category category = createTestCategory(1L, categoryForm);

        QuestionForm questionForm = createTestQuestionForm(category.getId(), "subject", "content");
        Question question = createTestQuestion(1L, questionForm, category);

        QuestionDto questionDto = QuestionDto.fromQuestion(question);
        when(questionService.getQuestion(questionDto.getId())).thenReturn(questionDto);

        //when
        ResultActions resultActions = mockMvc.perform(get(url));

        //then
        resultActions
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.id").value(questionDto.getId()))
            .andExpect(jsonPath("$.category.id").value(category.getId()))
            .andExpect(jsonPath("$.category.name").value(category.getName()))
            .andExpect(jsonPath("$.subject").value(question.getSubject()))
            .andExpect(jsonPath("$.content").value(question.getContent()));
    }

    @Test
    @DisplayName("PATCH /api/question/{id}")
    void t4() throws Exception {
        //given
        String url = "/api/question/1";
        CategoryForm categoryForm = createTestCategoryForm("category");
        Category category = createTestCategory(1L, categoryForm);

        QuestionForm questionForm = createTestQuestionForm(category.getId(), "subject", "content");
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
    void t5() throws Exception {
        //given
        String url = "/api/question/1";
        CategoryForm categoryForm = createTestCategoryForm("category");
        Category category = createTestCategory(1L, categoryForm);

        QuestionForm questionForm = createTestQuestionForm(category.getId(), "subject", "content");
        createTestQuestion(1L, questionForm, category);

        //when
        ResultActions resultActions = mockMvc.perform(delete(url));

        //then
        resultActions.andExpect(status().isNoContent());
    }
}
package org.example.backend.domain.question.controller;

import static org.example.backend.domain.question.util.QuestionUtils.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
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
        QuestionForm questionForm = createTestQuestionForm("test subject", "test content");
        Question question = createTestQuestion(1L, questionForm);
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
    @DisplayName("Patch /api/question/{id}")
    void updateQuestion() throws Exception {
        //given
        String url = "/api/question/1";
        QuestionForm questionForm = createTestQuestionForm("test subject", "test content");
        QuestionForm updateForm = createTestQuestionForm("update subject", "update content");

        Question question = createTestQuestion(1L, questionForm);
        Question updateQuestion = updateTestQuestion(question, updateForm);

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
}
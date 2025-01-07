package org.example.backend.domain.question.controller;

import static org.example.backend.domain.question.util.QuestionUtils.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.backend.domain.question.dto.QuestionDto;
import org.example.backend.domain.question.entity.Question;
import org.example.backend.domain.question.form.QuestionCreateForm;
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
        QuestionCreateForm questionCreateForm = createTestQuestionForm("test subject", "test content");
        Question question = createTestQuestion(1L, questionCreateForm);
        QuestionDto questionDto = QuestionDto.fromQuestion(question);

        when(questionService.createQuestion(any(QuestionCreateForm.class))).thenReturn(questionDto);

        //when
        ResultActions resultActions = mockMvc.perform(post(url)
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(questionCreateForm)));

        //then
        resultActions
            .andExpect(status().isCreated())
            .andExpect(jsonPath("$.id").value(questionDto.getId()))
            .andExpect(jsonPath("$.subject").value(questionDto.getSubject()))
            .andExpect(jsonPath("$.content").value(questionDto.getContent()));
    }
}
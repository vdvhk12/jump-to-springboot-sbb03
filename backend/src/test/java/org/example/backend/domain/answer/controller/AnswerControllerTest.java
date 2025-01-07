package org.example.backend.domain.answer.controller;

import static org.example.backend.domain.util.AnswerUtils.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.backend.domain.answer.dto.AnswerDto;
import org.example.backend.domain.answer.entity.Answer;
import org.example.backend.domain.answer.form.AnswerForm;
import org.example.backend.domain.answer.service.AnswerService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

@WebMvcTest(AnswerController.class)
class AnswerControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private AnswerService answerService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @DisplayName("POST /api/answer/create")
    void t1() throws Exception {
        //given
        String url = "/api/answer/create";
        AnswerForm answerForm = createTestAnswerForm(1L, "content");
        Answer answer = createTestAnswer(1L, answerForm);
        AnswerDto answerDto = AnswerDto.fromEntity(answer);
        when(answerService.createAnswer(any(AnswerForm.class))).thenReturn(answerDto);

        //when
        ResultActions resultActions = mockMvc.perform(post(url)
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(answerForm)));

        //then
        resultActions
            .andExpect(status().isCreated())
            .andExpect(jsonPath("$.id").value(answer.getId()))
            .andExpect(jsonPath("$.questionId").value(answer.getQuestionId()))
            .andExpect(jsonPath("$.content").value(answer.getContent()));
    }
}
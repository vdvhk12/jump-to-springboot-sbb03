package org.example.backend.domain.question.service;


import static org.assertj.core.api.Assertions.assertThat;
import static org.example.backend.domain.question.util.QuestionUtils.createTestQuestion;
import static org.example.backend.domain.question.util.QuestionUtils.createTestQuestionForm;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import org.example.backend.domain.question.dto.QuestionDto;
import org.example.backend.domain.question.entity.Question;
import org.example.backend.domain.question.form.QuestionCreateForm;
import org.example.backend.domain.question.repository.QuestionRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class QuestionServiceTest {

    @Mock
    private QuestionRepository questionRepository;

    @InjectMocks
    private QuestionService questionService;

    @Test
    @DisplayName("question create")
    void t1() {
        //given
        QuestionCreateForm questionCreateForm = createTestQuestionForm("test subject", "test content");
        Question question = createTestQuestion(1L, questionCreateForm);

        when(questionRepository.save(any(Question.class))).thenReturn(question);

        //when
        QuestionDto result = questionService.createQuestion(questionCreateForm);

        //then
        assertThat(result).isNotNull();
        assertThat(result.getId()).isEqualTo(1L);
        assertThat(result.getSubject()).isEqualTo(questionCreateForm.getSubject());
        assertThat(result.getContent()).isEqualTo(questionCreateForm.getContent());
    }
}
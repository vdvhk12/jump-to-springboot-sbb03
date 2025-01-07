package org.example.backend.domain.question.service;


import static org.assertj.core.api.Assertions.assertThat;
import static org.example.backend.domain.question.util.QuestionUtils.createTestQuestion;
import static org.example.backend.domain.question.util.QuestionUtils.createTestQuestionForm;
import static org.example.backend.domain.question.util.QuestionUtils.updateTestQuestion;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;
import org.example.backend.domain.question.dto.QuestionDto;
import org.example.backend.domain.question.entity.Question;
import org.example.backend.domain.question.form.QuestionForm;
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
        QuestionForm questionForm = createTestQuestionForm("test subject", "test content");
        Question question = createTestQuestion(1L, questionForm);

        when(questionRepository.save(any(Question.class))).thenReturn(question);

        //when
        QuestionDto result = questionService.createQuestion(questionForm);

        //then
        assertThat(result).isNotNull();
        assertThat(result.getId()).isEqualTo(1L);
        assertThat(result.getSubject()).isEqualTo(questionForm.getSubject());
        assertThat(result.getContent()).isEqualTo(questionForm.getContent());
    }

    @Test
    @DisplayName("question update")
    void t2() {
        //given
        QuestionForm createForm = createTestQuestionForm("test subject", "test content");
        QuestionForm updateForm = createTestQuestionForm("update subject", "update content");

        Question question = createTestQuestion(1L, createForm);
        Question updateQuestion = updateTestQuestion(question, updateForm);

        when(questionRepository.findById(any(Long.class))).thenReturn(Optional.of(question));
        when(questionRepository.save(any(Question.class))).thenReturn(updateQuestion);

        //when
        QuestionDto result = questionService.updateQuestion(question.getId(), updateForm);

        //then
        assertThat(result).isNotNull();
        assertThat(result.getId()).isEqualTo(1L);
        assertThat(result.getSubject()).isEqualTo(updateForm.getSubject());
        assertThat(result.getContent()).isEqualTo(updateForm.getContent());
        assertThat(result.getUpdatedAt()).isNotNull();
    }

    @Test
    @DisplayName("question delete")
    void t3() {
        //given
        QuestionForm createForm = createTestQuestionForm("test subject", "test content");
        Question question = createTestQuestion(1L, createForm);

        when(questionRepository.findById(any(Long.class))).thenReturn(Optional.of(question));

        //when
        questionService.deleteQuestion(question.getId());

        //then
        verify(questionRepository, times(1)).delete(question);
    }
}
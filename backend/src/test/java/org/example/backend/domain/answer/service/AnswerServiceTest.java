package org.example.backend.domain.answer.service;

import static org.assertj.core.api.Assertions.*;
import static org.example.backend.domain.util.AnswerUtils.*;
import static org.example.backend.domain.util.CategoryUtils.createTestCategory;
import static org.example.backend.domain.util.CategoryUtils.createTestCategoryForm;
import static org.example.backend.domain.util.QuestionUtils.createTestQuestion;
import static org.example.backend.domain.util.QuestionUtils.createTestQuestionForm;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;
import org.example.backend.domain.answer.dto.AnswerDto;
import org.example.backend.domain.answer.entity.Answer;
import org.example.backend.domain.answer.form.AnswerForm;
import org.example.backend.domain.answer.repository.AnswerRepository;
import org.example.backend.domain.category.entity.Category;
import org.example.backend.domain.question.entity.Question;
import org.example.backend.domain.question.service.QuestionService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class AnswerServiceTest {

    @Mock
    private AnswerRepository answerRepository;

    @Mock
    private QuestionService questionService;

    @InjectMocks
    private AnswerService answerService;

    @Test
    @DisplayName("create answer")
    void t1() {
        //given
        Category category = createTestCategory(1L, createTestCategoryForm("category"));
        Question question = createTestQuestion(1L,
            createTestQuestionForm(1L, "subject, ", "content"), category);
        when(questionService.getQuestionOrThrow(any(Long.class))).thenReturn(question);

        AnswerForm answerForm = createTestAnswerForm(1L, "content");
        Answer answer = createTestAnswer(question, 1L, answerForm);
        when(answerRepository.save(any(Answer.class))).thenReturn(answer);

        //when
        AnswerDto result = answerService.createAnswer(answerForm);

        //then
        assertThat(result).isNotNull();
        assertThat(result.getId()).isEqualTo(answer.getId());
        assertThat(result.getQuestion().getId()).isEqualTo(answer.getQuestion().getId());
        assertThat(result.getContent()).isEqualTo(answer.getContent());
    }

    @Test
    @DisplayName("update answer")
    void t2() {
        //given
        Category category = createTestCategory(1L, createTestCategoryForm("category"));
        Question question = createTestQuestion(1L,
            createTestQuestionForm(1L, "subject, ", "content"), category);

        AnswerForm answerForm = createTestAnswerForm(1L, "content");
        Answer answer = createTestAnswer(question, 1L, answerForm);
        when(answerRepository.findById(any(Long.class))).thenReturn(Optional.of(answer));

        AnswerForm updateForm = createTestAnswerForm(1L, "update content");
        Answer updatedAnswer = createTestAnswer(question, 1L, updateForm);
        when(answerRepository.save(any(Answer.class))).thenReturn(updatedAnswer);

        //when
        AnswerDto result = answerService.updateAnswer(answer.getId(), updateForm);

        //then
        assertThat(result).isNotNull();
        assertThat(result.getId()).isEqualTo(answer.getId());
        assertThat(result.getQuestion().getId()).isEqualTo(updatedAnswer.getQuestion().getId());
        assertThat(result.getContent()).isEqualTo(updatedAnswer.getContent());
    }

    @Test
    @DisplayName("delete answer")
    void t3() {
        //given
        Category category = createTestCategory(1L, createTestCategoryForm("category"));
        Question question = createTestQuestion(1L,
            createTestQuestionForm(1L, "subject, ", "content"), category);

        AnswerForm answerForm = createTestAnswerForm(1L, "content");
        Answer answer = createTestAnswer(question, 1L, answerForm);
        when(answerRepository.findById(any(Long.class))).thenReturn(Optional.of(answer));

        //when
        answerService.deleteAnswer(answer.getId());

        //then
        verify(answerRepository, times(1)).delete(any(Answer.class));
    }
}
package org.example.backend.domain.answer.service;

import static org.assertj.core.api.Assertions.*;
import static org.example.backend.domain.util.AnswerUtils.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;
import org.example.backend.domain.answer.dto.AnswerDto;
import org.example.backend.domain.answer.entity.Answer;
import org.example.backend.domain.answer.form.AnswerForm;
import org.example.backend.domain.answer.repository.AnswerRepository;
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

    @InjectMocks
    private AnswerService answerService;

    @Test
    @DisplayName("create answer")
    void t1() {
        //given
        AnswerForm answerForm = createTestAnswerForm(1L, "content");
        Answer answer = createTestAnswer(1L, answerForm);

        when(answerRepository.save(any(Answer.class))).thenReturn(answer);

        //when
        AnswerDto result = answerService.createAnswer(answerForm);

        //then
        assertThat(result).isNotNull();
        assertThat(result.getId()).isEqualTo(answer.getId());
        assertThat(result.getQuestionId()).isEqualTo(answer.getQuestionId());
        assertThat(result.getContent()).isEqualTo(answer.getContent());
    }

    @Test
    @DisplayName("update answer")
    void t2() {
        //given
        AnswerForm answerForm = createTestAnswerForm(1L, "content");
        Answer answer = createTestAnswer(1L, answerForm);
        when(answerRepository.findById(any(Long.class))).thenReturn(Optional.of(answer));

        AnswerForm updateForm = createTestAnswerForm(1L, "update content");
        Answer updatedAnswer = createTestAnswer(1L, updateForm);
        when(answerRepository.save(any(Answer.class))).thenReturn(updatedAnswer);

        //when
        AnswerDto result = answerService.updateAnswer(answer.getId(), updateForm);

        //then
        assertThat(result).isNotNull();
        assertThat(result.getId()).isEqualTo(answer.getId());
        assertThat(result.getQuestionId()).isEqualTo(updatedAnswer.getQuestionId());
        assertThat(result.getContent()).isEqualTo(updatedAnswer.getContent());
    }

    @Test
    @DisplayName("delete answer")
    void t3() {
        //given
        AnswerForm answerForm = createTestAnswerForm(1L, "content");
        Answer answer = createTestAnswer(1L, answerForm);
        when(answerRepository.findById(any(Long.class))).thenReturn(Optional.of(answer));

        //when
        answerService.deleteAnswer(answer.getId());

        //then
        verify(answerRepository, times(1)).delete(any(Answer.class));
    }
}
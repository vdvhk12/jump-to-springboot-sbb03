package org.example.backend.domain.answer.repository;

import static org.assertj.core.api.Assertions.*;
import static org.example.backend.domain.util.AnswerUtils.*;

import java.time.LocalDateTime;
import org.example.backend.domain.answer.entity.Answer;
import org.example.backend.domain.answer.form.AnswerForm;
import org.example.backend.global.exception.DataNotFoundException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
class AnswerRepositoryTest {

    @Autowired
    private AnswerRepository answerRepository;

    @Test
    @DisplayName("create answer")
    void t1() {
        //given
        AnswerForm answerForm = createTestAnswerForm(1L, "content");

        //when
        Answer result = answerRepository.save(Answer.of(answerForm));

        //then
        assertThat(result).isNotNull();
        assertThat(result.getId()).isNotNull();
        assertThat(result.getQuestionId()).isEqualTo(1L);
        assertThat(result.getContent()).isEqualTo(answerForm.getContent());
    }

    @Test
    @DisplayName("update answer")
    void t2() {
        //given
        AnswerForm answerForm = createTestAnswerForm(1L, "content");
        Answer answer = answerRepository.save(Answer.of(answerForm));

        AnswerForm updateForm = createTestAnswerForm(1L, "update content");

        //when
        Answer result = answerRepository.findById(answer.getId()).map(q -> q.toBuilder()
                .content(updateForm.getContent())
                .updatedAt(LocalDateTime.now())
                .build())
            .orElseThrow(() -> new DataNotFoundException("Answer not found"));

        //then
        assertThat(result).isNotNull();
        assertThat(result.getId()).isNotNull();
        assertThat(result.getQuestionId()).isEqualTo(1L);
        assertThat(result.getContent()).isEqualTo(updateForm.getContent());
        assertThat(result.getUpdatedAt()).isNotNull();
    }

    @Test
    @DisplayName("delete answer")
    void t3() {
        //given
        AnswerForm answerForm = createTestAnswerForm(1L, "content");
        Answer answer = answerRepository.save(Answer.of(answerForm));

        //when
        answerRepository.findById(answer.getId()).ifPresent(q -> answerRepository.delete(q));

        //then
        assertThatThrownBy(() -> answerRepository.findById(answer.getId())
            .orElseThrow(() -> new DataNotFoundException("Answer not found")))
            .isInstanceOf(DataNotFoundException.class).hasMessageContaining("Answer not found");
    }
}
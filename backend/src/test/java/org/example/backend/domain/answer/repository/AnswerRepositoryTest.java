package org.example.backend.domain.answer.repository;

import static org.assertj.core.api.Assertions.*;
import static org.example.backend.domain.util.AnswerUtils.*;

import org.example.backend.domain.answer.entity.Answer;
import org.example.backend.domain.answer.form.AnswerForm;
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
}
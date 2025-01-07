package org.example.backend.domain.question.repository;

import static org.assertj.core.api.Assertions.*;
import static org.example.backend.domain.question.util.QuestionUtils.*;

import org.example.backend.domain.question.entity.Question;
import org.example.backend.domain.question.form.QuestionForm;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
class QuestionRepositoryTest {

    @Autowired
    private QuestionRepository questionRepository;

    @Test
    @DisplayName("question create")
    void t1() {
        //given
        QuestionForm questionForm = createTestQuestionForm("test subject", "test content");

        //when
        Question result = questionRepository.save(Question.of(questionForm));

        //then
        assertThat(result).isNotNull();
        assertThat(result.getSubject()).isEqualTo(questionForm.getSubject());
        assertThat(result.getContent()).isEqualTo(questionForm.getContent());
    }
}
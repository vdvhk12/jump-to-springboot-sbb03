package org.example.backend.domain.answer.repository;

import static org.assertj.core.api.Assertions.*;
import static org.example.backend.domain.util.AnswerUtils.*;
import static org.example.backend.domain.util.CategoryUtils.createTestCategoryForm;
import static org.example.backend.domain.util.QuestionUtils.createTestQuestionForm;

import java.time.LocalDateTime;
import org.example.backend.domain.answer.entity.Answer;
import org.example.backend.domain.answer.form.AnswerForm;
import org.example.backend.domain.category.entity.Category;
import org.example.backend.domain.category.repository.CategoryRepository;
import org.example.backend.domain.question.entity.Question;
import org.example.backend.domain.question.repository.QuestionRepository;
import org.example.backend.global.exception.DataNotFoundException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
class AnswerRepositoryTest {

    @Autowired
    private AnswerRepository answerRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private QuestionRepository questionRepository;

    @Test
    @DisplayName("create answer")
    void t1() {
        //given
        Category category = categoryRepository.save(Category.of(createTestCategoryForm("category")));
        Question question = questionRepository.save(Question.of(
            createTestQuestionForm(category.getId(), "subject", "content"), category));
        AnswerForm answerForm = createTestAnswerForm(1L, "content");

        //when
        Answer result = answerRepository.save(Answer.of(question, answerForm));

        //then
        assertThat(result).isNotNull();
        assertThat(result.getId()).isNotNull();
        assertThat(result.getQuestion().getId()).isEqualTo(1L);
        assertThat(result.getContent()).isEqualTo(answerForm.getContent());
    }

    @Test
    @DisplayName("update answer")
    void t2() {
        //given
        Category category = categoryRepository.save(Category.of(createTestCategoryForm("category")));
        Question question = questionRepository.save(Question.of(
            createTestQuestionForm(category.getId(), "subject", "content"), category));
        Answer answer = answerRepository.save(
            Answer.of(question, createTestAnswerForm(1L, "content")));

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
        assertThat(result.getQuestion().getId()).isEqualTo(question.getId());
        assertThat(result.getContent()).isEqualTo(updateForm.getContent());
        assertThat(result.getUpdatedAt()).isNotNull();
    }

    @Test
    @DisplayName("delete answer")
    void t3() {
        //given
        Category category = categoryRepository.save(Category.of(createTestCategoryForm("category")));
        Question question = questionRepository.save(Question.of(
            createTestQuestionForm(category.getId(), "subject", "content"), category));
        Answer answer = answerRepository.save(
            Answer.of(question, createTestAnswerForm(1L, "content")));

        //when
        answerRepository.findById(answer.getId()).ifPresent(q -> answerRepository.delete(q));

        //then
        assertThatThrownBy(() -> answerRepository.findById(answer.getId())
            .orElseThrow(() -> new DataNotFoundException("Answer not found")))
            .isInstanceOf(DataNotFoundException.class).hasMessageContaining("Answer not found");
    }
}
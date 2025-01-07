package org.example.backend.domain.question.repository;

import static org.assertj.core.api.Assertions.*;
import static org.example.backend.domain.util.CategoryUtils.*;
import static org.example.backend.domain.util.QuestionUtils.createTestQuestionForm;

import java.time.LocalDateTime;
import java.util.List;
import org.example.backend.domain.category.entity.Category;
import org.example.backend.domain.category.form.CategoryForm;
import org.example.backend.domain.category.repository.CategoryRepository;
import org.example.backend.domain.question.entity.Question;
import org.example.backend.domain.question.form.QuestionForm;
import org.example.backend.global.exception.DataNotFoundException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
class QuestionRepositoryTest {

    @Autowired
    private QuestionRepository questionRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Test
    @DisplayName("create question")
    void t1() {
        //given
        CategoryForm categoryForm = createTestCategoryForm("category");
        Category category = categoryRepository.save(Category.of(categoryForm));

        QuestionForm questionForm = createTestQuestionForm(category.getId(), "subject", "content");

        //when
        Question result = questionRepository.save(Question.of(questionForm, category));

        //then
        assertThat(result).isNotNull();
        assertThat(result.getSubject()).isEqualTo(questionForm.getSubject());
        assertThat(result.getContent()).isEqualTo(questionForm.getContent());
    }

    @Test
    @DisplayName("get questions")
    void t2() {
        //given
        CategoryForm categoryForm = createTestCategoryForm("category");
        Category category = categoryRepository.save(Category.of(categoryForm));

        QuestionForm questionForm = createTestQuestionForm(category.getId(), "subject", "content");
        questionRepository.save(Question.of(questionForm, category));
        questionRepository.save(Question.of(questionForm, category));

        //when
        List<Question> result = questionRepository.findAll();

        //then
        assertThat(result).isNotNull();
        assertThat(result.size()).isEqualTo(2);
        assertThat(result.getFirst().getCategory()).isInstanceOf(Category.class);
        assertThat(result.getFirst().getCategory().getName()).isEqualTo(category.getName());
        assertThat(result.getFirst().getSubject()).isEqualTo(questionForm.getSubject());
        assertThat(result.getFirst().getContent()).isEqualTo(questionForm.getContent());

        assertThat(result.getLast().getCategory()).isInstanceOf(Category.class);
        assertThat(result.getLast().getCategory().getName()).isEqualTo(category.getName());
        assertThat(result.getLast().getSubject()).isEqualTo(questionForm.getSubject());
        assertThat(result.getLast().getContent()).isEqualTo(questionForm.getContent());
    }

    @Test
    @DisplayName("get question")
    void t3() {
        //given
        CategoryForm categoryForm = createTestCategoryForm("category");
        Category category = categoryRepository.save(Category.of(categoryForm));

        QuestionForm questionForm = createTestQuestionForm(category.getId(), "subject", "content");
        Question question = questionRepository.save(Question.of(questionForm, category));

        //when
        Question result = questionRepository.findById(question.getId())
            .orElseThrow(() -> new DataNotFoundException("Question not found"));

        //then
        assertThat(result).isNotNull();
        assertThat(result.getCategory()).isInstanceOf(Category.class);
        assertThat(result.getCategory().getName()).isEqualTo(category.getName());
        assertThat(result.getSubject()).isEqualTo(questionForm.getSubject());
        assertThat(result.getContent()).isEqualTo(questionForm.getContent());
    }

    @Test
    @DisplayName("update question")
    void t4() {
        //given
        CategoryForm categoryForm = createTestCategoryForm("category");
        Category category = categoryRepository.save(Category.of(categoryForm));

        QuestionForm createForm = createTestQuestionForm(category.getId(), "subject", "content");
        QuestionForm updateForm = createTestQuestionForm(category.getId(), "update subject", "update content");
        Question question = questionRepository.save(Question.of(createForm, category));

        //when
        Question result = questionRepository.findById(question.getId()).map(q -> q.toBuilder()
                .subject(updateForm.getSubject())
                .content(updateForm.getContent())
                .updatedAt(LocalDateTime.now())
                .build())
            .orElseThrow(() -> new DataNotFoundException("Question not found"));

        //then
        assertThat(result).isNotNull();
        assertThat(result.getSubject()).isEqualTo(updateForm.getSubject());
        assertThat(result.getContent()).isEqualTo(updateForm.getContent());
        assertThat(result.getUpdatedAt()).isNotNull();
    }

    @Test
    @DisplayName("delete question")
    void t5() {
        //given
        CategoryForm categoryForm = createTestCategoryForm("category");
        Category category = categoryRepository.save(Category.of(categoryForm));

        QuestionForm createForm = createTestQuestionForm(category.getId(), "subject", "content");
        Question question = questionRepository.save(Question.of(createForm, category));

        //when
        questionRepository.findById(question.getId()).ifPresent(q -> questionRepository.delete(q));

        //then
        assertThatThrownBy(() -> questionRepository.findById(question.getId())
            .orElseThrow(() -> new DataNotFoundException("Question not found")))
            .isInstanceOf(DataNotFoundException.class).hasMessageContaining("Question not found");
    }
}
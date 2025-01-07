package org.example.backend.domain.question.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.example.backend.domain.util.CategoryUtils.*;
import static org.example.backend.domain.util.QuestionUtils.createTestQuestion;
import static org.example.backend.domain.util.QuestionUtils.createTestQuestionForm;
import static org.example.backend.domain.util.QuestionUtils.updateTestQuestion;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;
import org.example.backend.domain.category.dto.CategoryDto;
import org.example.backend.domain.category.entity.Category;
import org.example.backend.domain.category.form.CategoryForm;
import org.example.backend.domain.category.service.CategoryService;
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

    @Mock
    private CategoryService categoryService;

    @InjectMocks
    private QuestionService questionService;

    @Test
    @DisplayName("create question")
    void t1() {
        //given
        CategoryForm categoryForm = createTestCategoryForm("category");
        CategoryDto category = CategoryDto.fromCategory(createTestCategory(1L, categoryForm));
        when(categoryService.getCategory(any(Long.class))).thenReturn(category);

        QuestionForm questionForm = createTestQuestionForm(category.getId(), "subject", "content");
        Question question = createTestQuestion(1L, questionForm, Category.fromDto(category));
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
    @DisplayName("get questions")
    void t2() {
        //given
        CategoryForm categoryForm = createTestCategoryForm("category");
        CategoryDto category = CategoryDto.fromCategory(createTestCategory(1L, categoryForm));

        QuestionForm questionForm1 = createTestQuestionForm(category.getId(), "subject1", "content1");
        Question question1 = createTestQuestion(1L, questionForm1, Category.fromDto(category));

        QuestionForm questionForm2 = createTestQuestionForm(category.getId(), "subject2", "content2");
        Question question2 = createTestQuestion(2L, questionForm2, Category.fromDto(category));

        List<Question> questions = List.of(question1, question2);
        when(questionRepository.findAll()).thenReturn(questions);

        //when
        List<QuestionDto> result = questionService.getAllQuestions();

        //then
        assertThat(result).isNotNull();
        assertThat(result.size()).isEqualTo(2);
        assertThat(result.getFirst().getCategory()).isInstanceOf(CategoryDto.class);
        assertThat(result.getFirst().getCategory().getId()).isEqualTo(category.getId());
        assertThat(result.getFirst().getSubject()).isEqualTo(questionForm1.getSubject());
        assertThat(result.getFirst().getContent()).isEqualTo(questionForm1.getContent());
        assertThat(result.getFirst().getCreatedAt()).isNotNull();

        assertThat(result.getLast().getCategory()).isInstanceOf(CategoryDto.class);
        assertThat(result.getLast().getCategory().getId()).isEqualTo(category.getId());
        assertThat(result.getLast().getSubject()).isEqualTo(questionForm2.getSubject());
        assertThat(result.getLast().getContent()).isEqualTo(questionForm2.getContent());
        assertThat(result.getLast().getCreatedAt()).isNotNull();
    }

    @Test
    @DisplayName("get question")
    void t3() {
        //given
        CategoryForm categoryForm = createTestCategoryForm("category");
        CategoryDto category = CategoryDto.fromCategory(createTestCategory(1L, categoryForm));

        QuestionForm questionForm = createTestQuestionForm(category.getId(), "subject", "content");
        Question question = createTestQuestion(1L, questionForm, Category.fromDto(category));

        when(questionRepository.findById(any(Long.class))).thenReturn(Optional.of(question));

        //when
        QuestionDto result = questionService.getQuestion(question.getId());

        //then
        assertThat(result).isNotNull();
        assertThat(result.getCategory()).isInstanceOf(CategoryDto.class);
        assertThat(result.getCategory().getId()).isEqualTo(category.getId());
        assertThat(result.getSubject()).isEqualTo(questionForm.getSubject());
        assertThat(result.getContent()).isEqualTo(questionForm.getContent());
        assertThat(result.getCreatedAt()).isNotNull();
    }

    @Test
    @DisplayName("update question")
    void t4() {
        //given
        CategoryForm categoryForm = createTestCategoryForm("category");
        CategoryDto category = CategoryDto.fromCategory(createTestCategory(1L, categoryForm));
        when(categoryService.getCategory(any(Long.class))).thenReturn(category);


        QuestionForm createForm = createTestQuestionForm(category.getId(), "subject", "content");
        QuestionForm updateForm = createTestQuestionForm(category.getId(), "update subject", "update content");

        Question question = createTestQuestion(1L, createForm, Category.fromDto(category));
        Question updateQuestion = updateTestQuestion(question, updateForm, Category.fromDto(category));

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
    @DisplayName("delete question")
    void t5() {
        //given
        CategoryForm categoryForm = createTestCategoryForm("category");
        Category category = createTestCategory(1L, categoryForm);

        QuestionForm createForm = createTestQuestionForm(category.getId(), "subject", "content");
        Question question = createTestQuestion(1L, createForm, category);

        when(questionRepository.findById(any(Long.class))).thenReturn(Optional.of(question));

        //when
        questionService.deleteQuestion(question.getId());

        //then
        verify(questionRepository, times(1)).delete(question);
    }
}
package org.example.backend.domain.question.service;

import java.time.LocalDateTime;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.example.backend.domain.category.entity.Category;
import org.example.backend.domain.category.service.CategoryService;
import org.example.backend.domain.question.dto.QuestionDto;
import org.example.backend.domain.question.entity.Question;
import org.example.backend.domain.question.form.QuestionForm;
import org.example.backend.domain.question.repository.QuestionRepository;
import org.example.backend.global.exception.DataNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class QuestionService {

    private final QuestionRepository questionRepository;
    private final CategoryService categoryService;

    public QuestionDto createQuestion(QuestionForm questionForm) {
        Category category = Category.fromDto(categoryService.getCategory(questionForm.getCategoryId()));
        return QuestionDto.fromQuestion(questionRepository.save(Question.of(questionForm, category)));
    }

    public List<QuestionDto> getAllQuestions() {
        return questionRepository.findAll().stream().map(QuestionDto::fromQuestion).toList();
    }

    public QuestionDto getQuestion(Long questionId) {
        return QuestionDto.fromQuestion(getQuestionOrThrow(questionId));
    }

    public QuestionDto updateQuestion(Long questionId, QuestionForm questionForm) {
        Category category = Category.fromDto(categoryService.getCategory(questionForm.getCategoryId()));
        Question question = getQuestionOrThrow(questionId);
        return QuestionDto.fromQuestion(questionRepository.save(question.toBuilder()
            .category(category)
            .subject(questionForm.getSubject())
            .content(questionForm.getContent())
            .updatedAt(LocalDateTime.now())
            .build()));
    }

    public void deleteQuestion(Long questionId) {
        Question question = getQuestionOrThrow(questionId);
        questionRepository.delete(question);
    }

    private Question getQuestionOrThrow(Long questionId) {
        return questionRepository.findById(questionId)
            .orElseThrow(() -> new DataNotFoundException("Question not found"));
    }
}

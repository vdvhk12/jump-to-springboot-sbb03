package org.example.backend.domain.question.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.example.backend.domain.category.entity.Category;
import org.example.backend.domain.category.service.CategoryService;
import org.example.backend.domain.question.dto.QuestionDetailDto;
import org.example.backend.domain.question.dto.QuestionListDto;
import org.example.backend.domain.question.entity.Question;
import org.example.backend.domain.question.form.QuestionForm;
import org.example.backend.domain.question.repository.QuestionRepository;
import org.example.backend.global.exception.DataNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Order;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class QuestionService {

    private final QuestionRepository questionRepository;
    private final CategoryService categoryService;

    public QuestionDetailDto createQuestion(QuestionForm questionForm) {
        Category category = Category.fromDto(categoryService.getCategory(questionForm.getCategoryId()));
        return QuestionDetailDto.fromEntity(questionRepository.save(Question.of(questionForm, category)));
    }

    public Page<QuestionListDto> getAllQuestions(int page) {
        List<Order> sorts = new ArrayList<>();
        sorts.add(Sort.Order.desc("createdAt"));
        Pageable pageable = PageRequest.of(page - 1, 10, Sort.by(sorts));
        return questionRepository.findAll(pageable).map(QuestionListDto::fromEntity);
    }

    public QuestionDetailDto getQuestion(Long questionId) {
        return QuestionDetailDto.fromEntity(getQuestionOrThrow(questionId));
    }

    public QuestionDetailDto updateQuestion(Long questionId, QuestionForm questionForm) {
        Category category = Category.fromDto(categoryService.getCategory(questionForm.getCategoryId()));
        Question question = getQuestionOrThrow(questionId);
        return QuestionDetailDto.fromEntity(questionRepository.save(question.toBuilder()
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

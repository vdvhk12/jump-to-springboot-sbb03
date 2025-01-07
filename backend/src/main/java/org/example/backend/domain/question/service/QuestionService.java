package org.example.backend.domain.question.service;

import java.time.LocalDateTime;
import lombok.RequiredArgsConstructor;
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

    public QuestionDto createQuestion(QuestionForm questionForm) {
        return QuestionDto.fromQuestion(questionRepository.save(Question.of(questionForm)));
    }

    public QuestionDto updateQuestion(Long questionId, QuestionForm questionForm) {
        Question question = getQuestionOrThrow(questionId);
        return QuestionDto.fromQuestion(questionRepository.save(question.toBuilder()
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

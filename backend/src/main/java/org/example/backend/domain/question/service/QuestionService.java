package org.example.backend.domain.question.service;

import lombok.RequiredArgsConstructor;
import org.example.backend.domain.question.dto.QuestionDto;
import org.example.backend.domain.question.entity.Question;
import org.example.backend.domain.question.form.QuestionForm;
import org.example.backend.domain.question.repository.QuestionRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class QuestionService {

    private final QuestionRepository questionRepository;

    public QuestionDto createQuestion(QuestionForm questionForm) {
        return QuestionDto.fromQuestion(questionRepository.save(Question.of(questionForm)));
    }
}

package org.example.backend.domain.answer.service;

import lombok.RequiredArgsConstructor;
import org.example.backend.domain.answer.dto.AnswerDto;
import org.example.backend.domain.answer.entity.Answer;
import org.example.backend.domain.answer.form.AnswerForm;
import org.example.backend.domain.answer.repository.AnswerRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AnswerService {

    private final AnswerRepository answerRepository;

    public AnswerDto createAnswer(AnswerForm answerForm) {
        return AnswerDto.fromEntity(answerRepository.save(Answer.of(answerForm)));
    }
}

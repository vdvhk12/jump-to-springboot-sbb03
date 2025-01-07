package org.example.backend.domain.answer.service;

import java.time.LocalDateTime;
import lombok.RequiredArgsConstructor;
import org.example.backend.domain.answer.dto.AnswerDto;
import org.example.backend.domain.answer.entity.Answer;
import org.example.backend.domain.answer.form.AnswerForm;
import org.example.backend.domain.answer.repository.AnswerRepository;
import org.example.backend.global.exception.DataNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AnswerService {

    private final AnswerRepository answerRepository;

    public AnswerDto createAnswer(AnswerForm answerForm) {
        return AnswerDto.fromEntity(answerRepository.save(Answer.of(answerForm)));
    }

    public AnswerDto updateAnswer(Long answerId, AnswerForm answerForm) {
        Answer answer = getAnswerOrThrow(answerId);
        return AnswerDto.fromEntity(answerRepository.save(answer.toBuilder()
            .content(answerForm.getContent())
            .updatedAt(LocalDateTime.now())
            .build()));
    }

    public void deleteAnswer(Long answerId) {
        Answer answer = getAnswerOrThrow(answerId);
        answerRepository.delete(answer);
    }

    private Answer getAnswerOrThrow(Long answerId) {
        return answerRepository.findById(answerId)
            .orElseThrow(() -> new DataNotFoundException("Answer not found"));
    }
}

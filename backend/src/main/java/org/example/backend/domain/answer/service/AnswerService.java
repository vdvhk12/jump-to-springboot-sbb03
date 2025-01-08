package org.example.backend.domain.answer.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.example.backend.domain.answer.dto.AnswerDto;
import org.example.backend.domain.answer.entity.Answer;
import org.example.backend.domain.answer.form.AnswerForm;
import org.example.backend.domain.answer.repository.AnswerRepository;
import org.example.backend.global.exception.DataNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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

    public Page<AnswerDto> getAnswerPage (Long questionId, int page, String sort) {
        Pageable pageable = getPageable(page, sort);
        return answerRepository.findAllByQuestionId(questionId, pageable).map(AnswerDto::fromEntity);
    }

    private Pageable getPageable(int page, String sort) {
        List<Sort.Order> sorts = new ArrayList<>();
        if (sort.isEmpty() || sort.equals("old")) {
            sorts.add(Sort.Order.asc("createdAt"));
        } else if(sort.equals("new")) {
            sorts.add(Sort.Order.desc("createdAt"));
        } else if(sort.equals("recommend")) {
            sorts.add(Sort.Order.desc("voter"));
        }
        return PageRequest.of(page - 1, 10, Sort.by(sorts));
    }
}

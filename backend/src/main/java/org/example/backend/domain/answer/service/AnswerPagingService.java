package org.example.backend.domain.answer.service;

import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.example.backend.domain.answer.dto.AnswerDto;
import org.example.backend.domain.answer.repository.AnswerRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Order;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AnswerPagingService {

    private final AnswerRepository answerRepository;

    public Page<AnswerDto> getAnswerPage (Long questionId, int page, String sort) {
        Pageable pageable = getPageable(page, sort);
        return answerRepository.findAllByQuestionId(questionId, pageable).map(AnswerDto::fromEntity);
    }

    private Pageable getPageable(int page, String sort) {
        List<Order> sorts = new ArrayList<>();
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

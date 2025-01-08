package org.example.backend.domain.answer.repository;

import org.example.backend.domain.answer.entity.Answer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AnswerRepository extends JpaRepository<Answer, Long> {
    Page<Answer> findAllByQuestionId(Long questionId, Pageable pageable);
}

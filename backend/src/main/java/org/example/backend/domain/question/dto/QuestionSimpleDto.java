package org.example.backend.domain.question.dto;

import lombok.Builder;
import lombok.Getter;
import org.example.backend.domain.question.entity.Question;

@Getter
@Builder
public class QuestionSimpleDto {

    private Long id;
    private String subject;

    public static QuestionSimpleDto fromEntity(Question question) {
        return QuestionSimpleDto.builder()
            .id(question.getId())
            .subject(question.getSubject())
            .build();
    }
}

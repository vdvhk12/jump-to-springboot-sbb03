package org.example.backend.domain.answer.dto;

import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Getter;
import org.example.backend.domain.answer.entity.Answer;
import org.example.backend.domain.question.dto.QuestionSimpleDto;

@Getter
@Builder
public class AnswerDto {

    private Long id;
    private QuestionSimpleDto question;
    private String content;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public static AnswerDto fromEntity(Answer answer) {
        return AnswerDto.builder()
            .id(answer.getId())
            .question(QuestionSimpleDto.fromEntity(answer.getQuestion()))
            .content(answer.getContent())
            .createdAt(answer.getCreatedAt())
            .updatedAt(answer.getUpdatedAt())
            .build();
    }
}

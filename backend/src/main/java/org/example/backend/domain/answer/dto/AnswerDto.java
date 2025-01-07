package org.example.backend.domain.answer.dto;

import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Getter;
import org.example.backend.domain.answer.entity.Answer;

@Getter
@Builder
public class AnswerDto {

    private Long id;
    private Long questionId;
    private String content;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public static AnswerDto fromEntity(Answer answer) {
        return AnswerDto.builder()
            .id(answer.getId())
            .questionId(answer.getQuestionId())
            .content(answer.getContent())
            .createdAt(answer.getCreatedAt())
            .updatedAt(answer.getUpdatedAt())
            .build();
    }
}

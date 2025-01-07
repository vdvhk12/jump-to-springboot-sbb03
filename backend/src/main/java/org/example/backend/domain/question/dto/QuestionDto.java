package org.example.backend.domain.question.dto;

import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Getter;
import org.example.backend.domain.category.dto.CategoryDto;
import org.example.backend.domain.question.entity.Question;

@Getter
@Builder
public class QuestionDto {

    private Long id;
    private CategoryDto category;
    private String subject;
    private String content;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private int views;

    public static QuestionDto fromQuestion(Question question) {
        return QuestionDto.builder()
            .id(question.getId())
            .category(CategoryDto.fromCategory(question.getCategory()))
            .subject(question.getSubject())
            .content(question.getContent())
            .createdAt(question.getCreatedAt())
            .updatedAt(question.getUpdatedAt())
            .views(question.getViews())
            .build();
    }
}

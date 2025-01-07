package org.example.backend.domain.question.dto;

import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Getter;
import org.example.backend.domain.answer.dto.AnswerDto;
import org.example.backend.domain.category.dto.CategoryDto;
import org.example.backend.domain.question.entity.Question;
import org.springframework.data.domain.Page;

@Getter
@Builder
public class QuestionDetailDto {

    private Long id;
    private CategoryDto category;
    private String subject;
    private String content;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private int views;
    private Page<AnswerDto> answers;

    public static QuestionDetailDto fromEntity(Question question) {
        return QuestionDetailDto.builder()
            .id(question.getId())
            .category(CategoryDto.fromCategory(question.getCategory()))
            .subject(question.getSubject())
            .content(question.getContent())
            .createdAt(question.getCreatedAt())
            .updatedAt(question.getUpdatedAt())
            .views(question.getViews())
            .answers(Page.empty())
            .build();
    }

    public static QuestionDetailDto fromEntityAndAnswerPage(Question question, Page<AnswerDto> answers) {
        return QuestionDetailDto.builder()
            .id(question.getId())
            .category(CategoryDto.fromCategory(question.getCategory()))
            .subject(question.getSubject())
            .content(question.getContent())
            .createdAt(question.getCreatedAt())
            .updatedAt(question.getUpdatedAt())
            .views(question.getViews())
            .answers(answers)
            .build();
    }
}

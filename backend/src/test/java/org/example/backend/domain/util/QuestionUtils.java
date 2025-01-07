package org.example.backend.domain.util;

import java.time.LocalDateTime;
import org.example.backend.domain.category.entity.Category;
import org.example.backend.domain.question.entity.Question;
import org.example.backend.domain.question.form.QuestionForm;

public class QuestionUtils {

    public static QuestionForm createTestQuestionForm(Long categoryId, String subject, String content) {
        QuestionForm form = new QuestionForm();
        form.setCategoryId(categoryId);
        form.setSubject(subject);
        form.setContent(content);
        return form;
    }

    public static Question createTestQuestion(Long questionId, QuestionForm questionForm, Category category) {
        return Question.builder()
            .id(questionId)
            .category(category)
            .subject(questionForm.getSubject())
            .content(questionForm.getContent())
            .createdAt(LocalDateTime.now())
            .views(0)
            .build();
    }

    public static Question updateTestQuestion(Question question, QuestionForm questionForm, Category category) {
        return question.toBuilder()
            .category(category)
            .subject(questionForm.getSubject())
            .content(questionForm.getContent())
            .updatedAt(LocalDateTime.now())
            .build();
    }
}

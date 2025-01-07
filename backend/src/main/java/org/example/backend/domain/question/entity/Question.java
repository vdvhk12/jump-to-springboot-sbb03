package org.example.backend.domain.question.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.example.backend.domain.answer.entity.Answer;
import org.example.backend.domain.category.entity.Category;
import org.example.backend.domain.question.form.QuestionForm;

@Entity
@Getter
@Builder(toBuilder = true)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Question {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    private Category category;

    @Column(length = 200)
    private String subject;

    @Column(columnDefinition = "TEXT")
    private String content;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    private int views;

    @OneToMany(cascade = CascadeType.REMOVE)
    private List<Answer> answers;

    public static Question of(QuestionForm questionForm, Category category) {
        return Question.builder()
            .category(category)
            .subject(questionForm.getSubject())
            .content(questionForm.getContent())
            .createdAt(LocalDateTime.now())
            .views(0)
            .answers(new ArrayList<>())
            .build();
    }
}

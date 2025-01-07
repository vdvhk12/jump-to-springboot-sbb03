package org.example.backend.domain.question.controller;

import lombok.RequiredArgsConstructor;
import org.example.backend.domain.question.dto.QuestionDto;
import org.example.backend.domain.question.form.QuestionCreateForm;
import org.example.backend.domain.question.service.QuestionService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/question")
@RequiredArgsConstructor
public class QuestionController {

    private final QuestionService questionService;

    @PostMapping("/create")
    public ResponseEntity<QuestionDto> createQuestion(@RequestBody QuestionCreateForm questionCreateForm) {
        QuestionDto questionDto = questionService.createQuestion(questionCreateForm);
        return ResponseEntity.status(HttpStatus.CREATED).body(questionDto);
    }

}

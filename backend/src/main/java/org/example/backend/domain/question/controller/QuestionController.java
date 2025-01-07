package org.example.backend.domain.question.controller;

import lombok.RequiredArgsConstructor;
import org.example.backend.domain.question.dto.QuestionDto;
import org.example.backend.domain.question.form.QuestionForm;
import org.example.backend.domain.question.service.QuestionService;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/question")
@RequiredArgsConstructor
public class QuestionController {

    private final QuestionService questionService;

    @PostMapping("/create")
    public ResponseEntity<QuestionDto> createQuestion(@RequestBody QuestionForm questionForm) {
        QuestionDto questionDto = questionService.createQuestion(questionForm);
        return ResponseEntity.status(HttpStatus.CREATED).body(questionDto);
    }

    @GetMapping("/list")
    public ResponseEntity<Page<QuestionDto>> getQuestions(
        @RequestParam(value = "page", defaultValue = "1") int page) {
        Page<QuestionDto> questions = questionService.getAllQuestions(page);
        return ResponseEntity.status(HttpStatus.OK).body(questions);
    }

    @GetMapping("/{id}")
    public ResponseEntity<QuestionDto> getQuestion(@PathVariable Long id) {
        QuestionDto questionDto = questionService.getQuestion(id);
        return ResponseEntity.status(HttpStatus.OK).body(questionDto);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<QuestionDto> updateQuestion(@PathVariable("id") Long id, @RequestBody QuestionForm questionForm) {
        QuestionDto questionDto = questionService.updateQuestion(id, questionForm);
        return ResponseEntity.status(HttpStatus.OK).body(questionDto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteQuestion(@PathVariable("id") Long id) {
        questionService.deleteQuestion(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}

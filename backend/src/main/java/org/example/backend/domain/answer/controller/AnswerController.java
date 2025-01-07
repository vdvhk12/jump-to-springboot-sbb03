package org.example.backend.domain.answer.controller;

import lombok.RequiredArgsConstructor;
import org.example.backend.domain.answer.dto.AnswerDto;
import org.example.backend.domain.answer.form.AnswerForm;
import org.example.backend.domain.answer.service.AnswerService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/answer/")
@RequiredArgsConstructor
public class AnswerController {

    private final AnswerService answerService;

    @PostMapping("/create")
    public ResponseEntity<AnswerDto> createAnswer(@RequestBody AnswerForm answerForm) {
        AnswerDto answer = answerService.createAnswer(answerForm);
        return ResponseEntity.status(HttpStatus.CREATED).body(answer);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<AnswerDto> updateAnswer(@PathVariable("id") Long id,
        @RequestBody AnswerForm answerForm) {
        AnswerDto answer = answerService.updateAnswer(id, answerForm);
        return ResponseEntity.status(HttpStatus.OK).body(answer);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAnswer(@PathVariable("id") Long id) {
        answerService.deleteAnswer(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}

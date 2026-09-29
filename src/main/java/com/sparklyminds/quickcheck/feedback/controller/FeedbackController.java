package com.sparklyminds.quickcheck.feedback.controller;

import com.sparklyminds.quickcheck.feedback.dto.FeedbackResponse;
import com.sparklyminds.quickcheck.feedback.dto.SubmitFeedbackRequest;
import com.sparklyminds.quickcheck.feedback.service.FeedbackService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/feedback")
@RequiredArgsConstructor
public class FeedbackController {

    private final FeedbackService service;

    @PostMapping
    public ResponseEntity<FeedbackResponse> submit(@Valid @RequestBody SubmitFeedbackRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.submit(request));
    }

    @GetMapping("/admin/assessment/{assessmentId}")
    public List<FeedbackResponse> list(@PathVariable Long assessmentId) {
        return service.list(assessmentId);
    }
}
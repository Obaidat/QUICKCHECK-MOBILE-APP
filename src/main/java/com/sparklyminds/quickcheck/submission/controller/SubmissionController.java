package com.sparklyminds.quickcheck.submission.controller;

import com.sparklyminds.quickcheck.submission.dto.SubmissionRequest;
import com.sparklyminds.quickcheck.submission.service.SubmissionService;
import com.sparklyminds.quickcheck.result.dto.ResultResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/assessment-submission")
@RequiredArgsConstructor
public class SubmissionController {

    private final SubmissionService submissionService;

    // Submit multiple answers at once
    @PostMapping
    public ResponseEntity<List<ResultResponse.Translation>> createSubmission(@RequestParam Long assessmentId, @Valid @RequestBody List<SubmissionRequest> requests) {
        return ResponseEntity.status(HttpStatus.CREATED).body(submissionService.create(assessmentId, requests));
    }
}
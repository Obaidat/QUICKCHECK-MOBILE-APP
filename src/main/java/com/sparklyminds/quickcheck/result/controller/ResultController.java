package com.sparklyminds.quickcheck.result.controller;

import com.sparklyminds.quickcheck.result.dto.ResultRequest;
import com.sparklyminds.quickcheck.result.dto.ResultResponse;
import com.sparklyminds.quickcheck.result.service.ResultService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/results")
@RequiredArgsConstructor
public class ResultController {

    private final ResultService resultService;

    // ---------------------- PUBLIC ---------------------- //

    @GetMapping("/{id}")
    public ResponseEntity<ResultResponse> getById(
            @PathVariable Long id
    ) {

        return ResponseEntity.ok(
                resultService.getById(id)
        );
    }

    // ---------------------- ADMIN ---------------------- //

    @GetMapping("/admin/assessment/{assessmentId}")
    public ResponseEntity<List<ResultResponse>> getByAssessment(
            @PathVariable Long assessmentId
    ) {

        return ResponseEntity.ok(
                resultService.getByAssessmentId(assessmentId)
        );
    }

    @PostMapping
    public ResponseEntity<ResultResponse> create(
            @Valid @RequestBody ResultRequest request
    ) {

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(resultService.create(request));
    }

    @PutMapping("/admin/{id}")
    public ResponseEntity<ResultResponse> update(
            @PathVariable Long id,
            @Valid @RequestBody ResultRequest request
    ) {

        return ResponseEntity.ok(
                resultService.update(id, request)
        );
    }

    @DeleteMapping("/admin/{id}")
    public ResponseEntity<Void> delete(
            @PathVariable Long id
    ) {

        resultService.delete(id);

        return ResponseEntity.noContent().build();
    }
}
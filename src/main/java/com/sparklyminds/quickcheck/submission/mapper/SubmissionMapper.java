package com.sparklyminds.quickcheck.submission.mapper;

import com.sparklyminds.quickcheck.submission.dto.SubmissionRequest;
import com.sparklyminds.quickcheck.submission.dto.SubmissionResponse;
import com.sparklyminds.quickcheck.submission.entity.Submission;
import org.springframework.stereotype.Component;

@Component
public class SubmissionMapper {

    public Submission toEntity(SubmissionRequest request) {
        Submission submission = new Submission();
        submission.setValue(request.getValue());
        return submission;
    }

    public SubmissionResponse toResponse(Submission submission) {
        return SubmissionResponse.builder()
                .id(submission.getId())
                .questionId(submission.getQuestion().getId())
                .value(submission.isValue())
                .build();
    }

    public void updateEntity(Submission submission, SubmissionRequest request) {
        submission.setValue(request.getValue());
    }
}
package com.sparklyminds.quickcheck.submission.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class SubmissionResponse {

    private Long id;
    private Long questionId;
    private boolean value;
}
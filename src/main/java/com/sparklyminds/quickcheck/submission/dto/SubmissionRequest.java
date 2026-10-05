package com.sparklyminds.quickcheck.submission.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SubmissionRequest {

    @NotNull
    private Long questionId;

    @NotNull
    private Boolean value;
}
package com.sparklyminds.quickcheck.feedback.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SubmitFeedbackRequest {

    @NotNull
    private Long assessmentId;

    @Min(1)
    @Max(5)
    private int rating;

    @Size(max = 2000)
    private String comment;
}
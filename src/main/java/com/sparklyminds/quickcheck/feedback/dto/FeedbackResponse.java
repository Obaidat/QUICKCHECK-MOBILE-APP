package com.sparklyminds.quickcheck.feedback.dto;

import lombok.Builder;
import lombok.Getter;
import java.time.Instant;

@Getter
@Builder
public class FeedbackResponse {

    private Long id;
    private Long assessmentId;
    private int rating;
    private String comment;
    private Instant createdAt;
}
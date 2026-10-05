package com.sparklyminds.quickcheck.feedback.mapper;

import com.sparklyminds.quickcheck.feedback.dto.FeedbackResponse;
import com.sparklyminds.quickcheck.feedback.entity.Feedback;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class FeedBackMapper {

    public FeedbackResponse toResponse(Feedback feedback) {
        return FeedbackResponse
                .builder()
                .id(feedback.getId())
                .assessmentId(feedback.getAssessment().getId())
                .rating(feedback.getRating())
                .comment(feedback.getComment())
                .createdAt(feedback.getCreatedAt())
                .build();
    }
}

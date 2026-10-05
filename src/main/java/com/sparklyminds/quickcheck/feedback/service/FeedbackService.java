package com.sparklyminds.quickcheck.feedback.service;

import com.sparklyminds.quickcheck.assessment.repository.AssessmentRepository;
import com.sparklyminds.quickcheck.common.exception.ResourceNotFoundException;
import com.sparklyminds.quickcheck.feedback.dto.FeedbackResponse;
import com.sparklyminds.quickcheck.feedback.dto.SubmitFeedbackRequest;
import com.sparklyminds.quickcheck.feedback.entity.Feedback;
import com.sparklyminds.quickcheck.feedback.mapper.FeedBackMapper;
import com.sparklyminds.quickcheck.feedback.repository.FeedbackRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class FeedbackService {

    private final FeedbackRepository repository;
    private final AssessmentRepository assessmentRepository;
    private final FeedBackMapper feedBackMapper;

    public FeedbackResponse submit(SubmitFeedbackRequest request) {

        Feedback feedback = new Feedback();

        feedback.setAssessment(
                assessmentRepository
                        .findById(request.getAssessmentId())
                        .orElseThrow(() -> new ResourceNotFoundException(
                                "Assessment not found with id: "
                                        + request.getAssessmentId()
                        ))
        );

        feedback.setRating(request.getRating());
        feedback.setComment(request.getComment());

        return feedBackMapper.toResponse(repository.save(feedback));
    }

    @Transactional(readOnly = true)
    public List<FeedbackResponse> list(Long assessmentId) {
        return repository
                .findByAssessmentIdOrderByCreatedAtDesc(assessmentId)
                .stream()
                .map(feedBackMapper::toResponse)
                .toList();
    }
}
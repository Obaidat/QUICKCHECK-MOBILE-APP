package com.sparklyminds.quickcheck.feedback.repository;

import com.sparklyminds.quickcheck.feedback.entity.Feedback;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FeedbackRepository extends JpaRepository<Feedback, Long> {

    List<Feedback> findByAssessmentIdOrderByCreatedAtDesc(
            Long assessmentId
    );
}
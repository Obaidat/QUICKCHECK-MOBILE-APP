package com.sparklyminds.quickcheck.submission.repository;

import com.sparklyminds.quickcheck.submission.entity.Submission;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface SubmissionRepository extends JpaRepository<Submission, Long> {

    List<Submission> findByQuestionId(Long questionId);
    void deleteByQuestionId(Long questionId);
}
package com.sparklyminds.quickcheck.result.repository;

import com.sparklyminds.quickcheck.result.entity.Result;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ResultRepository extends JpaRepository<Result, Long> {

    List<Result> findByAssessmentId(Long assessmentId);

    List<Result> findByAssessmentIdOrderByMinPointsAsc(Long assessmentId);

    List<Result> findByAssessmentIdAndRedFlagResult(Long assessmentId, boolean redFlagResult);
}
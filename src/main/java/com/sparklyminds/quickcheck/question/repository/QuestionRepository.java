package com.sparklyminds.quickcheck.question.repository;

import com.sparklyminds.quickcheck.question.entity.Question;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface QuestionRepository extends JpaRepository<Question, Long> {

    List<Question> findByAssessmentIdAndEnabledTrueOrderByQuestionOrderAsc(
            Long assessmentId
    );

    List<Question> findByAssessmentIdOrderByQuestionOrderAsc(
            Long assessmentId
    );
}
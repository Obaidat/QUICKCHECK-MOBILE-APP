package com.sparklyminds.quickcheck.A_done.assessment.repository;

import com.sparklyminds.quickcheck.A_done.assessment.entity.AssessmentTranslation;
import com.sparklyminds.quickcheck.A_done.common.enums.Language;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface AssessmentTranslationRepository
        extends JpaRepository<AssessmentTranslation, Long> {

    List<AssessmentTranslation> findByAssessmentId(Long assessmentId);

    boolean existsByAssessmentIdAndLanguage(
            Long assessmentId,
            Language language
    );

    Optional<AssessmentTranslation> findByIdAndAssessmentId(
            Long id,
            Long assessmentId
    );
}
package com.sparklyminds.quickcheck.assessment.repository;

import com.sparklyminds.quickcheck.assessment.entity.Assessment;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface AssessmentRepository extends JpaRepository<Assessment, Long> {
    List<Assessment> findByEnabledTrueOrderByIdAsc();
    boolean existsByCode(String code);
}
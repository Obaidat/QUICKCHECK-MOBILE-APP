package com.sparklyminds.quickcheck.A_done.assessment.repository;

import com.sparklyminds.quickcheck.A_done.assessment.entity.Assessment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AssessmentRepository extends JpaRepository<Assessment, Long> {

    List<Assessment> findByEnabledTrueOrderByIdAsc();
    boolean existsByCode(String code);
}
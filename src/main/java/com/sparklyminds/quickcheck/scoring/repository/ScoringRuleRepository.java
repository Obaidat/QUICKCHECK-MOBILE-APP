package com.sparklyminds.quickcheck.scoring.repository;

import com.sparklyminds.quickcheck.scoring.entity.ScoringRule;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ScoringRuleRepository extends JpaRepository<ScoringRule, Long> {

    Optional<ScoringRule> findByQuestionId(Long questionId);
}
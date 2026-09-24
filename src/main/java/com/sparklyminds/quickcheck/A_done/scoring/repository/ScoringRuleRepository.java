package com.sparklyminds.quickcheck.A_done.scoring.repository;

import com.sparklyminds.quickcheck.A_done.scoring.entity.ScoringRule;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ScoringRuleRepository extends JpaRepository<ScoringRule, Long> {

}
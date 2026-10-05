package com.sparklyminds.quickcheck.scoring.entity;

import com.sparklyminds.quickcheck.question.entity.Question;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(
        name = "scoring_rules",
        uniqueConstraints = @UniqueConstraint(
                name = "uk_scoring_rule_question_answer",
                columnNames = {"question_id"}
        )
)
@Getter
@Setter
@NoArgsConstructor
public class ScoringRule {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "question_id", nullable = false)
    private Question question;

    @Column(nullable = false)
    private int points;

    @Column(nullable = false)
    private boolean redFlag;
}
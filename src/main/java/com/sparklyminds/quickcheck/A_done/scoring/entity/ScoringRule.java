package com.sparklyminds.quickcheck.A_done.scoring.entity;

import com.sparklyminds.quickcheck.A_done.assessment.entity.Assessment;
import com.sparklyminds.quickcheck.A_done.question.entity.Question;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(
        name = "scoring_rules",
        uniqueConstraints = @UniqueConstraint(
                name = "uk_scoring_rule_question_answer",
                columnNames = {"question_id", "answer_value"}
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

    @Column(name = "answer_value", nullable = false)
    private boolean answerValue;

    @Column(nullable = false)
    private int points;

    @Column(nullable = false)
    private boolean redFlag;
}
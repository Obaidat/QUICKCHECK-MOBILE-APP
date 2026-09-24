package com.sparklyminds.quickcheck.scoring.service;

import com.sparklyminds.quickcheck.A_done.question.entity.Question;
import com.sparklyminds.quickcheck.A_done.scoring.entity.ScoringRule;
import org.junit.jupiter.api.Test;
import java.util.Map;
import static org.assertj.core.api.Assertions.assertThat;

class RedFlagEvaluatorTest {
    private final RedFlagEvaluator evaluator = new RedFlagEvaluator();

    @Test
    void usesQuestionConfigurationWhenNoRuleExists() {
        Question question = question(10L);
        question.setRedFlagOnYes(true);

        assertThat(evaluator.isRedFlag(question, true, Map.of())).isTrue();
        assertThat(evaluator.isRedFlag(question, false, Map.of())).isFalse();
    }

    @Test
    void ruleOverridesQuestionConfiguration() {
        Question question = question(10L);
        question.setRedFlagOnYes(true);
        ScoringRule rule = new ScoringRule();
        rule.setRedFlag(false);

        assertThat(evaluator.isRedFlag(question, true, Map.of("10:true", rule))).isFalse();
    }

    private Question question(Long id) {
        Question question = new Question();
        question.setId(id);
        return question;
    }
}

package com.sparklyminds.quickcheck.A_done.scoring.service;

import com.sparklyminds.quickcheck.A_done.question.entity.Question;
import com.sparklyminds.quickcheck.A_done.question.repository.QuestionRepository;
import com.sparklyminds.quickcheck.A_done.common.exception.BadRequestException;
import com.sparklyminds.quickcheck.A_done.common.exception.ResourceNotFoundException;
import com.sparklyminds.quickcheck.A_done.scoring.dto.ScoringRequest;
import com.sparklyminds.quickcheck.A_done.scoring.dto.ScoringResponse;
import com.sparklyminds.quickcheck.A_done.scoring.entity.ScoringRule;
import com.sparklyminds.quickcheck.A_done.scoring.repository.ScoringRuleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class ScoringService {

    private final ScoringRuleRepository scoringRuleRepository;
    private final QuestionRepository questionRepository;

    @Transactional(readOnly = true)
    public List<ScoringResponse> getByQuestionId(Long questionId) {

        return scoringRuleRepository
                .findAll()
                .stream()
                .filter(rule ->
                        rule.getQuestion()
                                .getId()
                                .equals(questionId)
                )
                .map(this::toResponse)
                .toList();
    }

    @Transactional(readOnly = true)
    public ScoringResponse getById(Long id) {
        ScoringRule rule = findById(id);
        return toResponse(rule);
    }

    public ScoringResponse create(ScoringRequest request) {

        Question question = findQuestion(request.getQuestionId());

        validateDuplicateRule(
                question.getId(),
                request.getAnswerValue(),
                null
        );

        ScoringRule rule = new ScoringRule();

        rule.setQuestion(question);
        rule.setAnswerValue(request.getAnswerValue());
        rule.setPoints(request.getPoints());
        rule.setRedFlag(request.getRedFlag());

        ScoringRule saved = scoringRuleRepository.save(rule);
        return toResponse(saved);
    }

    public ScoringResponse update(Long id, ScoringRequest request) {

        ScoringRule rule = findById(id);
        Question question = findQuestion(request.getQuestionId());

        validateDuplicateRule(
                question.getId(),
                request.getAnswerValue(),
                id
        );

        rule.setQuestion(question);
        rule.setAnswerValue(request.getAnswerValue());
        rule.setPoints(request.getPoints());
        rule.setRedFlag(request.getRedFlag());

        return toResponse(rule);
    }

    public void delete(Long id) {
        ScoringRule rule = findById(id);
        scoringRuleRepository.delete(rule);
    }

    private void validateDuplicateRule(
            Long questionId,
            boolean answerValue,
            Long currentRuleId
    ) {

        boolean duplicate =
                scoringRuleRepository
                        .findAll()
                        .stream()
                        .anyMatch(rule ->
                                rule.getQuestion()
                                        .getId()
                                        .equals(questionId)
                                        && rule.isAnswerValue() == answerValue
                                        && (
                                        currentRuleId == null
                                                || !rule.getId()
                                                .equals(currentRuleId)
                                )
                        );

        if (duplicate) {
            throw new BadRequestException(
                    "A scoring rule already exists for this question and answer value"
            );
        }
    }

    private Question findQuestion(Long questionId) {

        return questionRepository
                .findById(questionId)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Question not found with id: " + questionId
                        )
                );
    }

    private ScoringRule findById(Long id) {

        return scoringRuleRepository
                .findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Scoring rule not found with id: " + id
                        )
                );
    }

    private ScoringResponse toResponse(
            ScoringRule rule
    ) {

        return ScoringResponse.builder()
                .id(rule.getId())
                .questionId(rule.getQuestion().getId())
                .answerValue(rule.isAnswerValue())
                .points(rule.getPoints())
                .redFlag(rule.isRedFlag())
                .build();
    }
}
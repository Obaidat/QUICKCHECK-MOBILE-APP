package com.sparklyminds.quickcheck.scoring.service;

import com.sparklyminds.quickcheck.question.entity.Question;
import com.sparklyminds.quickcheck.question.repository.QuestionRepository;
import com.sparklyminds.quickcheck.common.exception.BadRequestException;
import com.sparklyminds.quickcheck.common.exception.ResourceNotFoundException;
import com.sparklyminds.quickcheck.scoring.dto.ScoringRequest;
import com.sparklyminds.quickcheck.scoring.dto.ScoringResponse;
import com.sparklyminds.quickcheck.scoring.entity.ScoringRule;
import com.sparklyminds.quickcheck.scoring.repository.ScoringRuleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class ScoringService {

    private final ScoringRuleRepository scoringRuleRepository;
    private final QuestionRepository questionRepository;

    @Transactional(readOnly = true)
    public ScoringResponse getByQuestionId(Long questionId) {

        ScoringRule rule = scoringRuleRepository
                .findByQuestionId(questionId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Scoring rule not found for question: " + questionId
                ));

        return toResponse(rule);
    }

    @Transactional(readOnly = true)
    public ScoringResponse getById(Long id) {
        ScoringRule rule = findById(id);
        return toResponse(rule);
    }

    @Transactional(readOnly = true)
    public boolean isRedFlag(Long questionId) {
        ScoringResponse scoring = getByQuestionId(questionId);
        return scoring.isRedFlag();
    }

    public ScoringResponse create(ScoringRequest request) {

        Question question = findQuestion(request.getQuestionId());
        validateDuplicateRule(question.getId(), null);

        ScoringRule rule = new ScoringRule();
        rule.setQuestion(question);
        rule.setPoints(request.getPoints());
        rule.setRedFlag(request.getRedFlag());

        ScoringRule saved = scoringRuleRepository.save(rule);
        return toResponse(saved);
    }

    public ScoringResponse update(Long id, ScoringRequest request) {

        ScoringRule rule = findById(id);
        Question question = findQuestion(request.getQuestionId());
        validateDuplicateRule(question.getId(), id);

        rule.setQuestion(question);
        rule.setPoints(request.getPoints());
        rule.setRedFlag(request.getRedFlag());

        return toResponse(rule);
    }

    public void delete(Long id) {
        ScoringRule rule = findById(id);
        scoringRuleRepository.delete(rule);
    }

    private void validateDuplicateRule(Long questionId, Long currentRuleId) {

        boolean duplicate = scoringRuleRepository
                        .findAll()
                        .stream()
                        .anyMatch(rule ->
                                rule.getQuestion().getId()
                                        .equals(questionId) &&
                                        (currentRuleId == null || !rule.getId().equals(currentRuleId))
                        );

        if (duplicate) {
            throw new BadRequestException("A scoring rule already exists for this question and answer value");
        }
    }

    private Question findQuestion(Long questionId) {
        return questionRepository
                .findById(questionId)
                .orElseThrow(() -> new ResourceNotFoundException("Question not found with id: " + questionId));
    }

    private ScoringRule findById(Long id) {

        return scoringRuleRepository.findById(id).orElseThrow(() ->
                new ResourceNotFoundException("Scoring rule not found with id: " + id));
    }

    private ScoringResponse toResponse(ScoringRule rule) {
        return ScoringResponse.builder()
                .id(rule.getId())
                .questionId(rule.getQuestion().getId())
                .points(rule.getPoints())
                .redFlag(rule.isRedFlag())
                .build();
    }
}
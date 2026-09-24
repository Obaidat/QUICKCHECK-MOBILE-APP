package com.sparklyminds.quickcheck.A_done.question.service;

import com.sparklyminds.quickcheck.A_done.assessment.entity.Assessment;
import com.sparklyminds.quickcheck.A_done.assessment.repository.AssessmentRepository;
import com.sparklyminds.quickcheck.A_done.common.enums.Language;
import com.sparklyminds.quickcheck.A_done.question.dto.QuestionRequest;
import com.sparklyminds.quickcheck.A_done.question.dto.QuestionResponse;
import com.sparklyminds.quickcheck.A_done.question.entity.Question;
import com.sparklyminds.quickcheck.A_done.question.entity.QuestionTranslation;
import com.sparklyminds.quickcheck.A_done.question.mapper.QuestionMapper;
import com.sparklyminds.quickcheck.A_done.question.mapper.QuestionTranslationMapper;
import com.sparklyminds.quickcheck.A_done.question.repository.QuestionRepository;
import lombok.RequiredArgsConstructor;
import com.sparklyminds.quickcheck.A_done.common.exception.BadRequestException;
import com.sparklyminds.quickcheck.A_done.common.exception.ResourceNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.HashSet;

@Service
@RequiredArgsConstructor
@Transactional
public class QuestionService {

    private final QuestionRepository questionRepository;
    private final AssessmentRepository assessmentRepository;
    private final QuestionMapper questionMapper;
    private final QuestionTranslationMapper translationMapper;

    // ---------------------- PUBLIC ---------------------- //

    @Transactional(readOnly = true)
    public List<QuestionResponse> getByAssessmentId(Long assessmentId) {
        requireEnabledAssessment(assessmentId);
        return questionRepository
                .findByAssessmentIdAndEnabledTrueOrderByQuestionOrderAsc(assessmentId)
                .stream()
                .map(questionMapper::toResponse)
                .toList();
    }

    // ---------------------- ADMIN ---------------------- //

    @Transactional(readOnly = true)
    public List<QuestionResponse> getAllByAssessmentId(Long assessmentId) {
        requireAssessment(assessmentId);
        return questionRepository
                .findByAssessmentIdOrderByQuestionOrderAsc(assessmentId)
                .stream()
                .map(questionMapper::toResponse)
                .toList();
    }

    @Transactional(readOnly = true)
    public QuestionResponse getById(Long id) {
        Question question = findById(id);
        if (!question.isEnabled() || !question.getAssessment().isEnabled()) {
            throw new ResourceNotFoundException("Question not found with id: " + id);
        }
        return questionMapper.toResponse(question);
    }

    public QuestionResponse create(QuestionRequest request) {
        validateTranslations(request);
        Assessment assessment = requireAssessment(request.getAssessmentId());

        Question question = questionMapper.toEntity(request);
        question.setAssessment(assessment);

        // Add translations
        if (request.getTranslations() != null) {
            for (var translationRequest : request.getTranslations()) {
                QuestionTranslation translation =
                        translationMapper.toEntity(translationRequest);

                question.addTranslation(translation);
            }
        }

        Question saved = questionRepository.save(question);

        return questionMapper.toResponse(saved);
    }

    public QuestionResponse update(Long id, QuestionRequest request) {
        validateTranslations(request);
        Question question = findById(id);

        Assessment assessment = requireAssessment(request.getAssessmentId());

        // Update question fields
        questionMapper.updateEntity(question, request);
        question.setAssessment(assessment);

        // Replace translations
        question.getTranslations().clear();

        if (request.getTranslations() != null) {
            for (var translationRequest : request.getTranslations()) {

                QuestionTranslation translation =
                        translationMapper.toEntity(translationRequest);

                question.addTranslation(translation);
            }
        }

        return questionMapper.toResponse(question);
    }

    public QuestionResponse updateEnabled(Long id, boolean enabled) {
        Question question = findById(id);

        question.setEnabled(enabled);

        return questionMapper.toResponse(question);
    }

    public void delete(Long id) {
        Question question = findById(id);
        questionRepository.delete(question);
    }

    // ---------------------- HELPER ---------------------- //

    private Question findById(Long id) {
        return questionRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Question not found with id: " + id));
    }

    private Assessment requireAssessment(Long id) {
        return assessmentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Assessment not found with id: " + id));
    }

    private void requireEnabledAssessment(Long id) {
        Assessment assessment = requireAssessment(id);
        if (!assessment.isEnabled()) {
            throw new ResourceNotFoundException("Assessment not found with id: " + id);
        }
    }

    private void validateTranslations(QuestionRequest request) {
        var languages = new HashSet<Language>();
        request.getTranslations().forEach(translation -> {
            if (!languages.add(translation.getLanguage())) {
                throw new BadRequestException("A question can have only one translation per language");
            }
        });
    }
}

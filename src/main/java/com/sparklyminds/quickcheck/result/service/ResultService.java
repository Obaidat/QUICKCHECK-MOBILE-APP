package com.sparklyminds.quickcheck.result.service;

import com.sparklyminds.quickcheck.assessment.entity.Assessment;
import com.sparklyminds.quickcheck.assessment.repository.AssessmentRepository;
import com.sparklyminds.quickcheck.common.exception.ResourceNotFoundException;
import com.sparklyminds.quickcheck.result.dto.ResultRequest;
import com.sparklyminds.quickcheck.result.dto.ResultResponse;
import com.sparklyminds.quickcheck.result.entity.Result;
import com.sparklyminds.quickcheck.result.entity.ResultTranslation;
import com.sparklyminds.quickcheck.result.mapper.ResultMapper;
import com.sparklyminds.quickcheck.result.repository.ResultRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class ResultService {

    private final ResultRepository resultRepository;
    private final AssessmentRepository assessmentRepository;
    private final ResultMapper resultMapper;

    // ---------------------- GET ---------------------- //

    @Transactional(readOnly = true)
    public List<ResultResponse> getByAssessmentId(Long assessmentId) {

        return resultRepository
                .findByAssessmentIdOrderByMinPointsAsc(assessmentId)
                .stream()
                .map(resultMapper::toResponse)
                .toList();
    }

    @Transactional(readOnly = true)
    public ResultResponse getById(Long id) {

        return resultMapper.toResponse(findById(id));
    }

    // ---------------------- CREATE ---------------------- //

    public ResultResponse create(ResultRequest request) {

        Assessment assessment = assessmentRepository
                .findById(request.getAssessmentId())
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Assessment not found with id: "
                                + request.getAssessmentId()
                ));

        Result result = resultMapper.toEntity(request);
        result.setAssessment(assessment);

        addTranslations(result, request);

        Result saved = resultRepository.save(result);

        return resultMapper.toResponse(saved);
    }

    // ---------------------- UPDATE ---------------------- //

    public ResultResponse update(
            Long id,
            ResultRequest request
    ) {

        Result result = findById(id);

        Assessment assessment = assessmentRepository
                .findById(request.getAssessmentId())
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Assessment not found with id: "
                                + request.getAssessmentId()
                ));

        resultMapper.updateEntity(result, request);

        result.setAssessment(assessment);

        // Replace translations
        result.getTranslations().clear();

        addTranslations(result, request);

        return resultMapper.toResponse(result);
    }

    // ---------------------- DELETE ---------------------- //

    public void delete(Long id) {

        Result result = findById(id);

        resultRepository.delete(result);
    }

    // ---------------------- TRANSLATIONS ---------------------- //

    private void addTranslations(
            Result result,
            ResultRequest request
    ) {

        if (request.getTranslations() == null) {
            return;
        }

        for (ResultRequest.Translation translationRequest
                : request.getTranslations()) {

            ResultTranslation translation =
                    resultMapper.toTranslationEntity(
                            translationRequest
                    );

            result.addTranslation(translation);
        }
    }

    // ---------------------- HELPER ---------------------- //

    private Result findById(Long id) {

        return resultRepository
                .findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Result not found with id: " + id
                ));
    }
}
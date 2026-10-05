package com.sparklyminds.quickcheck.submission.service;

import com.sparklyminds.quickcheck.common.exception.ResourceNotFoundException;
import com.sparklyminds.quickcheck.scoring.dto.ScoringResponse;
import com.sparklyminds.quickcheck.scoring.service.ScoringService;
import com.sparklyminds.quickcheck.submission.dto.SubmissionRequest;
import com.sparklyminds.quickcheck.result.dto.ResultResponse;
import com.sparklyminds.quickcheck.result.service.ResultService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class SubmissionService {

    private final ResultService resultService;
    private final ScoringService scoringService;

    // ---------------------- ADMIN ---------------------- //

    public List<ResultResponse.Translation> create(Long assessmentId, List<SubmissionRequest> requests) {

        int finalValue = 0;
        boolean hasRedFlag = false;

        // Count points & check for red flags
        for (SubmissionRequest submissionRequest : requests) {

            ScoringResponse scoring = scoringService.getByQuestionId(submissionRequest.getQuestionId());

            if (submissionRequest.getValue()) {
                finalValue += scoring.getPoints();

                if (scoring.isRedFlag()) {
                    hasRedFlag = true;
                }
            }
        }

        // Red flag result OR normal result based on score
        List<ResultResponse> results = resultService.getByAssessmentId(assessmentId);
        for (ResultResponse resultResponse : results) {

            if (hasRedFlag && resultResponse.isRedFlag()) {
                return resultResponse.getTranslations();
            }

            if (!hasRedFlag && !resultResponse.isRedFlag()
                    && finalValue >= resultResponse.getMinPoints() && finalValue <= resultResponse.getMaxPoints()) {
                return resultResponse.getTranslations();
            }
        }

        throw new ResourceNotFoundException(
                "No result found for assessment " + assessmentId
                        + " with score " + finalValue
        );
    }
}
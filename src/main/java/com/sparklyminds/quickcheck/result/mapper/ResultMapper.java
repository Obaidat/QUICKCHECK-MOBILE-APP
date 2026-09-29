package com.sparklyminds.quickcheck.result.mapper;

import com.sparklyminds.quickcheck.result.dto.ResultRequest;
import com.sparklyminds.quickcheck.result.dto.ResultResponse;
import com.sparklyminds.quickcheck.result.entity.Result;
import com.sparklyminds.quickcheck.result.entity.ResultTranslation;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ResultMapper {

    public Result toEntity(ResultRequest request) {

        Result result = new Result();

        result.setMinPoints(request.getMinPoints());
        result.setMaxPoints(request.getMaxPoints());
        result.setRedFlag(request.isRedFlag());

        return result;
    }

    public void updateEntity(
            Result result,
            ResultRequest request
    ) {
        result.setMinPoints(request.getMinPoints());
        result.setMaxPoints(request.getMaxPoints());
        result.setRedFlag(request.isRedFlag());
    }

    public ResultTranslation toTranslationEntity(
            ResultRequest.Translation request
    ) {

        ResultTranslation translation = new ResultTranslation();

        translation.setLanguage(request.getLanguage());
        translation.setHeadAnswer(request.getHeadAnswer());
        translation.setShortAnswer(request.getShortAnswer());
        translation.setFullAnswer(request.getFullAnswer());

        return translation;
    }

    public ResultResponse toResponse(Result result) {

        return ResultResponse.builder()
                .id(result.getId())
                .assessmentId(result.getAssessment().getId())
                .minPoints(result.getMinPoints())
                .maxPoints(result.getMaxPoints())
                .redFlagResult(result.isRedFlag())
                .translations(
                        result.getTranslations()
                                .stream()
                                .map(this::toTranslationResponse)
                                .toList()
                )
                .build();
    }

    private ResultResponse.Translation toTranslationResponse(
            ResultTranslation translation
    ) {

        return ResultResponse.Translation.builder()
                .language(translation.getLanguage())
                .headAnswer(translation.getHeadAnswer())
                .shortAnswer(translation.getShortAnswer())
                .fullAnswer(translation.getFullAnswer())
                .build();
    }
}
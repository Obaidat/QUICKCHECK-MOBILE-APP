package com.sparklyminds.quickcheck.A_done.question.mapper;

import com.sparklyminds.quickcheck.A_done.question.dto.QuestionTranslationRequest;
import com.sparklyminds.quickcheck.A_done.question.dto.QuestionTranslationResponse;
import com.sparklyminds.quickcheck.A_done.question.entity.QuestionTranslation;
import org.springframework.stereotype.Component;

@Component
public class QuestionTranslationMapper {

    public QuestionTranslation toEntity(QuestionTranslationRequest request) {
        QuestionTranslation translation = new QuestionTranslation();
        translation.setLanguage(request.getLanguage());
        translation.setText(request.getText());
        return translation;
    }

    public QuestionTranslationResponse toResponse(QuestionTranslation translation) {
        QuestionTranslationResponse response = new QuestionTranslationResponse();
        response.setId(translation.getId());
        response.setLanguage(translation.getLanguage());
        response.setText(translation.getText());
        return response;
    }

    public void updateEntity(QuestionTranslation translation, QuestionTranslationRequest request) {
        translation.setLanguage(request.getLanguage());
        translation.setText(request.getText());
    }
}
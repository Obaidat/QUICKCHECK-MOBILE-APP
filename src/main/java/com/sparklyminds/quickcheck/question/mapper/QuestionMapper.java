package com.sparklyminds.quickcheck.question.mapper;

import com.sparklyminds.quickcheck.question.dto.QuestionRequest;
import com.sparklyminds.quickcheck.question.dto.QuestionResponse;
import com.sparklyminds.quickcheck.question.entity.Question;
import com.sparklyminds.quickcheck.question.entity.QuestionTranslation;
import org.springframework.stereotype.Component;

@Component
public class QuestionMapper {

    public Question toEntity(QuestionRequest request) {
        Question question = new Question();
        question.setQuestionOrder(request.getQuestionOrder());
        question.setEnabled(request.getEnabled());
        return question;
    }

    public QuestionResponse toResponse(Question question) {

        QuestionResponse response = new QuestionResponse();
        response.setId(question.getId());
        response.setAssessmentId(question.getAssessment().getId());
        response.setQuestionOrder(question.getQuestionOrder());
        response.setEnabled(question.isEnabled());
        response.setTranslations(
                question.getTranslations()
                        .stream()
                        .map(this::toTranslationResponse)
                        .toList()
        );
        return response;
    }

    public void updateEntity(Question question, QuestionRequest request) {
        question.setQuestionOrder(request.getQuestionOrder());
        question.setEnabled(request.getEnabled());
    }

    // Translations

    public QuestionTranslation toTranslationEntity(QuestionRequest.Translation request) {
        QuestionTranslation translation = new QuestionTranslation();
        translation.setLanguage(request.getLanguage());
        translation.setText(request.getText());
        return translation;
    }

    public void updateTranslationEntity(QuestionTranslation translation, QuestionRequest.Translation request) {
        translation.setLanguage(request.getLanguage());
        translation.setText(request.getText());
    }

    private QuestionResponse.Translation toTranslationResponse(QuestionTranslation translation) {
        QuestionResponse.Translation response = new QuestionResponse.Translation();
        response.setId(translation.getId());
        response.setLanguage(translation.getLanguage());
        response.setText(translation.getText());
        return response;
    }
}
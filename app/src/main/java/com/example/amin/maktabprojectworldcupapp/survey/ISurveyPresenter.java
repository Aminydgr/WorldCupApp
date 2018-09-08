package com.example.amin.maktabprojectworldcupapp.survey;

import java.util.UUID;

/**
 * Created by Amin on 8/17/2018.
 */

public interface ISurveyPresenter {
    void loadQuestions();

    void allowedUser();

    void loadOptions(UUID questionUUID);
}

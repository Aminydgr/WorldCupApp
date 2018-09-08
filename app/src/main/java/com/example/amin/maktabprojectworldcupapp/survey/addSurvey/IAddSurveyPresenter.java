package com.example.amin.maktabprojectworldcupapp.survey.addSurvey;

/**
 * Created by Amin on 8/17/2018.
 */

public interface IAddSurveyPresenter {
    void addOptionDialog();

    void optionAdded(String optionText);

    void getOptionList();

    void validInput(String questionText);
}

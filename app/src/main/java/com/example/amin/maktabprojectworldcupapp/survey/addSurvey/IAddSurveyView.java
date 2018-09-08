package com.example.amin.maktabprojectworldcupapp.survey.addSurvey;

import com.example.amin.maktabprojectworldcupapp.model.Option;

import java.util.List;

/**
 * Created by Amin on 8/17/2018.
 */

public interface IAddSurveyView {
    void showAddOptionDialog();

    void setRecyclerAdapter(List<Option> optionList);

    void uploadFailed(String errorMessage);

    void uploadOk(String message);

    void showQuestionError(String errorMessage);

    void showOptionsCountError(String errorMessage);
}

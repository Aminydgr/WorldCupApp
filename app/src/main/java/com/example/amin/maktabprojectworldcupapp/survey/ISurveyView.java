package com.example.amin.maktabprojectworldcupapp.survey;

import com.example.amin.maktabprojectworldcupapp.model.Option;
import com.example.amin.maktabprojectworldcupapp.model.Question;

import java.util.List;

/**
 * Created by Amin on 8/17/2018.
 */

public interface ISurveyView {
    void setUpPager(List<Question> questionList);

    void addNotAllowed();

    void addAllowed();

    void setUpOptionAdapter(List<Option> optionList);
}

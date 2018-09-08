package com.example.amin.maktabprojectworldcupapp.survey.addSurvey.addOption;

import android.content.Intent;

/**
 * Created by Amin on 8/17/2018.
 */

public interface IAddOptionView {
    void sendResult(Intent intent);

    void showOptionTextError(String message);
}

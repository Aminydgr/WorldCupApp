package com.example.amin.maktabprojectworldcupapp.survey.addSurvey;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;

import com.example.amin.maktabprojectworldcupapp.SingleFragmentActivity;

public class AddSurveyActivity extends SingleFragmentActivity {

    public static Intent newIntent(Context packageContext) {
        Intent intent = new Intent ( packageContext, AddSurveyActivity.class );
        return intent;
    }


    @Override
    public Fragment createFragment() {
        return AddSurveyFragment.newInstance ();
    }
}

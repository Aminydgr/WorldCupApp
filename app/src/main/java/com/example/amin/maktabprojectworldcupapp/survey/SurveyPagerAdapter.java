package com.example.amin.maktabprojectworldcupapp.survey;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.example.amin.maktabprojectworldcupapp.model.Question;

import java.util.List;

/**
 * Created by Amin on 8/17/2018.
 */

public class SurveyPagerAdapter extends FragmentPagerAdapter {

    private Context context;
    private List<Question> questionList;

    public SurveyPagerAdapter(FragmentManager fm, Context context, List<Question> questionList) {
        super ( fm );
        this.context = context;
        this.questionList = questionList;
    }

    @Override
    public Fragment getItem(int position) {
        Question question = questionList.get ( position );
        return SurveyPageFragment.newInstance (question.getText (), question.getUuid ());
    }

    @Override
    public int getCount() {
        return questionList.size ();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return (position + 1) + "";
    }
}

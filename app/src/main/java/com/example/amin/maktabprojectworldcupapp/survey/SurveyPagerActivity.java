package com.example.amin.maktabprojectworldcupapp.survey;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.amin.maktabprojectworldcupapp.R;
import com.example.amin.maktabprojectworldcupapp.model.Option;
import com.example.amin.maktabprojectworldcupapp.model.Question;
import com.example.amin.maktabprojectworldcupapp.survey.addSurvey.AddSurveyActivity;

import java.util.List;

public class SurveyPagerActivity extends AppCompatActivity implements ISurveyView {

    private TabLayout tabLayout;
    private ViewPager viewPager;

    private SurveyPresenter presenter;

    public static Intent newIntent(Context packageContext) {
        Intent intent = new Intent ( packageContext, SurveyPagerActivity.class );
        return intent;
    }

    private void initView() {
        tabLayout = (TabLayout) findViewById ( R.id.survey_pager_tab_layout );
        viewPager = (ViewPager) findViewById ( R.id.survey_pager_view_pager );
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate ( savedInstanceState );
        setContentView ( R.layout.activity_survey_pager );

        presenter = new SurveyPresenter ( this, getApplicationContext () );

        initView ();

        presenter.loadQuestions ();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater ().inflate ( R.menu.survey_pager_menu, menu );
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId ()) {
            case R.id.add_new_survey_menu_item:
                presenter.allowedUser ();
                return true;
            case R.id.survey_results_menu_item:
                return true;
            default:
                return super.onOptionsItemSelected ( item );
        }
    }

    @Override
    public void setUpPager(List<Question> questionList) {
        viewPager.setAdapter ( new SurveyPagerAdapter ( getSupportFragmentManager (), getApplicationContext (), questionList ) );
        tabLayout.setupWithViewPager ( viewPager );
    }

    @Override
    public void addNotAllowed() {
        Toast.makeText ( getApplicationContext (), "این قابلیت برای شما فعال نمی باشد!", Toast.LENGTH_LONG ).show ();
    }

    @Override
    public void addAllowed() {
        startActivity ( AddSurveyActivity.newIntent ( getApplicationContext () ) );
    }

    @Override
    public void setUpOptionAdapter(List<Option> optionList) {

    }
}

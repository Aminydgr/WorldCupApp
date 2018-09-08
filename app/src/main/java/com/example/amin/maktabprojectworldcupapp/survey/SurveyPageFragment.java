package com.example.amin.maktabprojectworldcupapp.survey;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.amin.maktabprojectworldcupapp.Constant;
import com.example.amin.maktabprojectworldcupapp.R;
import com.example.amin.maktabprojectworldcupapp.model.Option;
import com.example.amin.maktabprojectworldcupapp.model.Question;

import java.util.List;
import java.util.UUID;

/**
 * A simple {@link Fragment} subclass.
 */
public class SurveyPageFragment extends Fragment implements ISurveyView {

    private TextView questionTextTxtView;
    private RecyclerView optionRecyclerView;

    private UUID questionUUID;
    private String questionText;

    private SurveyPresenter presenter;

    public static SurveyPageFragment newInstance(String questionText, UUID questionUUID) {

        Bundle args = new Bundle ();
        args.putSerializable ( Constant.ARGS_QUESTION_UUID, questionUUID );
        args.putString ( Constant.ARGS_QUESTION_TEXT, questionText );

        SurveyPageFragment fragment = new SurveyPageFragment ();
        fragment.setArguments ( args );
        return fragment;
    }

    public SurveyPageFragment() {
        // Required empty public constructor
    }

    private void initView(View view) {
        questionTextTxtView = (TextView) view.findViewById ( R.id.question_text_txtView );
        optionRecyclerView = (RecyclerView) view.findViewById ( R.id.survey_options_recyclerView );
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate ( R.layout.fragment_survey_page, container, false );

        questionUUID = (UUID) getArguments ().getSerializable ( Constant.ARGS_QUESTION_UUID );
        questionText = getArguments ().getString ( Constant.ARGS_QUESTION_TEXT );

        presenter = new SurveyPresenter ( this, getContext () );

        initView ( view );

        questionTextTxtView.setText ( questionText );
        optionRecyclerView.setLayoutManager ( new LinearLayoutManager ( getActivity () ) );

        presenter.loadOptions(questionUUID);

        return view;
    }

    @Override
    public void setUpPager(List<Question> questionList) {
    }

    @Override
    public void addNotAllowed() {
    }

    @Override
    public void addAllowed() {
    }

    @Override
    public void setUpOptionAdapter(List<Option> optionList) {
        optionRecyclerView.setAdapter ( new OptionAdapter ( getContext (), optionList ) );
    }
}

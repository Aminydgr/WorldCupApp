package com.example.amin.maktabprojectworldcupapp.survey.addSurvey;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.example.amin.maktabprojectworldcupapp.Constant;
import com.example.amin.maktabprojectworldcupapp.R;
import com.example.amin.maktabprojectworldcupapp.model.Option;
import com.example.amin.maktabprojectworldcupapp.survey.SurveyPagerActivity;
import com.example.amin.maktabprojectworldcupapp.survey.addSurvey.addOption.AddOptionDialogFragment;
import com.example.amin.maktabprojectworldcupapp.survey.addSurvey.addOption.AddOptionRecyclerAdapter;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class AddSurveyFragment extends Fragment implements IAddSurveyView, View.OnClickListener {

    private TextInputEditText questionTextTxtInEdTxt;
    private RecyclerView optionsRecyclerView;
    private Button addOptionBtn;
    private Button addQuestionBtn;
    private Button backBtn;

    private AddSurveyPresenter presenter;

    public static AddSurveyFragment newInstance() {

        Bundle args = new Bundle ();

        AddSurveyFragment fragment = new AddSurveyFragment ();
        fragment.setArguments ( args );
        return fragment;
    }

    public AddSurveyFragment() {
        // Required empty public constructor
    }

    private void initView(View view) {
        questionTextTxtInEdTxt = (TextInputEditText) view.findViewById ( R.id.question_text_txtInEdTxt );
        optionsRecyclerView = (RecyclerView) view.findViewById ( R.id.options_recyclerView );
        addOptionBtn = (Button) view.findViewById ( R.id.add_new_option_btn );
        addQuestionBtn = (Button) view.findViewById ( R.id.add_question_btn );
        backBtn = (Button) view.findViewById ( R.id.back_to_survey_btn );
    }

    public void showToast(String message) {
        Toast.makeText ( getActivity (), message, Toast.LENGTH_LONG ).show ();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate ( R.layout.fragment_ad_survey, container, false );

        presenter = new AddSurveyPresenter ( getContext (), this );

        initView ( view );

        addOptionBtn.setOnClickListener ( this );
        addQuestionBtn.setOnClickListener ( this );
        backBtn.setOnClickListener ( this );

        optionsRecyclerView.setLayoutManager ( new LinearLayoutManager ( getActivity () ) );
        presenter.getOptionList ();

        return view;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId ()) {
            case R.id.add_new_option_btn:
                presenter.addOptionDialog ();
                break;
            case R.id.add_question_btn:
                presenter.validInput ( questionTextTxtInEdTxt.getText ().toString () );
                break;
            case R.id.back_to_survey_btn:
                break;
        }
    }

    @Override
    public void showAddOptionDialog() {
        AddOptionDialogFragment addOptionDialogFragment = AddOptionDialogFragment.newInstance ();
        addOptionDialogFragment.setTargetFragment ( AddSurveyFragment.this, Constant.REQUEST_ADD_OPTION_DIALOG );
        addOptionDialogFragment.show ( getFragmentManager (), Constant.ADD_OPTION_DIALOG_TAG );
    }

    @Override
    public void setRecyclerAdapter(List<Option> optionList) {
        optionsRecyclerView.setAdapter ( new AddOptionRecyclerAdapter ( getContext (), optionList ) );
    }

    @Override
    public void uploadFailed(String errorMessage) {
        showToast ( errorMessage );
    }

    @Override
    public void uploadOk(String message) {
        showToast ( message );
        startActivity ( SurveyPagerActivity.newIntent ( getActivity () ) );
    }

    @Override
    public void showQuestionError(String errorMessage) {
        questionTextTxtInEdTxt.setError ( errorMessage );
    }

    @Override
    public void showOptionsCountError(String errorMessage) {
        showToast ( errorMessage );
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult ( requestCode, resultCode, data );

        if (resultCode != Activity.RESULT_OK)
            return;

        if (requestCode == Constant.REQUEST_ADD_OPTION_DIALOG) {
            presenter.optionAdded ( data.getStringExtra ( Constant.ARGS_OPTION_TEXT ) );
        }
    }
}

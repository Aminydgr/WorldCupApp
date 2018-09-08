package com.example.amin.maktabprojectworldcupapp.survey.addSurvey.addOption;


import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputEditText;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.amin.maktabprojectworldcupapp.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class AddOptionDialogFragment extends DialogFragment implements IAddOptionView, View.OnClickListener {

    private TextInputEditText optionTextTxtInEdTxt;
    private Button addOptionBtn;
    private Button backBtn;

    private AddOptionPresenter presenter;

    public static AddOptionDialogFragment newInstance() {

        Bundle args = new Bundle ();

        AddOptionDialogFragment fragment = new AddOptionDialogFragment ();
        fragment.setArguments ( args );
        return fragment;
    }

    public AddOptionDialogFragment() {
        // Required empty public constructor
    }

    private void initView(View view) {
        optionTextTxtInEdTxt = (TextInputEditText) view.findViewById ( R.id.option_text_txtInEdTxt );
        addOptionBtn = (Button) view.findViewById ( R.id.add_option_btn );
        backBtn = (Button) view.findViewById ( R.id.back_to_add_survey_btn );
    }


    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        View view = LayoutInflater.from ( getActivity () ).inflate ( R.layout.fragment_add_option_dialog, null );

        presenter = new AddOptionPresenter ( getActivity (), this );

        initView ( view );

        addOptionBtn.setOnClickListener ( this );
        backBtn.setOnClickListener ( this );

        return new AlertDialog.Builder ( getActivity () )
                .setTitle ( R.string.add_new_option )
                .setView ( view )
                .create ();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId ()) {
            case R.id.add_option_btn:
                presenter.validInput ( optionTextTxtInEdTxt.getText ().toString () );
                break;
            case R.id.back_to_add_survey_btn:
                dismiss ();
                break;
        }
    }

    @Override
    public void sendResult(Intent intent) {
        if (getTargetFragment () == null)
            return;
        getTargetFragment ().onActivityResult ( getTargetRequestCode (), Activity.RESULT_OK, intent );

        Toast.makeText ( getActivity (),"گزینه با موفقیت اضافه شد!" , Toast.LENGTH_SHORT ).show ();
        dismiss ();
    }

    @Override
    public void showOptionTextError(String message) {
        optionTextTxtInEdTxt.setError ( message );
    }
}

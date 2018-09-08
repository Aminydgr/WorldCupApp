package com.example.amin.maktabprojectworldcupapp.survey.addSurvey.addOption;

import android.content.Context;
import android.content.Intent;

import com.example.amin.maktabprojectworldcupapp.Constant;

/**
 * Created by Amin on 8/17/2018.
 */

public class AddOptionPresenter implements IAddOptionPresenter {

    private Context context;
    private IAddOptionView addOptionView;

    public AddOptionPresenter(Context context, IAddOptionView addOptionView) {
        this.context = context;
        this.addOptionView = addOptionView;
    }

    @Override
    public void validInput(String optionText) {
        if (optionText.length () > 0) {
            Intent intent = new Intent ();
            intent.putExtra ( Constant.ARGS_OPTION_TEXT, optionText );
            addOptionView.sendResult ( intent );
        } else
            addOptionView.showOptionTextError ( "متن گزینه را وارد کنید!" );
    }
}

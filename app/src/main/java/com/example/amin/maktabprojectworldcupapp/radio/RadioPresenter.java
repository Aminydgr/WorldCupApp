package com.example.amin.maktabprojectworldcupapp.radio;

import android.content.Context;

/**
 * Created by Amin on 8/17/2018.
 */

public class RadioPresenter implements IRadioPresenter{

    private Context context;
    private IRadioView radioView;

    public RadioPresenter(Context context, IRadioView radioView) {
        this.context = context;
        this.radioView = radioView;
    }
}

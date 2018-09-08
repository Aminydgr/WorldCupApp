package com.example.amin.maktabprojectworldcupapp.profile;

import android.content.Context;

import com.example.amin.maktabprojectworldcupapp.SharedPrefManager;

/**
 * Created by Amin on 9/1/2018.
 */

public class ProfilePresenter implements IProfilePresenter {

    private Context context;
    private IProfileView profileView;

    public ProfilePresenter(Context context, IProfileView profileView) {
        this.context = context;
        this.profileView = profileView;
    }

    @Override
    public void logout() {
        SharedPrefManager.getInstance ( context ).logout ();
    }
}

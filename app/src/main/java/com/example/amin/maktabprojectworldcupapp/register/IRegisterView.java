package com.example.amin.maktabprojectworldcupapp.register;

import android.content.DialogInterface;
import android.graphics.Bitmap;

/**
 * Created by Amin on 8/10/2018.
 */

public interface IRegisterView {
    void showChooserDialog();

    void hideChooserDialog(DialogInterface dialog);

    void showPermissionDialog();

    void requestPermissions();

    void cameraIntent();

    void galleryIntent();

    void setImageView(Bitmap bitmap);

    void showNameError(String errorMessage);

    void showPhoneNumberError(String errorMessage);

    void showPasswordError(String errorMessage);

    void showRepeatPassError(String errorMessage);

    void registerOk(String message);

    void registerFailed(String message);

    void backToLoginPage();
}

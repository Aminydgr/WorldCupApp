package com.example.amin.maktabprojectworldcupapp.register;

import android.content.DialogInterface;
import android.content.Intent;

/**
 * Created by Amin on 8/10/2018.
 */

public interface IRegisterPresenter {
    void back();

    void selectChooser();

    void requestPermissionResult(CharSequence userChosenTask);

    void onSelectedFromGalleryResult(Intent data);

    void onCaptureImageResult(Intent data);

    void validInput(String name, String phoneNumber, String password, String repeatPassword, boolean admin);

    boolean checkPermission();

    String selectImage(CharSequence item, DialogInterface dialog);
}

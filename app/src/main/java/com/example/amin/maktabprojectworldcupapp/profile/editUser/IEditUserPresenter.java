package com.example.amin.maktabprojectworldcupapp.profile.editUser;

import android.content.DialogInterface;
import android.content.Intent;

/**
 * Created by Amin on 9/1/2018.
 */

public interface IEditUserPresenter {
    void userInfo();

    void selectChooser();

    CharSequence selectImage(CharSequence item, DialogInterface dialog);

    void requestPermissionResult(CharSequence userChosenTask);

    void onCaptureImageResult(Intent data);

    void onSelectedFromGalleryResult(Intent data);

    void validInput(String name, String pass, String repeatPass);
}

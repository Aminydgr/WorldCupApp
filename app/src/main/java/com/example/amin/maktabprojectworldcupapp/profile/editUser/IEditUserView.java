package com.example.amin.maktabprojectworldcupapp.profile.editUser;

import android.content.DialogInterface;
import android.graphics.Bitmap;

import com.example.amin.maktabprojectworldcupapp.model.User;

/**
 * Created by Amin on 9/1/2018.
 */

public interface IEditUserView {
    void showUserInfo(User user);

    void showChooserDialog();

    void cameraIntent();

    void galleryIntent();

    void hideChooserDialog(DialogInterface dialog);

    void requestPermissions();

    void showPermissionDialog();

    void setImageView(Bitmap bitmap);

    void showNameError(String errorMessage);

    void showPasswordError(String errorMessage);

    void showRepeatPassError(String errorMessage);

    void editOk(String message);

    void editFailed(String message);
}

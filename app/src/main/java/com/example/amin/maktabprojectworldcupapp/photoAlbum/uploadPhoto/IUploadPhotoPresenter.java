package com.example.amin.maktabprojectworldcupapp.photoAlbum.uploadPhoto;

import android.content.DialogInterface;
import android.content.Intent;

/**
 * Created by Amin on 8/16/2018.
 */

public interface IUploadPhotoPresenter {
    void selectChooser();

    void requestPermissionResult(CharSequence userChosenTask);

    void onSelectedFromGalleryResult(Intent data);

    void onCaptureImageResult(Intent data);

    void validInput();

    boolean checkPermission();

    String selectImage(CharSequence item, DialogInterface dialog);

}

package com.example.amin.maktabprojectworldcupapp.photoAlbum.uploadPhoto;

import android.content.DialogInterface;
import android.graphics.Bitmap;

/**
 * Created by Amin on 8/16/2018.
 */

public interface IUploadPhotoView {
    void showChooserDialog();

    void hideChooserDialog(DialogInterface dialog);

    void showPermissionDialog();

    void requestPermissions();

    void cameraIntent();

    void galleryIntent();

    void setImageView(Bitmap bitmap);

    void showImgViewError(String errorMessage);

    void uploadFailed(String message);

    void uploadOk(String message);
}

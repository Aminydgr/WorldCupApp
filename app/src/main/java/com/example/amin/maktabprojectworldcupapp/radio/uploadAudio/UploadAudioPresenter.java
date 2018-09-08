package com.example.amin.maktabprojectworldcupapp.radio.uploadAudio;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;

import com.example.amin.maktabprojectworldcupapp.R;

/**
 * Created by Amin on 8/17/2018.
 */

public class UploadAudioPresenter implements IUploadAudioPresenter {

    private Context context;
    private IUploadAudioView uploadAudioView;

    public UploadAudioPresenter(Context context, IUploadAudioView uploadAudioView) {
        this.context = context;
        this.uploadAudioView = uploadAudioView;
    }

    @Override
    public void selectChooser() {
        uploadAudioView.showChooserDialog ();
    }

    @Override
    public void validInput() {

    }

    @Override
    public String selectAudio(CharSequence item, DialogInterface dialog) {
        String userChosenTask = null;
        if (item.equals ( context.getString ( R.string.gallery ) )) {
            userChosenTask = context.getString ( R.string.gallery );
            if (checkPermission ()) {
                uploadAudioView.galleryIntent ();
            }
        } else if (item.equals ( context.getString ( R.string.back ) )) {
            uploadAudioView.hideChooserDialog ( dialog );
        }
        return userChosenTask;
    }

    @Override
    public void onSelectedFromGalleryResult(Intent data) {

    }

    private boolean checkPermission() {
        //check permission at runtime for Marshmallow & greater than Marshmallow version
        int currentAPIVersion = Build.VERSION.SDK_INT;
        if (currentAPIVersion >= android.os.Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission ( context, Manifest.permission.READ_EXTERNAL_STORAGE )
                    != PackageManager.PERMISSION_GRANTED) {
                if (ActivityCompat.shouldShowRequestPermissionRationale (
                        (Activity) context, Manifest.permission.READ_EXTERNAL_STORAGE )) {
                    uploadAudioView.showPermissionDialog ();
                } else {
                    uploadAudioView.requestPermissions ();
                }
                return false;
            } else {
                return true;
            }
        } else {
            return true;
        }
    }
}
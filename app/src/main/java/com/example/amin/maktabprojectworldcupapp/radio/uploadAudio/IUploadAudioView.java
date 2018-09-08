package com.example.amin.maktabprojectworldcupapp.radio.uploadAudio;

import android.content.DialogInterface;

/**
 * Created by Amin on 8/17/2018.
 */

public interface IUploadAudioView {
    void showChooserDialog();

    void showPermissionDialog();

    void requestPermissions();

    void hideChooserDialog(DialogInterface dialog);

    void galleryIntent();
}

package com.example.amin.maktabprojectworldcupapp.radio.uploadAudio;

import android.content.DialogInterface;
import android.content.Intent;

/**
 * Created by Amin on 8/17/2018.
 */

public interface IUploadAudioPresenter {
    void selectChooser();

    void validInput();

    String selectAudio(CharSequence item, DialogInterface dialog);

    void onSelectedFromGalleryResult(Intent data);
}

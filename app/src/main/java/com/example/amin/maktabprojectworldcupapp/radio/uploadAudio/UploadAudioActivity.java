package com.example.amin.maktabprojectworldcupapp.radio.uploadAudio;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;

import com.example.amin.maktabprojectworldcupapp.SingleFragmentActivity;
import com.example.amin.maktabprojectworldcupapp.photoAlbum.uploadPhoto.UploadPhotoActivity;

public class UploadAudioActivity extends SingleFragmentActivity {

    public static Intent newIntent(Context packageContext) {
        Intent intent = new Intent ( packageContext, UploadAudioActivity.class );
        return intent;
    }

    @Override
    public Fragment createFragment() {
        return UploadAudioFragment.newInstance ();
    }
}

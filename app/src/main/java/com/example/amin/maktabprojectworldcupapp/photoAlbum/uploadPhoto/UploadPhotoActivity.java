package com.example.amin.maktabprojectworldcupapp.photoAlbum.uploadPhoto;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;

import com.example.amin.maktabprojectworldcupapp.SingleFragmentActivity;

public class UploadPhotoActivity extends SingleFragmentActivity {

    public static Intent newIntent(Context packageContext) {
        Intent intent = new Intent ( packageContext, UploadPhotoActivity.class );
        return intent;
    }

    @Override
    public Fragment createFragment() {
        return UploadPhotoFragment.newInstance ();
    }
}

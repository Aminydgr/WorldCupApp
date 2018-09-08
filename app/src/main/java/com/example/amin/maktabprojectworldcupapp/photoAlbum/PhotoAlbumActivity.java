package com.example.amin.maktabprojectworldcupapp.photoAlbum;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;

import com.example.amin.maktabprojectworldcupapp.SingleFragmentActivity;

public class PhotoAlbumActivity extends SingleFragmentActivity {

    public static Intent newIntent(Context packageContext) {
        Intent intent = new Intent ( packageContext, PhotoAlbumActivity.class );
        return intent;
    }

    @Override
    public Fragment createFragment() {
        return PhotoAlbumFragment.newInstance ();
    }
}

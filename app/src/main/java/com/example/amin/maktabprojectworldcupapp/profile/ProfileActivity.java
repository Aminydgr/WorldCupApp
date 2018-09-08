package com.example.amin.maktabprojectworldcupapp.profile;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;

import com.example.amin.maktabprojectworldcupapp.SingleFragmentActivity;

public class ProfileActivity extends SingleFragmentActivity {

    public static Intent newIntent(Context packageContext) {
        Intent intent = new Intent ( packageContext, ProfileActivity.class );
        return intent;
    }

    @Override
    public Fragment createFragment() {
        return ProfileFragment.newInstance ();
    }
}

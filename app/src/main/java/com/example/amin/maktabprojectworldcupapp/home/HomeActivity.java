package com.example.amin.maktabprojectworldcupapp.home;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;

import com.example.amin.maktabprojectworldcupapp.SingleFragmentActivity;

public class HomeActivity extends SingleFragmentActivity {

    public static Intent newIntent(Context packageContext) {
        Intent intent = new Intent ( packageContext, HomeActivity.class );
        return intent;
    }

    @Override
    public Fragment createFragment() {
        return HomeFragment.newInstance ();
    }
}

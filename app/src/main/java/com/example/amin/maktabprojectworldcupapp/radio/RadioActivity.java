package com.example.amin.maktabprojectworldcupapp.radio;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;

import com.example.amin.maktabprojectworldcupapp.SingleFragmentActivity;

public class RadioActivity extends SingleFragmentActivity {

    public static Intent newIntent(Context packageContext) {
        Intent intent = new Intent ( packageContext, RadioActivity.class );
        return intent;
    }

    @Override
    public Fragment createFragment() {
        return RadioFragment.newInstance ();
    }
}

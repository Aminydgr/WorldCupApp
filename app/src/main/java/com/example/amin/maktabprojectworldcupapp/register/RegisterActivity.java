package com.example.amin.maktabprojectworldcupapp.register;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;

import com.example.amin.maktabprojectworldcupapp.SingleFragmentActivity;

public class RegisterActivity extends SingleFragmentActivity {

    public static Intent newIntent(Context packageContext) {
        Intent intent = new Intent ( packageContext, RegisterActivity.class );
        return intent;
    }

    @Override
    public Fragment createFragment() {
        return RegisterFragment.newInstance ();
    }
}

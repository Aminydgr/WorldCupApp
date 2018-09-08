package com.example.amin.maktabprojectworldcupapp.login;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;

import com.example.amin.maktabprojectworldcupapp.Constant;
import com.example.amin.maktabprojectworldcupapp.SingleFragmentActivity;

public class LoginActivity extends SingleFragmentActivity {

    public static Intent newIntent(Context packageContext, String phone_number) {
        Intent intent = new Intent ( packageContext, LoginActivity.class );
        intent.putExtra ( Constant.EXTRA_PHONE_NUMBER, phone_number );
        return intent;
    }

    @Override
    public Fragment createFragment() {
        return LoginFragment.newInstance (getIntent ().getStringExtra ( Constant.EXTRA_PHONE_NUMBER ));
    }
}

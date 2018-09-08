package com.example.amin.maktabprojectworldcupapp.profile.editUser;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;

import com.example.amin.maktabprojectworldcupapp.SingleFragmentActivity;

public class EditUserActivity extends SingleFragmentActivity {

    public static Intent newIntent(Context packageContext) {
        Intent intent = new Intent ( packageContext, EditUserActivity.class );
        return intent;
    }

    @Override
    public Fragment createFragment() {
        return EditUserFragment.newInstance ();
    }
}

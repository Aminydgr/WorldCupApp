package com.example.amin.maktabprojectworldcupapp.chatRoom;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;

import com.example.amin.maktabprojectworldcupapp.SingleFragmentActivity;

public class ChatListActivity extends SingleFragmentActivity {

    public static Intent newIntent(Context packageContext) {
        Intent intent = new Intent ( packageContext, ChatListActivity.class );
        return intent;
    }

    @Override
    public Fragment createFragment() {
        return ChatListFragment.newInstance ();
    }
}

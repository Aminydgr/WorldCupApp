package com.example.amin.maktabprojectworldcupapp.chatRoom.chat;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;

import com.example.amin.maktabprojectworldcupapp.Constant;
import com.example.amin.maktabprojectworldcupapp.SingleFragmentActivity;

import java.util.UUID;

public class ChatActivity extends SingleFragmentActivity {

    public static Intent newIntent(Context packageContext, String chatRoomTitle, UUID chatRoomUuid) {
        Intent intent = new Intent ( packageContext, ChatActivity.class );
        intent.putExtra ( Constant.EXTRA_CHAT_ROOM_TITLE, chatRoomTitle );
        intent.putExtra ( Constant.EXTRA_CHAT_ROOM_UUID, chatRoomUuid );
        return intent;
    }

    @Override
    public Fragment createFragment() {
        return ChatFragment.newInstance ( getIntent ().getStringExtra ( Constant.EXTRA_CHAT_ROOM_TITLE ),
                (UUID) getIntent ().getSerializableExtra ( Constant.EXTRA_CHAT_ROOM_UUID ) );
    }
}

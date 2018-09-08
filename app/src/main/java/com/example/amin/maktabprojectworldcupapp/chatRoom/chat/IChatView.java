package com.example.amin.maktabprojectworldcupapp.chatRoom.chat;

import com.example.amin.maktabprojectworldcupapp.model.Chat;

import java.util.List;

/**
 * Created by Amin on 8/23/2018.
 */

public interface IChatView {
    void showDialog();

    void setUpAdapter(List<Chat> chatList);
}

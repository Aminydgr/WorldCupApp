package com.example.amin.maktabprojectworldcupapp.chatRoom;

import com.example.amin.maktabprojectworldcupapp.model.ChatRoom;

import java.util.List;

/**
 * Created by Amin on 8/23/2018.
 */

public interface IChatListView {
    void addNotAllowed();

    void addAllowed();

    void showProgressBar();

    void hideProgressBar();

    void setUpAdapter(List<ChatRoom> chatRoomList);
}

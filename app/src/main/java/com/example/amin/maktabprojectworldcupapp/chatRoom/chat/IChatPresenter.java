package com.example.amin.maktabprojectworldcupapp.chatRoom.chat;

import java.util.UUID;

/**
 * Created by Amin on 8/23/2018.
 */

public interface IChatPresenter {
    void addChat();

    void loadChat(UUID chatRoomUuid);
}

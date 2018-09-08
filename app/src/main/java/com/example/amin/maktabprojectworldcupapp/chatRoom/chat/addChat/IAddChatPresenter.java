package com.example.amin.maktabprojectworldcupapp.chatRoom.chat.addChat;

import com.example.amin.maktabprojectworldcupapp.model.User;

import java.util.UUID;

/**
 * Created by Amin on 8/30/2018.
 */

public interface IAddChatPresenter {
    void validInput(String chatText, User user, UUID chatRoomUuid);
}

package com.example.amin.maktabprojectworldcupapp.chatRoom.chat.addChat;

import java.util.UUID;

/**
 * Created by Amin on 8/30/2018.
 */

public interface IAddChatView {
    void uploadFailed(String message);

    void uploadOk(String message, UUID chatUUID, String userName, String chatText);

    void showChatTextError(String errorMessage);
}

package com.example.amin.maktabprojectworldcupapp.chatRoom.addChatRoom;

import java.util.UUID;

/**
 * Created by Amin on 8/23/2018.
 */

public interface IAddChatRoomView {
    void showChatRoomTitleError(String errorMessage);

    void uploadOk(String message, String chatRoomTitle, UUID chatRoomUUID);

    void uploadFailed(String message);
}

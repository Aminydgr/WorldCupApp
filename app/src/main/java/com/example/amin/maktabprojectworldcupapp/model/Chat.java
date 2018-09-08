package com.example.amin.maktabprojectworldcupapp.model;

import java.util.UUID;

/**
 * Created by Amin on 8/30/2018.
 */

public class Chat {

    private UUID uuid;
    private UUID chatRoomUUID;
    private String userName;
    private UUID userUUID;
    private String text;

    public Chat(UUID uuid, UUID chatRoomUUID, String userName, UUID userUUID, String text) {
        this.uuid = uuid;
        this.chatRoomUUID = chatRoomUUID;
        this.userName = userName;
        this.userUUID = userUUID;
        this.text = text;
    }

    public Chat(UUID uuid, String userName, String text) {
        this.uuid = uuid;
        this.userName = userName;
        this.text = text;
    }

    public UUID getUuid() {
        return uuid;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

    public UUID getChatRoomUUID() {
        return chatRoomUUID;
    }

    public void setChatRoomUUID(UUID chatRoomUUID) {
        this.chatRoomUUID = chatRoomUUID;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public UUID getUserUUID() {
        return userUUID;
    }

    public void setUserUUID(UUID userUUID) {
        this.userUUID = userUUID;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}

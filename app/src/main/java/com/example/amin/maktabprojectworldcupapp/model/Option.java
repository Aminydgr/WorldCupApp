package com.example.amin.maktabprojectworldcupapp.model;

import java.util.UUID;

/**
 * Created by Amin on 8/17/2018.
 */

public class Option {

    private UUID uuid;
    private UUID questionUUID;
    private String text;
    private int selectedCounter;

    public Option() {
        this.uuid = UUID.randomUUID ();
    }

    public Option(UUID uuid, UUID questionUUID, String text) {
        this.uuid = uuid;
        this.questionUUID = questionUUID;
        this.text = text;
    }

    public Option(UUID questionUUID, String text) {
        this.questionUUID = questionUUID;
        this.text = text;
        this.uuid = UUID.randomUUID ();
    }

    public Option( UUID uuid, UUID questionUUID, String text, int selectedCounter) {
        this.uuid = uuid;
        this.questionUUID = questionUUID;
        this.text = text;
        this.selectedCounter = selectedCounter;
    }

    public UUID getUuid() {
        return uuid;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

    public UUID getQuestionUUID() {
        return questionUUID;
    }

    public void setQuestionUUID(UUID questionUUID) {
        this.questionUUID = questionUUID;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public int getSelectedCounter() {
        return selectedCounter;
    }

    public void setSelectedCounter(int selectedCounter) {
        this.selectedCounter = selectedCounter;
    }
}

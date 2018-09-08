package com.example.amin.maktabprojectworldcupapp;

/**
 * Created by Amin on 8/13/2018.
 */

public class UrlEndPoints {
    private static final String MAIN_URL = "http://192.168.43.25/maktabProjectWorldCupApp/";

    public static final String UPLOAD_USER = MAIN_URL + "user.php?apicall=uploaduser";
    public static final String LOGIN_USER = MAIN_URL + "user.php?apicall=login";
    public static final String UPDATE_USER = MAIN_URL + "user.php?apicall=updateuser";
    public static final String UPLOAD_PHOTO = MAIN_URL + "photo?apicall=uploadpic";
    public static final String LOAD_PHOTOS = MAIN_URL + "photo?apicall=getpics";
    public static final String UPLOAD_QUESTION = MAIN_URL + "question.php?apicall=uploadquestion";
    public static final String LOAD_QUESTIONS = MAIN_URL + "question.php?apicall=getquestions";
    public static final String UPLOAD_OPTION = MAIN_URL + "option.php?apicall=uploadoption";
    public static final String LOAD_OPTIONS = MAIN_URL + "option.php?apicall=getoptions";
    public static final String UPLOAD_CHAT_ROOM = MAIN_URL + "chatRoom.php?apicall=uploadchatroom";
    public static final String LOAD_CHAT_ROOMS = MAIN_URL + "chatRoom.php?apicall=getchatrooms";
    public static final String UPLOAD_CHAT = MAIN_URL + "chat.php?apicall=uploadchat";
    public static final String LOAD_CHAT = MAIN_URL + "chat.php?apicall=getchats";
}

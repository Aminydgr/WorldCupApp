package com.example.amin.maktabprojectworldcupapp.chatRoom;

import android.content.Context;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.amin.maktabprojectworldcupapp.Constant;
import com.example.amin.maktabprojectworldcupapp.SharedPrefManager;
import com.example.amin.maktabprojectworldcupapp.UrlEndPoints;
import com.example.amin.maktabprojectworldcupapp.model.ChatRoom;
import com.example.amin.maktabprojectworldcupapp.model.User;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Created by Amin on 8/23/2018.
 */

public class ChatListPresenter implements IChatListPresenter {

    private IChatListView chatListView;
    private Context context;
    private List<ChatRoom> chatRoomList;

    public ChatListPresenter(IChatListView chatListView, Context context) {
        this.chatListView = chatListView;
        this.context = context;
        this.chatRoomList = new ArrayList<> ();
    }

    @Override
    public void allowedUser() {
        User user = SharedPrefManager.getInstance ( context ).getUser ();
        if (user.getAdmin ())
            chatListView.addAllowed ();
        else
            chatListView.addNotAllowed ();
    }

    @Override
    public void loadChatRoom() {
        chatListView.showProgressBar ();

        StringRequest stringRequest = new StringRequest ( Request.Method.GET, UrlEndPoints.LOAD_CHAT_ROOMS,
                new Response.Listener<String> () {
                    @Override
                    public void onResponse(String response) {
                        chatListView.hideProgressBar ();

                        try {
                            JSONObject obj = new JSONObject ( response );

                            JSONArray jsonArray = obj.getJSONArray ( "chatrooms" );

                            for (int i = 0; i < jsonArray.length (); i++) {
                                JSONObject chatRoomObj = jsonArray.getJSONObject ( i );

                                chatRoomList.add ( new ChatRoom (
                                        UUID.fromString ( chatRoomObj.getString ( "uuid" ) ),
                                        chatRoomObj.getString ( "title" )
                                ) );
                            }
                            chatListView.setUpAdapter (chatRoomList);
                        } catch (JSONException e) {
                            Log.i ( Constant.SERVER_LOG, "onResponseError" + e.getMessage () );
                        }
                    }
                }, new Response.ErrorListener () {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.i ( Constant.SERVER_LOG, error.getMessage () );
            }
        } );

        Volley.newRequestQueue ( context ).add ( stringRequest );
    }
}

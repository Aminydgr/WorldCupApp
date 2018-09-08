package com.example.amin.maktabprojectworldcupapp.chatRoom.chat;

import android.content.Context;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.amin.maktabprojectworldcupapp.Constant;
import com.example.amin.maktabprojectworldcupapp.UrlEndPoints;
import com.example.amin.maktabprojectworldcupapp.model.Chat;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * Created by Amin on 8/23/2018.
 */

public class ChatPresenter implements IChatPresenter {

    private Context context;
    private IChatView chatView;
    private List<Chat> chatList;

    public ChatPresenter(Context context, IChatView chatView) {
        this.context = context;
        this.chatView = chatView;
        this.chatList = new ArrayList<> ();
    }

    @Override
    public void addChat() {
        chatView.showDialog ();
    }

    @Override
    public void loadChat(final UUID chatRoomUuid) {
        StringRequest stringRequest = new StringRequest ( Request.Method.DEPRECATED_GET_OR_POST, UrlEndPoints.LOAD_CHAT,
                new Response.Listener<String> () {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject ( response );

                            if (!jsonObject.getBoolean ( "error" )) {
                                JSONArray jsonArray = jsonObject.getJSONArray ( "chats" );
                                for (int i = 0; i < jsonArray.length (); i++) {

                                    JSONObject chatJson = jsonArray.getJSONObject ( i );

                                    chatList.add ( new Chat ( UUID.fromString ( chatJson.getString ( "uuid" ) ),
                                            UUID.fromString ( chatJson.getString ( "chatRoomUUID" ) ),
                                            chatJson.getString ( "userName" ),
                                            UUID.fromString ( chatJson.getString ( "userUUID" ) ),
                                            chatJson.getString ( "text" ) ) );
                                }
                                chatView.setUpAdapter ( chatList );
                            }
                        } catch (JSONException e) {
                            Log.i ( Constant.SERVER_LOG, "onResponseError" + e.getMessage () );
                        }
                    }
                }, new Response.ErrorListener () {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.i ( Constant.SERVER_LOG, error.getMessage () );
            }
        } ) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<> ();
                params.put ( "chatRoomUUID", chatRoomUuid.toString () );
                return params;
            }
        };
        Volley.newRequestQueue ( context ).add ( stringRequest );
    }
}

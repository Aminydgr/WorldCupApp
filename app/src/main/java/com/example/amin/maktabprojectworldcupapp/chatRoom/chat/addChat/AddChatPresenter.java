package com.example.amin.maktabprojectworldcupapp.chatRoom.chat.addChat;

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
import com.example.amin.maktabprojectworldcupapp.model.User;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * Created by Amin on 8/30/2018.
 */

public class AddChatPresenter implements IAddChatPresenter {

    private Context context;
    private IAddChatView addChatView;

    public AddChatPresenter(Context context, IAddChatView addChatView) {
        this.context = context;
        this.addChatView = addChatView;
    }

    @Override
    public void validInput(String chatText, User user, UUID chatRoomUuid) {
        if (chatText.length () > 0)
            uploadChat(chatText, user, chatRoomUuid);
        else
            addChatView.showChatTextError("پیام نباید خالی باشد!");
    }

    private void uploadChat(final String chatText, final User user, final UUID chatRoomUuid) {
        final UUID chatUUID =  UUID.randomUUID ();
        StringRequest stringRequest = new StringRequest ( Request.Method.POST, UrlEndPoints.UPLOAD_CHAT,
                new Response.Listener<String> () {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject obj = new JSONObject ( response );
                            if (obj.getString ( "message" ).equals ( "پیام شما با موفقیت فرستاده شد!" ))
                                addChatView.uploadOk ( obj.getString ( "message" ), chatUUID, user.getName (), chatText );
                            else
                                addChatView.uploadFailed ( obj.getString ( "message" ) );
                        } catch (JSONException e) {
                            Log.i ( Constant.SERVER_LOG, "error in onResponse -> " + e.getMessage () );
                        }
                    }
                }, new Response.ErrorListener () {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.i ( Constant.SERVER_LOG, "error in onErrorResponse -> " + error.getMessage () );
            }
        } ) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<> ();
                params.put ( "uuid", chatUUID.toString () );
                params.put ( "chatRoomUUID", chatRoomUuid.toString () );
                params.put ( "userName", user.getName () );
                params.put ( "userUUID", user.getUuid ().toString () );
                params.put ( "text", chatText );
                Log.i ( Constant.SERVER_LOG, "getParams()" );
                return params;
            }
        };

        Volley.newRequestQueue ( context ).add ( stringRequest );
    }
}

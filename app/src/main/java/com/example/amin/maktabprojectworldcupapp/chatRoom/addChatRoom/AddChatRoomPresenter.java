package com.example.amin.maktabprojectworldcupapp.chatRoom.addChatRoom;

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

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * Created by Amin on 8/23/2018.
 */

public class AddChatRoomPresenter implements IAddChatRoomPresenter {

    private IAddChatRoomView addChatRoomView;
    private Context context;

    public AddChatRoomPresenter(IAddChatRoomView addChatRoomView, Context context) {
        this.addChatRoomView = addChatRoomView;
        this.context = context;
    }

    @Override
    public void validInput(String chatRoomTitle) {
        if (chatRoomTitle.length () >= 5)
            uploadChatRoom(chatRoomTitle);
        else
            addChatRoomView.showChatRoomTitleError("عنوان نباید کمتر از 5 حرف داشته باشد!");
    }

    private void uploadChatRoom(final String chatRoomTitle) {
        final UUID chatRoomUUID = UUID.randomUUID ();
        StringRequest stringRequest = new StringRequest ( Request.Method.POST, UrlEndPoints.UPLOAD_CHAT_ROOM,
                new Response.Listener<String> () {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject obj = new JSONObject ( response );
                            if (obj.getString ( "message" ).equals ( "اتاق گفت و گو با موفقیت ثبت شد!" ))
                                addChatRoomView.uploadOk ( obj.getString ( "message" ), chatRoomTitle, chatRoomUUID );
                            else
                                addChatRoomView.uploadFailed ( obj.getString ( "message" ) );
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
                params.put ( "uuid", chatRoomUUID.toString () );
                params.put ( "title", chatRoomTitle );
                Log.i ( Constant.SERVER_LOG, "getParams()" );
                return params;
            }
        };

        Volley.newRequestQueue ( context ).add ( stringRequest );
    }
}

package com.example.amin.maktabprojectworldcupapp.login;

import android.content.Context;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;
import com.example.amin.maktabprojectworldcupapp.Constant;
import com.example.amin.maktabprojectworldcupapp.UrlEndPoints;
import com.example.amin.maktabprojectworldcupapp.model.User;
import com.example.amin.maktabprojectworldcupapp.server.VolleyMultipartRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Amin on 8/10/2018.
 */

public class LoginPresenter implements ILoginPresenter {

    private Context context;
    private User user;
    private ILoginView loginView;

    public LoginPresenter(ILoginView loginView, Context context) {
        this.context = context;
        this.loginView = loginView;
    }


    @Override
    public void register() {
        loginView.goToRegisterPage ();
    }

    @Override
    public void validInput(String phoneNumber, String password, boolean rememberMe) {
        if (validPhoneNumber ( phoneNumber ) && password.length () > 0) {
            userLogin ( phoneNumber, password, rememberMe );
        } else {
            if (!validPhoneNumber ( phoneNumber ))
                loginView.showPhoneNumberError ( "شماره وارد شده معتبر نیست!" );
            if (password.length () == 0)
                loginView.showPasswordError ( "لطفا رمز عبور را وارد نمایید!" );
        }
    }

    private void userLogin(final String phoneNumber, final String password, boolean rememberMe) {
        VolleyMultipartRequest volleyMultipartRequest = new VolleyMultipartRequest ( Request.Method.POST, UrlEndPoints.LOGIN_USER,
                new Response.Listener<NetworkResponse> () {
                    @Override
                    public void onResponse(NetworkResponse response) {
                        try {
                            JSONObject obj = new JSONObject ( new String ( response.data ) );
                            if (!obj.getBoolean ( "error" )) {
                                JSONObject userJson = obj.getJSONObject ( "user" );

                                user = new User ( UUID.fromString ( userJson.getString ( "uuid" ) ),
                                        userJson.getString ( "name" ),
                                        userJson.getString ( "phoneNumber" ),
                                        userJson.getString ( "password" ),
                                        userJson.getString ( "photoPath" ) );
                                if (userJson.getString ( "admin" ).equals ( "yes" ))
                                    user.setAdmin ( true );
                                else
                                    user.setAdmin ( false );

                                loginView.loginOK ( user, obj.getString ( "message" ) );
                            } else
                                loginView.loginFailed ( obj.getString ( "message" ) );
                        } catch (JSONException e) {
                            Log.i ( Constant.SERVER_LOG, e.getMessage () );
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
                params.put ( "phoneNumber", phoneNumber );
                params.put ( "password", password );
                return params;
            }
        };

        Volley.newRequestQueue ( context ).add ( volleyMultipartRequest );
    }

    private boolean validPhoneNumber(String phoneNumber) {
        String mobileReg = "(0|\\+98)?([ ]|,|-|[()]){0,2}9[1|2|3|4]([ ]|,|-|[()]){0,2}(?:[0-9]([ ]|,|-|[()]){0,2}){8}";
        Pattern pattern = Pattern.compile ( mobileReg );
        Matcher matcher = pattern.matcher ( phoneNumber );
        return matcher.matches ();
    }
}

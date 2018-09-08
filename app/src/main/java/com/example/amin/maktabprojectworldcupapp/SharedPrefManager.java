package com.example.amin.maktabprojectworldcupapp;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.amin.maktabprojectworldcupapp.login.LoginActivity;
import com.example.amin.maktabprojectworldcupapp.model.User;

import java.util.UUID;

import static com.example.amin.maktabprojectworldcupapp.Constant.KEY_IS_AdMIN;
import static com.example.amin.maktabprojectworldcupapp.Constant.KEY_PHONE_NUMBER;
import static com.example.amin.maktabprojectworldcupapp.Constant.KEY_REMEMBER_USER;
import static com.example.amin.maktabprojectworldcupapp.Constant.KEY_USER_NAME;
import static com.example.amin.maktabprojectworldcupapp.Constant.KEY_USER_PASS;
import static com.example.amin.maktabprojectworldcupapp.Constant.KEY_USER_PHOTO;
import static com.example.amin.maktabprojectworldcupapp.Constant.KEY_USER_UUID;
import static com.example.amin.maktabprojectworldcupapp.Constant.SHARED_PREF_NAME;

/**
 * Created by Amin on 8/14/2018.
 */

public class SharedPrefManager {

    private static SharedPrefManager instance;
    private Context context;

    public SharedPrefManager(Context context) {
        this.context = context;
    }

    public static synchronized SharedPrefManager getInstance(Context context) {
        if (instance == null) {
            instance = new SharedPrefManager ( context );
        }
        return instance;
    }

    //method to let the user login
    //this method will store the user data in shared preferences
    public void userLogin(User user) {
        SharedPreferences sharedPreferences = context.getSharedPreferences ( SHARED_PREF_NAME, Context.MODE_PRIVATE );
        SharedPreferences.Editor editor = sharedPreferences.edit ();
        editor.putString ( KEY_PHONE_NUMBER, user.getPhoneNumber () );
        editor.putString ( KEY_USER_NAME, user.getName () );
        editor.putBoolean ( KEY_IS_AdMIN, user.getAdmin () );
        editor.putString ( KEY_USER_PHOTO, user.getPhotoPath () );
        editor.putString ( KEY_USER_PASS, user.getPassword () );
        editor.putString ( KEY_USER_UUID, user.getUuid ().toString () );
        editor.apply ();
    }

    //this method will checker whether user is already logged in or not
    public boolean isLoggedIn() {
        SharedPreferences sharedPreferences = context.getSharedPreferences ( SHARED_PREF_NAME, Context.MODE_PRIVATE );
        return sharedPreferences.getString ( KEY_PHONE_NUMBER, null ) != null;
    }

    //this method will give the logged in user
    public User getUser() {
        SharedPreferences sharedPreferences = context.getSharedPreferences ( SHARED_PREF_NAME, Context.MODE_PRIVATE );
        return new User (
                UUID.fromString ( sharedPreferences.getString ( KEY_USER_UUID, null ) ),
                sharedPreferences.getString ( KEY_USER_NAME, null ),
                sharedPreferences.getString ( KEY_PHONE_NUMBER, null ),
                sharedPreferences.getString ( KEY_USER_PASS, null ),
                sharedPreferences.getString ( KEY_USER_PHOTO, null ),
                sharedPreferences.getBoolean ( KEY_IS_AdMIN, false )
        );
    }

    public void setRememberUser(String phoneNumber) {
        SharedPreferences sharedPreferences = context.getSharedPreferences ( SHARED_PREF_NAME, Context.MODE_PRIVATE );
        SharedPreferences.Editor editor = sharedPreferences.edit ();
        editor.putString ( KEY_REMEMBER_USER, phoneNumber );
        editor.apply ();
    }

    public String getRememberUser() {
        SharedPreferences sharedPreferences = context.getSharedPreferences ( SHARED_PREF_NAME, Context.MODE_PRIVATE );
        return sharedPreferences.getString ( KEY_REMEMBER_USER, null );
    }

    //this method will logout the user
    public void logout() {
        SharedPreferences sharedPreferences = context.getSharedPreferences ( SHARED_PREF_NAME, Context.MODE_PRIVATE );
        SharedPreferences.Editor editor = sharedPreferences.edit ();
        editor.clear ();
        editor.apply ();
        context.startActivity ( LoginActivity.newIntent ( context, null ) );
    }
}

package com.example.amin.maktabprojectworldcupapp.login;

/**
 * Created by Amin on 8/10/2018.
 */

public interface ILoginPresenter {
    void register();

    void validInput(String phoneNumber, String password, boolean rememberMe);
}

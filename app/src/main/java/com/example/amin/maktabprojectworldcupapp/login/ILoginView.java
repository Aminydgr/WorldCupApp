package com.example.amin.maktabprojectworldcupapp.login;

import com.example.amin.maktabprojectworldcupapp.model.User;

/**
 * Created by Amin on 8/10/2018.
 */

public interface ILoginView {
    void goToRegisterPage();

    void showPasswordError(String errorMessage);

    void showPhoneNumberError(String errorMessage);

    void loginOK(User user, String message);

    void loginFailed(String message);
}

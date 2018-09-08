package com.example.amin.maktabprojectworldcupapp.login;


import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Toast;

import com.example.amin.maktabprojectworldcupapp.Constant;
import com.example.amin.maktabprojectworldcupapp.R;
import com.example.amin.maktabprojectworldcupapp.SharedPrefManager;
import com.example.amin.maktabprojectworldcupapp.home.HomeActivity;
import com.example.amin.maktabprojectworldcupapp.model.User;
import com.example.amin.maktabprojectworldcupapp.register.RegisterActivity;


/**
 * A simple {@link Fragment} subclass.
 */
public class LoginFragment extends Fragment implements ILoginView, View.OnClickListener {

    private TextInputEditText phoneNumberTxtInEdTxt;
    private TextInputEditText passwordTxtInEdTxt;
    private CheckBox rememberMeChBox;
    private Button loginBtn;
    private Button registerBtn;

    private LoginPresenter presenter;

    public static LoginFragment newInstance(String phoneNumber) {

        Bundle args = new Bundle ();
        args.putString ( Constant.ARGS_PHONE_NUMBER, phoneNumber );

        LoginFragment fragment = new LoginFragment ();
        fragment.setArguments ( args );
        return fragment;
    }

    public LoginFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate ( R.layout.fragment_login, container, false );

        String phoneNumber = getArguments ().getString ( Constant.ARGS_PHONE_NUMBER );
        String rememberPhoneNumber = SharedPrefManager.getInstance ( getContext () ).getRememberUser ();
        presenter = new LoginPresenter ( this, getContext () );

        initView ( view );

        if (phoneNumber != null) {
            phoneNumberTxtInEdTxt.setText ( phoneNumber );
        }
        if (rememberPhoneNumber != null) {
            phoneNumberTxtInEdTxt.setText ( rememberPhoneNumber );
            rememberMeChBox.setChecked ( true );
            rememberMeChBox.setClickable ( false );
        }

        loginBtn.setOnClickListener ( this );
        registerBtn.setOnClickListener ( this );

        return view;
    }

    private void initView(View view) {
        phoneNumberTxtInEdTxt = (TextInputEditText) view.findViewById ( R.id.login_page_phone_number_txtInEdTxt );
        passwordTxtInEdTxt = (TextInputEditText) view.findViewById ( R.id.login_page_password_txtInEdTxt );
        rememberMeChBox = (CheckBox) view.findViewById ( R.id.remember_me_chBox );
        loginBtn = (Button) view.findViewById ( R.id.login_btn );
        registerBtn = (Button) view.findViewById ( R.id.login_page_register_btn );
    }

    public void showToast(String message) {
        Toast.makeText ( getActivity (), message, Toast.LENGTH_LONG ).show ();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId ()) {
            case R.id.login_page_register_btn:
                presenter.register ();
                break;
            case R.id.login_btn:
                presenter.validInput ( phoneNumberTxtInEdTxt.getText ().toString (), passwordTxtInEdTxt.getText ().toString (),
                        rememberMeChBox.isChecked () );
                break;
        }
    }

    @Override
    public void goToRegisterPage() {
        startActivity ( RegisterActivity.newIntent ( getActivity () ) );
    }

    @Override
    public void showPasswordError(String errorMessage) {
        passwordTxtInEdTxt.setError ( errorMessage );
    }

    @Override
    public void showPhoneNumberError(String errorMessage) {
        phoneNumberTxtInEdTxt.setError ( errorMessage );
    }

    @Override
    public void loginOK(User user, String message) {
        showToast ( message );

        SharedPrefManager.getInstance ( getContext () ).userLogin ( user );
        if (rememberMeChBox.isChecked ())
            SharedPrefManager.getInstance ( getContext () ).setRememberUser ( user.getPhoneNumber () );

        startActivity ( HomeActivity.newIntent ( getActivity () ) );
    }

    @Override
    public void loginFailed(String message) {
        showToast ( message );
    }
}

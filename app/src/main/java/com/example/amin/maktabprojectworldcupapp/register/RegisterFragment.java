package com.example.amin.maktabprojectworldcupapp.register;


import android.Manifest;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.amin.maktabprojectworldcupapp.Constant;
import com.example.amin.maktabprojectworldcupapp.R;
import com.example.amin.maktabprojectworldcupapp.login.LoginActivity;


/**
 * A simple {@link Fragment} subclass.
 */
public class RegisterFragment extends Fragment implements IRegisterView, View.OnClickListener {

    private ImageView photoImgView;
    private TextInputEditText nameTxtInEdTxt;
    private TextInputEditText phoneNumberTxtInEdTxt;
    private TextInputEditText passwordTxtInEdTxt;
    private TextInputEditText repeatPasswordTxtInEdTxt;
    private CheckBox adminChBox;
    private Button choosePhotoBtn;
    private Button registerBtn;
    private Button backBtn;

    private RegisterPresenter presenter;
    private CharSequence userChosenTask;


    public static RegisterFragment newInstance() {

        Bundle args = new Bundle ();

        RegisterFragment fragment = new RegisterFragment ();
        fragment.setArguments ( args );
        return fragment;
    }

    public RegisterFragment() {
        // Required empty public constructor
    }

    private void initView(View view) {
        photoImgView = (ImageView) view.findViewById ( R.id.user_photo_register_page_imgView );
        nameTxtInEdTxt = (TextInputEditText) view.findViewById ( R.id.user_name_register_page_txtInEdTxt );
        phoneNumberTxtInEdTxt = (TextInputEditText) view.findViewById ( R.id.user_phone_number_register_page_txtInEdTxt );
        passwordTxtInEdTxt = (TextInputEditText) view.findViewById ( R.id.user_password_register_page_txtInEdTxt );
        repeatPasswordTxtInEdTxt = (TextInputEditText) view.findViewById ( R.id.repeat_password_register_page_txtInEdTxt );
        adminChBox = (CheckBox) view.findViewById ( R.id.is_admin_checkBox );
        choosePhotoBtn = (Button) view.findViewById ( R.id.choose_photo_register_page_btn );
        registerBtn = (Button) view.findViewById ( R.id.register_btn );
        backBtn = (Button) view.findViewById ( R.id.back_to_login_btn );
    }

    private void externalPermission() {
        ActivityCompat.requestPermissions ( getActivity (),
                new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                Constant.PERMISSION_REQUEST_READ_EXTERNAL_STORAGE );
    }

    public void showToast(String message) {
        Toast.makeText ( getActivity (), message, Toast.LENGTH_LONG ).show ();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate ( R.layout.fragment_register, null );

        presenter = new RegisterPresenter ( this, getContext () );

        initView ( view );
        choosePhotoBtn.setOnClickListener ( this );
        registerBtn.setOnClickListener ( this );
        backBtn.setOnClickListener ( this );

        return view;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId ()) {
            case R.id.choose_photo_register_page_btn:
                presenter.selectChooser ();
                break;
            case R.id.register_btn:
                presenter.validInput ( nameTxtInEdTxt.getText ().toString (), phoneNumberTxtInEdTxt.getText ().toString (),
                        passwordTxtInEdTxt.getText ().toString (), repeatPasswordTxtInEdTxt.getText ().toString (),
                        adminChBox.isChecked () );
                break;
            case R.id.back_to_login_btn:
                presenter.back ();
        }
    }

    @Override
    public void showChooserDialog() {
        final CharSequence[] items = {getString ( R.string.camera ), getString ( R.string.gallery ), getString ( R.string.back )};
        final AlertDialog.Builder builder = new AlertDialog.Builder ( getActivity () );
        builder.setTitle ( R.string.choose_photo )
                .setItems ( items, new DialogInterface.OnClickListener () {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        userChosenTask = presenter.selectImage ( items[which], dialog );
                    }
                } )
                .show ();
    }

    @Override
    public void hideChooserDialog(DialogInterface dialog) {
        dialog.dismiss ();
    }

    @Override
    public void showPermissionDialog() {
        new AlertDialog.Builder ( getActivity () )
                .setCancelable ( true )
                .setTitle ( "Permission necessary" )
                .setMessage ( "External storage permission is necessary" )
                .setPositiveButton ( android.R.string.yes, new DialogInterface.OnClickListener () {
                    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
                    public void onClick(DialogInterface dialog, int which) {
                        externalPermission ();
                    }
                } )
                .create ()
                .show ();
    }

    @Override
    public void requestPermissions() {
        externalPermission ();
    }

    @Override
    public void cameraIntent() {
        Intent intent = new Intent ( MediaStore.ACTION_IMAGE_CAPTURE );
        startActivityForResult ( intent, Constant.REQUEST_CAMERA );
    }

    @Override
    public void galleryIntent() {
        Intent intent = new Intent ( Intent.ACTION_GET_CONTENT );
        intent.setType ( "image/*" );
        startActivityForResult ( Intent.createChooser ( intent, getString ( R.string.choose_file ) ), Constant.REQUEST_GALLERY );
    }

    @Override
    public void setImageView(Bitmap bitmap) {
        photoImgView.setImageBitmap ( bitmap );
    }


    @Override
    public void showNameError(String errorMessage) {
        nameTxtInEdTxt.setError ( errorMessage );
    }

    @Override
    public void showPhoneNumberError(String errorMessage) {
        phoneNumberTxtInEdTxt.setError ( errorMessage );
    }

    @Override
    public void showPasswordError(String errorMessage) {
        passwordTxtInEdTxt.setError ( errorMessage );
    }

    @Override
    public void showRepeatPassError(String errorMessage) {
        repeatPasswordTxtInEdTxt.setError ( errorMessage );
    }

    @Override
    public void registerOk(String message) {
        showToast ( message );
        startActivity ( LoginActivity.newIntent ( getActivity (), phoneNumberTxtInEdTxt.getText ().toString () ) );
    }

    @Override
    public void registerFailed(String message) {
        showToast ( message );
    }

    @Override
    public void backToLoginPage() {
        startActivity ( LoginActivity.newIntent ( getActivity (), null ) );
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult ( requestCode, permissions, grantResults );
        if (requestCode == Constant.PERMISSION_REQUEST_READ_EXTERNAL_STORAGE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED)
                presenter.requestPermissionResult ( userChosenTask );
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult ( requestCode, resultCode, data );
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == Constant.REQUEST_CAMERA)
                presenter.onCaptureImageResult ( data );
            else if (requestCode == Constant.REQUEST_GALLERY)
                presenter.onSelectedFromGalleryResult ( data );
        }
    }
}

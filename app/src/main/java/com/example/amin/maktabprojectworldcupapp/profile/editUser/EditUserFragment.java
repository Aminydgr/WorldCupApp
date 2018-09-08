package com.example.amin.maktabprojectworldcupapp.profile.editUser;


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
import android.support.design.widget.TextInputEditText;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.amin.maktabprojectworldcupapp.Constant;
import com.example.amin.maktabprojectworldcupapp.R;
import com.example.amin.maktabprojectworldcupapp.model.User;
import com.example.amin.maktabprojectworldcupapp.profile.ProfileActivity;

/**
 * A simple {@link Fragment} subclass.
 */
public class EditUserFragment extends Fragment implements IEditUserView, View.OnClickListener {

    private ImageView photoImgView;
    private TextInputEditText nameTxtInEdTxt;
    private TextInputEditText passTxtInEdTxt;
    private TextInputEditText repeatPassTxtInEdTxt;
    private Button changePhotoBtn;
    private Button editBtn;
    private Button backBtn;

    private EditUserPresenter presenter;
    private CharSequence userChosenTask;

    public static EditUserFragment newInstance() {

        Bundle args = new Bundle ();

        EditUserFragment fragment = new EditUserFragment ();
        fragment.setArguments ( args );
        return fragment;
    }

    public EditUserFragment() {
        // Required empty public constructor
    }

    private void initView(View view) {
        photoImgView = (ImageView) view.findViewById ( R.id.user_photo_imgView );
        nameTxtInEdTxt = (TextInputEditText) view.findViewById ( R.id.user_name_txtInEdTxt );
        passTxtInEdTxt = (TextInputEditText) view.findViewById ( R.id.user_password_txtInEdTxt );
        repeatPassTxtInEdTxt = (TextInputEditText) view.findViewById ( R.id.repeat_password_txtInEdTxt );
        changePhotoBtn = (Button) view.findViewById ( R.id.change_photo_btn );
        editBtn = (Button) view.findViewById ( R.id.edit_btn );
        backBtn = (Button) view.findViewById ( R.id.back_to_profile_btn );
    }

    public void showToast(String message) {
        Toast.makeText ( getActivity (), message, Toast.LENGTH_LONG ).show ();
    }

    private void externalPermission() {
        ActivityCompat.requestPermissions ( getActivity (),
                new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                Constant.PERMISSION_REQUEST_READ_EXTERNAL_STORAGE );
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate ( R.layout.fragment_edit_user, container, false );

        presenter = new EditUserPresenter ( getContext (), this );

        initView ( view );

        presenter.userInfo ();

        changePhotoBtn.setOnClickListener ( this );
        editBtn.setOnClickListener ( this );
        backBtn.setOnClickListener ( this );

        return view;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId ()) {
            case R.id.change_photo_btn:
                presenter.selectChooser ();
                break;
            case R.id.edit_btn:
                presenter.validInput ( nameTxtInEdTxt.getText ().toString (), passTxtInEdTxt.getText ().toString (),
                        repeatPassTxtInEdTxt.getText ().toString () );
                break;
            case R.id.back_to_profile_btn:
                startActivity ( ProfileActivity.newIntent ( getActivity () ) );
                showToast ( "تغییرات ذخیره نشد!" );
                break;
        }
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

    @Override
    public void showUserInfo(User user) {
        Glide.with ( getContext () )
                .load ( user.getPhotoPath () )
                .into ( photoImgView );
        nameTxtInEdTxt.setText ( user.getName () );
        passTxtInEdTxt.setText ( user.getPassword () );
        repeatPassTxtInEdTxt.setText ( user.getPassword () );
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
    public void hideChooserDialog(DialogInterface dialog) {
        dialog.dismiss ();
    }

    @Override
    public void requestPermissions() {
        externalPermission ();
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
    public void setImageView(Bitmap bitmap) {
        photoImgView.setImageBitmap ( bitmap );
    }

    @Override
    public void showNameError(String errorMessage) {
        nameTxtInEdTxt.setError ( errorMessage );
    }

    @Override
    public void showPasswordError(String errorMessage) {
        passTxtInEdTxt.setError ( errorMessage );
    }

    @Override
    public void showRepeatPassError(String errorMessage) {
        repeatPassTxtInEdTxt.setError ( errorMessage );
    }

    @Override
    public void editOk(String message) {
        showToast ( message );
        startActivity ( ProfileActivity.newIntent ( getActivity () ) );
    }

    @Override
    public void editFailed(String message) {
        showToast ( message );
    }
}
package com.example.amin.maktabprojectworldcupapp.photoAlbum.uploadPhoto;


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
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.amin.maktabprojectworldcupapp.Constant;
import com.example.amin.maktabprojectworldcupapp.R;
import com.example.amin.maktabprojectworldcupapp.photoAlbum.PhotoAlbumActivity;

/**
 * A simple {@link Fragment} subclass.
 */
public class UploadPhotoFragment extends Fragment implements IUploadPhotoView, View.OnClickListener {

    private ImageView photoImgView;
    private Button choosePhotoBtn;
    private Button uploadPhotoBtn;

    private UploadPhotoPresenter presenter;
    private CharSequence userChosenTask;

    public static UploadPhotoFragment newInstance() {

        Bundle args = new Bundle ();

        UploadPhotoFragment fragment = new UploadPhotoFragment ();
        fragment.setArguments ( args );
        return fragment;
    }

    public UploadPhotoFragment() {
        // Required empty public constructor
    }

    private void initView(View view) {
        photoImgView = (ImageView) view.findViewById ( R.id.photo_imgView );
        choosePhotoBtn = (Button) view.findViewById ( R.id.choose_photo_btn );
        uploadPhotoBtn = (Button) view.findViewById ( R.id.upload_photo_btn );
    }

    private void externalPermission() {
        ActivityCompat.requestPermissions ( getActivity (),
                new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                Constant.PERMISSION_REQUEST_READ_EXTERNAL_STORAGE );
    }

    public void showToast(String message) {
        Toast.makeText ( getActivity (), message, Toast.LENGTH_LONG ).show ();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate ( savedInstanceState );
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate ( R.layout.fragment_upload_photo, container, false );

        presenter = new UploadPhotoPresenter ( this, getActivity () );

        initView ( view );

        choosePhotoBtn.setOnClickListener ( this );
        uploadPhotoBtn.setOnClickListener ( this );

        return view;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId ()) {
            case R.id.choose_photo_btn:
                presenter.selectChooser ();
                break;
            case R.id.upload_photo_btn:
                presenter.validInput ();
                break;
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
    public void showImgViewError(String errorMessage) {
        showToast ( errorMessage );
    }

    @Override
    public void uploadFailed(String message) {
        showToast ( message );
    }

    @Override
    public void uploadOk(String message) {
        showToast ( message );
        startActivity ( PhotoAlbumActivity.newIntent ( getActivity () ) );
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

package com.example.amin.maktabprojectworldcupapp.radio.uploadAudio;


import android.Manifest;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.amin.maktabprojectworldcupapp.Constant;
import com.example.amin.maktabprojectworldcupapp.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class UploadAudioFragment extends Fragment implements IUploadAudioView, View.OnClickListener {

    private TextView audioFilePathTxtView;
    private Button fileChooserBtn;
    private Button uploadAudioBtn;

    private UploadAudioPresenter presenter;
    private CharSequence userChosenTask;

    public static UploadAudioFragment newInstance() {
        Bundle args = new Bundle ();

        UploadAudioFragment fragment = new UploadAudioFragment ();
        fragment.setArguments ( args );
        return fragment;
    }

    public UploadAudioFragment() {
        // Required empty public constructor
    }

    private void initView(View view) {
        audioFilePathTxtView = (TextView) view.findViewById ( R.id.audio_file_path_txtView );
        fileChooserBtn = (Button) view.findViewById ( R.id.file_chooser_btn );
        uploadAudioBtn = (Button) view.findViewById ( R.id.upload_audio_btn );
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate ( R.layout.fragment_upload_audio, container, false );

        presenter = new UploadAudioPresenter ( getContext (), this );

        initView ( view );

        fileChooserBtn.setOnClickListener ( this );
        uploadAudioBtn.setOnClickListener ( this );

        return view;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId ()) {
            case R.id.file_chooser_btn:
                presenter.selectChooser ();
                break;
            case R.id.upload_audio_btn:
                presenter.validInput ();
                break;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult ( requestCode, resultCode, data );
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == Constant.REQUEST_GALLERY)
                presenter.onSelectedFromGalleryResult ( data );
        }
    }

    @Override
    public void showChooserDialog() {
        final CharSequence[] items = {getString ( R.string.gallery ), getString ( R.string.back )};
        final AlertDialog.Builder builder = new AlertDialog.Builder ( getActivity () );
        builder.setTitle ( R.string.choose_file )
                .setItems ( items, new DialogInterface.OnClickListener () {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        userChosenTask = presenter.selectAudio ( items[which], dialog );
                    }
                } )
                .show ();
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
    public void hideChooserDialog(DialogInterface dialog) {
        dialog.dismiss ();
    }

    @Override
    public void galleryIntent() {
        Intent intent = new Intent ( Intent.ACTION_GET_CONTENT );
        intent.setType ( "audio/*" );
        startActivityForResult ( Intent.createChooser ( intent, getString ( R.string.choose_file ) ), Constant.REQUEST_GALLERY );
    }
}

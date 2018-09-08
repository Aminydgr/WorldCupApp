package com.example.amin.maktabprojectworldcupapp.chatRoom.addChatRoom;


import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputEditText;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.amin.maktabprojectworldcupapp.Constant;
import com.example.amin.maktabprojectworldcupapp.R;

import java.util.UUID;

/**
 * A simple {@link Fragment} subclass.
 */
public class AddChatRoomDialogFragment extends DialogFragment implements IAddChatRoomView, View.OnClickListener{

    private TextInputEditText chatRoomTitleTxtInEdTxt;
    private Button addChatRoomBtn;
    private Button backBtn;

    private AddChatRoomPresenter presenter;

    public static AddChatRoomDialogFragment newInstance() {

        Bundle args = new Bundle ();

        AddChatRoomDialogFragment fragment = new AddChatRoomDialogFragment ();
        fragment.setArguments ( args );
        return fragment;
    }

    public AddChatRoomDialogFragment() {
        // Required empty public constructor
    }

    private void initView(View view) {
        chatRoomTitleTxtInEdTxt = (TextInputEditText) view.findViewById ( R.id.chat_room_title_txtInputEdTxt);
        addChatRoomBtn = (Button) view.findViewById ( R.id.add_chat_room_btn );
        backBtn = (Button) view.findViewById ( R.id.back_to_chat_list_btn );
    }

    private void showToast(String message) {
        Toast.makeText ( getActivity (), message, Toast.LENGTH_LONG ).show ();
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        View view = LayoutInflater.from ( getActivity () ).inflate ( R.layout.fragment_add_chat_room_dialog, null );

        presenter = new AddChatRoomPresenter ( this, getActivity () );

        initView ( view );

        addChatRoomBtn.setOnClickListener ( this );
        backBtn.setOnClickListener ( this );

        return new AlertDialog.Builder ( getActivity () )
                .setView ( view )
                .setTitle ( R.string.add_chat_room )
                .create ();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId ()) {
            case R.id.add_chat_room_btn:
                presenter.validInput( chatRoomTitleTxtInEdTxt.getText ().toString ());
                break;
            case R.id.back_to_chat_list_btn:
                dismiss ();
                break;
        }
    }

    @Override
    public void showChatRoomTitleError(String errorMessage) {
        chatRoomTitleTxtInEdTxt.setError ( errorMessage );
    }

    @Override
    public void uploadOk(String message, String chatRoomTitle, UUID chatRoomUUID) {
        if (getTargetFragment () == null)
            return;
        Intent intent = new Intent ();
        intent.putExtra ( Constant.EXTRA_CHAT_ROOM_UUID, chatRoomUUID );
        intent.putExtra ( Constant.EXTRA_CHAT_ROOM_TITLE, chatRoomTitle );
        getTargetFragment ().onActivityResult ( getTargetRequestCode (), Activity.RESULT_OK, intent );

        showToast ( message );
        dismiss ();
    }

    @Override
    public void uploadFailed(String message) {
        showToast ( message );
    }
}

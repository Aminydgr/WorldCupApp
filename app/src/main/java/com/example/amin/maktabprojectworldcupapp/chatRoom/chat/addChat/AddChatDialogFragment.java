package com.example.amin.maktabprojectworldcupapp.chatRoom.chat.addChat;


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
import com.example.amin.maktabprojectworldcupapp.SharedPrefManager;

import java.util.UUID;

/**
 * A simple {@link Fragment} subclass.
 */
public class AddChatDialogFragment extends DialogFragment implements View.OnClickListener, IAddChatView {

    private TextInputEditText chatTextTxtInEdTxt;
    private Button addChatBtn;
    private Button backBtn;

    private AddChatPresenter presenter;
    private UUID chatRoomUuid;

    public static AddChatDialogFragment newInstance(UUID chatRoomUuid) {

        Bundle args = new Bundle ();
        args.putSerializable ( Constant.ARGS_CHAT_ROOM_UUID, chatRoomUuid );

        AddChatDialogFragment fragment = new AddChatDialogFragment ();
        fragment.setArguments ( args );
        return fragment;
    }

    public AddChatDialogFragment() {
        // Required empty public constructor
    }

    private void initView(View view) {
        chatTextTxtInEdTxt = (TextInputEditText) view.findViewById ( R.id.chat_text_txtInputEdTxt );
        addChatBtn = (Button) view.findViewById ( R.id.add_chat_btn );
        backBtn = (Button) view.findViewById ( R.id.back_to_chat_btn );
    }

    private void showToast(String message) {
        Toast.makeText ( getActivity (), message, Toast.LENGTH_LONG ).show ();
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        View view = LayoutInflater.from ( getActivity () ).inflate ( R.layout.fragment_add_chat_dialog, null );

        chatRoomUuid = (UUID) getArguments ().getSerializable ( Constant.ARGS_CHAT_ROOM_UUID );

        presenter = new AddChatPresenter ( getContext (), this );

        initView ( view );

        addChatBtn.setOnClickListener ( this );
        backBtn.setOnClickListener ( this );

        return new AlertDialog.Builder ( getActivity () )
                .setTitle ( "فرستادن پیام" )
                .setView ( view )
                .create ();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId ()) {
            case R.id.add_chat_btn:
                presenter.validInput ( chatTextTxtInEdTxt.getText ().toString (),
                        SharedPrefManager.getInstance ( getActivity () ).getUser (),
                        chatRoomUuid );
                break;
            case R.id.back_to_chat_btn:
                dismiss ();
                break;
        }
    }

    @Override
    public void uploadFailed(String message) {
        showToast ( message );
    }

    @Override
    public void uploadOk(String message, UUID chatUUID, String userName, String chatText) {
        if (getTargetFragment () == null)
            return;
        Intent intent = new Intent ();
        intent.putExtra ( Constant.EXTRA_CHAT_UUID, chatUUID );
        intent.putExtra ( Constant.EXTRA_CHAT_USER_NAME, userName );
        intent.putExtra ( Constant.EXTRA_CHAT_TEXT, chatText );
        getTargetFragment ().onActivityResult ( getTargetRequestCode (), Activity.RESULT_OK, intent );

        showToast ( message );
        dismiss ();
    }

    @Override
    public void showChatTextError(String errorMessage) {
        chatTextTxtInEdTxt.setError ( errorMessage );
    }
}

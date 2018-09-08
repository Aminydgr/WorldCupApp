package com.example.amin.maktabprojectworldcupapp.chatRoom.chat;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.amin.maktabprojectworldcupapp.Constant;
import com.example.amin.maktabprojectworldcupapp.R;
import com.example.amin.maktabprojectworldcupapp.chatRoom.chat.addChat.AddChatDialogFragment;
import com.example.amin.maktabprojectworldcupapp.model.Chat;

import java.util.List;
import java.util.UUID;

/**
 * A simple {@link Fragment} subclass.
 */
public class ChatFragment extends Fragment implements IChatView, View.OnClickListener {

    private FloatingActionButton addChatFAB;
    private RecyclerView chatRecyclerView;

    private ChatPresenter presenter;
    private UUID chatRoomUuid;
    private List<Chat> chatList;
    private ChatAdapter adapter;

    public static ChatFragment newInstance(String chatRoomTitle, UUID chatRoomUuid) {

        Bundle args = new Bundle ();
        args.putString ( Constant.ARGS_CHAT_ROOM_TITLE, chatRoomTitle );
        args.putSerializable ( Constant.ARGS_CHAT_ROOM_UUID, chatRoomUuid );

        ChatFragment fragment = new ChatFragment ();
        fragment.setArguments ( args );
        return fragment;
    }

    public ChatFragment() {
        // Required empty public constructor
    }

    private void initView(View view) {
        addChatFAB = (FloatingActionButton) view.findViewById ( R.id.add_chat_FAB );
        chatRecyclerView = (RecyclerView) view.findViewById ( R.id.chat_recyclerView );
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate ( R.layout.fragment_chat, container, false );

        String chatRoomTitle = getArguments ().getString ( Constant.ARGS_CHAT_ROOM_TITLE );
        chatRoomUuid = (UUID) getArguments ().getSerializable ( Constant.ARGS_CHAT_ROOM_UUID );

        presenter = new ChatPresenter ( getActivity (), this );

        initView ( view );

        addChatFAB.setOnClickListener ( this );
        chatRecyclerView.setLayoutManager ( new LinearLayoutManager ( getActivity () ) );

        presenter.loadChat ( chatRoomUuid );

        return view;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId ()) {
            case R.id.add_chat_FAB:
                presenter.addChat ();
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult ( requestCode, resultCode, data );
        if (resultCode != Activity.RESULT_OK)
            return;
        if (requestCode == Constant.REQUEST_ADD_CHAT_DIALOG) {
            UUID chatUUID = (UUID) data.getSerializableExtra ( Constant.EXTRA_CHAT_UUID );
            String chatText = data.getStringExtra ( Constant.EXTRA_CHAT_TEXT );
            String userName = data.getStringExtra ( Constant.EXTRA_CHAT_USER_NAME );
            Chat chat = new Chat ( chatUUID, userName, chatText );
            chatList.add ( chat );
            adapter.setChatList ( chatList );
            adapter.notifyDataSetChanged ();
        }
    }

    @Override
    public void showDialog() {
        AddChatDialogFragment dialogFragment = AddChatDialogFragment.newInstance ( chatRoomUuid );
        dialogFragment.setTargetFragment ( ChatFragment.this, Constant.REQUEST_ADD_CHAT_DIALOG );
        dialogFragment.show ( getFragmentManager (), Constant.ADD_CHAT_DIALOG_TAG );
    }

    @Override
    public void setUpAdapter(List<Chat> chatList) {
        this.chatList = chatList;
        this.adapter = new ChatAdapter ( getContext (), chatList );
        chatRecyclerView.setAdapter ( adapter );
    }
}

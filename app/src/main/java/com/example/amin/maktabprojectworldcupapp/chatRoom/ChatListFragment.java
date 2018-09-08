package com.example.amin.maktabprojectworldcupapp.chatRoom;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.amin.maktabprojectworldcupapp.Constant;
import com.example.amin.maktabprojectworldcupapp.R;
import com.example.amin.maktabprojectworldcupapp.chatRoom.addChatRoom.AddChatRoomDialogFragment;
import com.example.amin.maktabprojectworldcupapp.model.ChatRoom;

import java.util.List;
import java.util.UUID;

/**
 * A simple {@link Fragment} subclass.
 */
public class ChatListFragment extends Fragment implements IChatListView {

    private ProgressBar progressBar;
    private RecyclerView recyclerView;

    private ChatListPresenter presenter;
    private List<ChatRoom> chatRoomList;
    private ChatListAdapter adapter;

    public static ChatListFragment newInstance() {

        Bundle args = new Bundle ();

        ChatListFragment fragment = new ChatListFragment ();
        fragment.setArguments ( args );
        return fragment;
    }

    public ChatListFragment() {
        // Required empty public constructor
    }

    private void initView(View view) {
        progressBar = (ProgressBar) view.findViewById ( R.id.chat_room_progressBar );
        recyclerView = (RecyclerView) view.findViewById ( R.id.chat_room_recyclerView );
    }

    private void showToast(String message) {
        Toast.makeText ( getActivity (), message, Toast.LENGTH_LONG ).show ();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate ( savedInstanceState );
        setHasOptionsMenu ( true );

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate ( R.layout.fragment_chat_room, container, false );

        presenter = new ChatListPresenter ( this, getActivity () );

        initView ( view );

        recyclerView.setLayoutManager ( new GridLayoutManager ( getActivity (), 3 ) );

        presenter.loadChatRoom ();

        return view;
    }


    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu ( menu, inflater );
        inflater.inflate ( R.menu.chat_list_menu, menu );
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId ()) {
            case R.id.add_chat_room_menu_item:
                presenter.allowedUser ();
                return true;
            default:
                return super.onOptionsItemSelected ( item );
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult ( requestCode, resultCode, data );
        if (resultCode != Activity.RESULT_OK)
            return;
        if (requestCode == Constant.REQUEST_ADD_CHAT_ROOM_DIALOG) {
            UUID chatRoomUUID = (UUID) data.getSerializableExtra ( Constant.EXTRA_CHAT_ROOM_UUID );
            String chatRoomTitle = data.getStringExtra ( Constant.EXTRA_CHAT_ROOM_TITLE );
            ChatRoom chatRoom = new ChatRoom ( chatRoomUUID, chatRoomTitle );
            chatRoomList.add ( chatRoom );
            adapter.setChatRoomList ( chatRoomList );
            adapter.notifyDataSetChanged ();
        }
    }

    @Override
    public void addNotAllowed() {
        showToast ( "این قابلیت برای شما فعال نمی باشد!" );
    }

    @Override
    public void addAllowed() {
        AddChatRoomDialogFragment dialogFragment = AddChatRoomDialogFragment.newInstance ();
        dialogFragment.setTargetFragment ( ChatListFragment.this, Constant.REQUEST_ADD_CHAT_ROOM_DIALOG );
        dialogFragment.show ( getFragmentManager (), Constant.ADD_CHAT_ROOM_DIALOG_TAG );
    }

    @Override
    public void showProgressBar() {
        progressBar.setVisibility ( View.VISIBLE );
    }

    @Override
    public void hideProgressBar() {
        progressBar.setVisibility ( View.INVISIBLE );
    }

    @Override
    public void setUpAdapter(List<ChatRoom> chatRoomList) {
        this.chatRoomList = chatRoomList;
        this.adapter = new ChatListAdapter ( getContext (), chatRoomList );
        recyclerView.setAdapter ( adapter );
    }
}

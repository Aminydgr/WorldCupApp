package com.example.amin.maktabprojectworldcupapp.chatRoom;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.amin.maktabprojectworldcupapp.R;
import com.example.amin.maktabprojectworldcupapp.chatRoom.chat.ChatActivity;
import com.example.amin.maktabprojectworldcupapp.model.ChatRoom;

import java.util.List;

/**
 * Created by Amin on 8/23/2018.
 */

public class ChatListAdapter extends RecyclerView.Adapter<ChatListAdapter.ChatListHolder> {

    private Context context;
    private List<ChatRoom> chatRoomList;

    public void setChatRoomList(List<ChatRoom> chatRoomList) {
        this.chatRoomList = chatRoomList;
    }

    public ChatListAdapter(Context context, List<ChatRoom> chatRoomList) {
        this.context = context;
        this.chatRoomList = chatRoomList;
    }

    @Override
    public ChatListHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from ( context ).inflate ( R.layout.chat_room_recycler_item, null, true );
        return new ChatListHolder ( view );
    }

    @Override
    public void onBindViewHolder(ChatListHolder holder, int position) {
        holder.bindChatRoom ( chatRoomList.get ( position ) );
    }

    @Override
    public int getItemCount() {
        return chatRoomList.size ();
    }

    public class ChatListHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private Button chatRoomTitleBtn;
        private ChatRoom chatRoom;

        public ChatListHolder(View itemView) {
            super ( itemView );
            chatRoomTitleBtn = (Button) itemView.findViewById ( R.id.chat_room_title_btn );
            chatRoomTitleBtn.setOnClickListener ( this );
        }

        public void bindChatRoom(ChatRoom chatRoom) {
            this.chatRoom = chatRoom;
            chatRoomTitleBtn.setText ( chatRoom.getTitle () );
        }

        @Override
        public void onClick(View v) {
            context.startActivity ( ChatActivity.newIntent ( context, chatRoom.getTitle (), chatRoom.getUuid () ) );
        }
    }

}

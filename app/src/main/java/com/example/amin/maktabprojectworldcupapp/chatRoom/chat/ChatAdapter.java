package com.example.amin.maktabprojectworldcupapp.chatRoom.chat;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.amin.maktabprojectworldcupapp.R;
import com.example.amin.maktabprojectworldcupapp.model.Chat;

import java.util.List;

/**
 * Created by Amin on 8/30/2018.
 */

public class ChatAdapter extends RecyclerView.Adapter<ChatAdapter.ChatHolder> {

    private Context context;
    private List<Chat> chatList;

    public ChatAdapter(Context context, List<Chat> chatList) {
        this.context = context;
        this.chatList = chatList;
    }

    public void setChatList(List<Chat> chatList) {
        this.chatList = chatList;
    }

    @Override
    public ChatHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from ( context ).inflate ( R.layout.chat_recycler_item, null, true );
        return new ChatHolder ( view );
    }

    @Override
    public void onBindViewHolder(ChatHolder holder, int position) {
        holder.bindChat ( chatList.get ( position ) );
    }

    @Override
    public int getItemCount() {
        return chatList.size ();
    }

    public class ChatHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private TextView chatUserNameTxtView;
        private TextView chatTextTxtView;
        private Chat chat;

        public ChatHolder(View itemView) {
            super ( itemView );

            chatUserNameTxtView = (TextView) itemView.findViewById ( R.id.chat_user_name_txtView );
            chatTextTxtView = (TextView) itemView.findViewById ( R.id.chat_text_txtView );

            itemView.setOnClickListener ( this );
        }

        public void bindChat(Chat chat) {
            this.chat = chat;
            chatUserNameTxtView.setText ( chat.getUserName () );
            chatTextTxtView.setText ( chat.getText () );
        }

        @Override
        public void onClick(View v) {
            Intent shareIntent = new Intent ( Intent.ACTION_SEND );
            shareIntent.setType ( "text/plain" );
            shareIntent.putExtra ( Intent.EXTRA_TEXT, chat.getUserName () + ":\n" + chat.getText () );
            context.startActivity ( Intent.createChooser ( shareIntent, "اشتراک گذاری ..." ) );
        }
    }
}

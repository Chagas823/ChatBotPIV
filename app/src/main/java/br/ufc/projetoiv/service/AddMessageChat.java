package br.ufc.projetoiv.service;

import android.app.Activity;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import br.ufc.projetoiv.adapter.MessageAdapter;
import br.ufc.projetoiv.model.Message;

public class AddMessageChat {
    Activity activity;
    List<Message> messageList;
    MessageAdapter messageAdapter;
    RecyclerView recyclerView;
    public AddMessageChat(Activity activity, List<Message> messageList, MessageAdapter messageAdapter, RecyclerView recyclerView){
        this.activity = activity;
        this.messageList = messageList;
        this.messageAdapter = messageAdapter;
        this.recyclerView = recyclerView;
    }

    public void  addToChat(String message, String sentBy) {
        activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                messageList.add(new Message(message, sentBy));
                messageAdapter.notifyDataSetChanged();
                recyclerView.smoothScrollToPosition(messageAdapter.getItemCount());
            }
        });

    }
}

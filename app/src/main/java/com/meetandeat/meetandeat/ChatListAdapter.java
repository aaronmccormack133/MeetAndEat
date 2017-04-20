package com.meetandeat.meetandeat;

import android.app.Activity;
import android.app.DownloadManager;
import android.graphics.Color;
import android.view.View;
import android.widget.TextView;


import com.firebase.ui.database.FirebaseListAdapter;

/**
 * Created by Admin on 20/04/2017.
 */

public class ChatListAdapter extends FirebaseListAdapter{

    private String mUsername;

    public ChatListAdapter(DownloadManager.Query ref, Activity activity, int layout, String mUsername) {
        super(ref, layout, Chat.class, activity);
        this.mUsername = mUsername;
    }

protected void populateView(View view, Chat chat) {
        // Map a Chat object to an entry in our listview
        String author = chat.getAuthor();
        TextView authorText = (TextView) view.findViewById(R.id.author);
        authorText.setText(author + ": ");
        // If the message was sent by this user, color it differently
        if (author != null && author.equals(mUsername)) {
            authorText.setTextColor(Color.RED);
        } else {
            authorText.setTextColor(Color.BLUE);
        }
        ((TextView) view.findViewById(R.id.message)).setText(chat.getMessage());
    }

    @Override
    protected void populateView(View v, Object model, int position) {

    }
}

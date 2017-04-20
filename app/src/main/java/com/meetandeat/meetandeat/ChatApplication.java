package com.meetandeat.meetandeat;

import com.firebase.client.Firebase;

/**
 * Created by Admin on 20/04/2017.
 */

public class ChatApplication extends android.app.Application {
    @Override
    public void onCreate() {
        super.onCreate();
        Firebase.setAndroidContext(this);
    }
}

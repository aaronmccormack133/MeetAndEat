package com.meetandeat.meetandeat;

import com.facebook.FacebookSdk;
import com.facebook.appevents.AppEventsLogger;
import com.firebase.client.Firebase;

/**
 * Created by Aaron on 15/04/2017.
 */

public class customApplication extends android.app.Application{

    @Override
    public void onCreate(){
        super.onCreate();
        Firebase.setAndroidContext(this);

        FacebookSdk.sdkInitialize(getApplicationContext());
        AppEventsLogger.activateApp(this);
    }
}

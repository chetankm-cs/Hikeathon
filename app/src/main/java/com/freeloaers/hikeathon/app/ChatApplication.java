package com.freeloaers.hikeathon.app;

import android.app.Application;

/**
 * Created by USER on 029-29-03.
 */
public class ChatApplication extends Application{
    @Override
    public void onCreate() {
        AppPreferences.initPreferences(this);
        super.onCreate();
    }
}

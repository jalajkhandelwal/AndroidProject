package com.example.topnews;

import android.app.Application;

public class TopNewsApp extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        DatabaseHelper.initDataBase(this);
    }
}

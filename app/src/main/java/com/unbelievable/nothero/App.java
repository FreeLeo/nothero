package com.unbelievable.nothero;

import android.app.Application;

public class App extends Application{
    public static App mInstance;

    public static App getInstance(){
        return mInstance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;
    }
}
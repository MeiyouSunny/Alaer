package com.cyberalaer.hybrid.ui;

import android.app.Application;

import com.alaer.lib.api.HttpManager;

import likly.dollar.$;

public class App extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        $.initialize(this);
        HttpManager.initHttp(getApplicationContext());
    }
}

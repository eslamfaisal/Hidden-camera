package com.detectspycamera.detectspycamera;

import android.app.Application;

import com.google.android.gms.ads.MobileAds;

public class App extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        MobileAds.initialize(getApplicationContext(), getResources().getString(R.string.app_id));

    }
}

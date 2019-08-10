package com.hackathon.tyroler;

import android.app.Application;
import android.util.Log;

public class TyrolerApplication extends Application {

    private static final String TAG = "TyrolerApplication";

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d(TAG, "App started up");
    }
}

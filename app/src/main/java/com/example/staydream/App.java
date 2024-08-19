package com.example.staydream;

import android.app.Application;
import android.content.Context;
import android.view.MotionEvent;
import android.view.inputmethod.InputMethodManager;

import com.example.staydream.Data.DataManager;
import com.example.staydream.Utilities.ImageLoader;
import com.example.staydream.Utilities.LocationManager;
import com.example.staydream.Utilities.SignalManager;

public class App extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        SignalManager.init(this);
        LocationManager.init(this,null);
        ImageLoader.initImageLoader(this);
        DataManager.init(this);


    }

}

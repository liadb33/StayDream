package com.example.staydream;

import android.app.Application;

import com.example.staydream.Data.HotelDataManager;
import com.example.staydream.Utilities.ImageLoader;
import com.example.staydream.Utilities.LocationManager;

public class App extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        LocationManager.init(this,null);
        ImageLoader.initImageLoader(this);
        HotelDataManager.init(this);
    }
}

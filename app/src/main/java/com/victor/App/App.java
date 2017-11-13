package com.victor.App;

import android.app.Application;

import com.victor.tv.library.util.VolleyRequest;

/**
 * Created by victor on 2017/9/26.
 */

public class App extends Application{
    private static App instance;
    public App() {
        instance = this;
    }

    public static App get() {
        return instance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        //初始化
        VolleyRequest.buildRequestQueue(this);
    }
}

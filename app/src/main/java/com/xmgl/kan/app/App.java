package com.xmgl.kan.app;

import android.app.Application;

import com.xmgl.kan.utils.CacheUtils;

/**
 */
public class App extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        CacheUtils.getInstance().init(this);
    }
}

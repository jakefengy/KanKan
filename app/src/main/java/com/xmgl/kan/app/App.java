package com.xmgl.kan.app;

import android.app.Application;

import com.xmgl.kan.db.helper.AppDbHelper;
import com.xmgl.kan.db.helper.UserHelper;

/**
 */
public class App extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        AppDbHelper.getInstance().init(this);
        new UserHelper().addDefaultUser();
    }
}

package com.uconnect.events.app;

import android.app.Application;

import com.uconnect.events.network.services.UCUserServices;

/**
 * Created by jaggu on 4/19/2015.
 */
public class UCApplication extends Application {

    private UCUserServices mUserServices;
    @Override
    public void onCreate() {
        super.onCreate();
    }

    /**
     * Returns the UserServices object
     * */
    public UCUserServices getUCUserServices() {
        if (mUserServices == null) {
            mUserServices = new UCUserServices(this);
        }
        return mUserServices;
    }
}

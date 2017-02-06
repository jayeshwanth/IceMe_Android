package com.uconnect.events.app;

import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v4.app.FragmentActivity;
import android.view.Window;

/**
 * Created by jaggu on 4/19/2015.
 */
public class UCSuperFragmentActivity extends FragmentActivity {

    public UCApplication app;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        app = (UCApplication) getApplication();
    }

    /**
     * Common method to launch the new activity which is being used from all the activity classes.
     * @param activityClass
     * @param bundle
     */
    protected void launchActivity(Class<?> activityClass, Bundle bundle) {
        Intent intent = new Intent(app, activityClass);
        if(bundle != null) {
            intent.putExtras(bundle);
        }

        startActivity(intent);
    }
}

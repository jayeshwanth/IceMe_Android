package com.uconnect.events.app.usersetup;

import android.os.Bundle;
import android.os.Handler;
import android.util.Log;

import com.uconnect.events.R;
import com.uconnect.events.app.UCAppConstants;
import com.uconnect.events.app.UCSuperFragmentActivity;
import com.uconnect.events.utils.UCAppUtils;

/**
 * Created by jaggu on 4/18/2015.
 */
public class UCSplashScreenActivity extends UCSuperFragmentActivity implements UCAppConstants {

    private final Runnable runnable = new Runnable() {
        @Override
        public void run() {
            setupUser();
        }
    };

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ucsplash);
        Handler splashTimeHandler = new Handler();
        splashTimeHandler.postDelayed(runnable , SPLASH_SCREEN_DELAY);
        //setupUser();
    }

    /**
     * Checking the user existance in local shared preferences. if user not exits
     * start setting up the user else showing the user home
     * screen (which contains contacts and events).
     */
    private void setupUser() {

        //TODO need to delete the below line of code.
        //UCAppPreferences.getInstance(app).savePreference(UCSharedPreferences.IS_USER_EXISTS, false);
        //boolean isUserExists = UCAppPreferences.getInstance(app).getPreference(UCSharedPreferences.IS_USER_EXISTS, false);
        boolean isUserExists = UCAppUtils.getInstance().isDBExists(app);
        Log.v("UC_TAG", "U connect isUserExists : " + isUserExists);
        if(!isUserExists) { /*User does not exists*/
           startSetupUser();
        } else {
            //1. Start service to reloading the user data.
            //2. Navigating to the user home screen.
        }
    }

    /**
     * Start setting up the user by reading email or phone no. and save the user
     * existence status true in local shared preferences and creating the local
     * database for user and crete the user on the server.
     */
    private void startSetupUser() {
        String userId = UCAppUtils.getInstance().getUCUserIdFromDevice(app);
        Log.v("UC_TAG", "U connect userId : " + userId);
        if(userId != null) {
            //Navigating to the create user screen.
            Bundle bundle = new Bundle();
            bundle.putString(BundleConstants.UC_USER_PRIMARY_EMAIL, userId);
            bundle.putString(BundleConstants.UC_USER_PHONE_NO, UCAppUtils.getInstance().getUCUserPhoneNoFromDevice(app));
            Log.v("UC_TAG", "U connect userId : " + userId);
            Log.v("UC_TAG", "U connect phoneNo : " + UCAppUtils.getInstance().getUCUserPhoneNoFromDevice(app));
            launchActivity(UCCustomUserCreate.class, bundle);
        } else {
            //Navigating to the custom user creation Activity.
        }
    }
}

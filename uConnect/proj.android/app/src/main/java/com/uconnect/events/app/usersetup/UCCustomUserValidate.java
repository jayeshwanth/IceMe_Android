/**
 * Created by jaggu on 4/18/2015.
 */
package com.uconnect.events.app.usersetup;

import android.app.Activity;
import android.os.Bundle;
import android.os.PersistableBundle;

/**
 * UCCustomUserValidate is to validate the user. here we should ask the user to
 * enter the verification code which he gets.
 */
public class UCCustomUserValidate extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState, PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
    }

    /**
     * Validating the verification code received by the user. if valid making the
     * user existance in local using shared preferences and create the user on the
     * server
     */
    private void validateUCCustomUserVerification() {
        if(true/*verification is valid*/) {
            //crete the user in local and on the server.
        } else {
            //Display warning message.
        }
    }
}

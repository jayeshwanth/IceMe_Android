package com.example.arpit2408.fb_linked;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.*;
import com.facebook.*;
import com.facebook.login.widget.*;
import com.facebook.login.*;
import android.app.Activity;


public class MainActivity extends Activity {



        @Override
        public View onCreateView(
                LayoutInflater inflater,
                ViewGroup container,
                Bundle savedInstanceState) {
            View view = inflater.inflate(R.layout.activity_main, container, false);

            LoginButton loginButton = (LoginButton) view.findViewById(R.id.login_button);
            loginButton.setReadPermissions("user_friends");
            // If using in a fragment
            // loginButton.setFragment(this);
            // Other app specific specialization

            // Callback registration
            loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
                @Override
                public void onSuccess(LoginResult loginResult) {
                    // App code
                }

                @Override
                public void onCancel() {
                    // App code
                }

                @Override
                public void onError(FacebookException exception) {
                    // App code
                }
            });
        return view;
        }


    CallbackManager callbackManager;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(getApplicationContext());
        callbackManager = CallbackManager.Factory.create();

        LoginButton loginButton = (LoginButton)view.findViewById(R.id.login_button);
        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() { ... });
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, android.widget.Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }
        @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
    @Override
    protected void onResume() {
        super.onResume();

        // Logs 'install' and 'app activate' App Events.
        com.facebook.appevents.AppEventsLogger.activateApp(this);
    }
    @Override
    protected void onPause() {
        super.onPause();

        // Logs 'app deactivate' App Event.
        com.facebook.appevents.AppEventsLogger.deactivateApp(this);
    }
}

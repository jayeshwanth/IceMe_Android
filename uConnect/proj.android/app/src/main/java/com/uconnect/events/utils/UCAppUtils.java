/**
 * Created by jaggu on 4/18/2015.
 */
package com.uconnect.events.utils;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.telephony.TelephonyManager;
import android.util.Patterns;

import com.uconnect.events.app.UCAppConstants;

import java.io.File;
import java.util.regex.Pattern;

/**
 * UCAppUtils is a utility class which provides several utility methods.
 */
public class UCAppUtils {

    private static UCAppUtils mInstance;
    private String[] countriesList = {"Choose a country", "India", "USA"};
    private String[] countryCodes = {"", "91", "1"};

    public static UCAppUtils getInstance() {
        if(mInstance == null) {
            mInstance = new UCAppUtils();
        }

        return mInstance;
    }
    /**
     * read the email if exists else read the phone no and return it as UCUserId.
     * If both does not exists return null.
     * @return UCUserId.
     */
    public String getUCUserIdFromDevice(Context context) {
        String ucUserId = null;
        Pattern emailPattern = Patterns.EMAIL_ADDRESS;
        Account[] accounts = AccountManager.get(context).getAccounts();
        for (Account account : accounts) {
            if (emailPattern.matcher(account.name).matches()) {
                ucUserId = account.name == null || account.name.equals("") ? "" : account.name;
                break;
            }
        }

        return ucUserId;
    }

    public String getUCUserPhoneNoFromDevice(Context context) {
        TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        String phoneNo = telephonyManager.getLine1Number();
        phoneNo = phoneNo == null ? "" : phoneNo;

        return phoneNo;
    }

    public boolean isDBExists(Context context) {
        File uConnectDB = context.getDatabasePath(UCAppConstants.UCSQLiteDB.DATABASE_NAME);

        return uConnectDB.exists();
    }

    public String[] getCountries() {

        return countriesList;
    }

    public String[] getCountryCodes() {

        return countryCodes;
    }

    public String getCountryCode(int index) {

        return countryCodes[index];
    }

    public String getCountryFromCode(String countryCode) {
        int position = 0;

        for(int i = 0; i < countryCodes.length; i++) {
            if(countryCode.equals(countryCodes[i])) {
                position = i;
                break;
            }
        }

        return countriesList[position];
    }

    /**
     * Checking the network availability (both mobile data and wifi)
     * @param mContext
     * @return
     */
    public boolean isNetworkAvailable(Context mContext) {
        if (mContext == null) {
            return false;
        }

        ConnectivityManager connectivity = (ConnectivityManager) mContext.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivity != null) {
            NetworkInfo[] networkInfo = connectivity.getAllNetworkInfo();
            if (networkInfo != null) {
                for (int i = 0; i < networkInfo.length; i++) {
                    if (networkInfo[i].getState() == NetworkInfo.State.CONNECTED) {
                        return true;
                    }
                }
            }
        }
        return false;
    }
}

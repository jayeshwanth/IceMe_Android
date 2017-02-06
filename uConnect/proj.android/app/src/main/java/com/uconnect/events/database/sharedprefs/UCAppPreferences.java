//***************************************************************************************************
//***************************************************************************************************
//      Project name                    		: UConnect
//      Class Name                              : UCAppPreferences
//      Author                                  :
//***************************************************************************************************
//      Class Description: Custom class with several methods to work with SharedPreferences.
//***************************************************************************************************
//***************************************************************************************************

package com.uconnect.events.database.sharedprefs;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.Set;

public class UCAppPreferences {
    private static UCAppPreferences mInstance;
    private final SharedPreferences mPreferencesObject;

    private UCAppPreferences(Context context) {
        super();
        String mPreferenceName = "UCConnectPreferences";
        mPreferencesObject = context.getSharedPreferences(mPreferenceName, Context.MODE_PRIVATE);
    }

    public static UCAppPreferences getInstance(Context context) {
        if (mInstance == null) {
            mInstance = new UCAppPreferences(context);
            return mInstance;
        }
        return mInstance;
    }

    /**
     * To save the string value into the shared preferences.
     * @param key
     * @param value
     */
    public void savePreference(String key, String value) {
        mPreferencesObject.edit().putString(key, value).commit();
    }

    /**
     * To get the string value from the shared preferences.
     * @param key
     * @param defaultValue
     * @return String
     */
    public String getPreference(String key, String defaultValue) {
        String toReturn = mPreferencesObject.getString(key, defaultValue);
        if (toReturn == null) {
            toReturn = "";
        }
        return toReturn;
    }

    /**
     * To save the boolean value into the shared preferences.
     * @param key
     * @param value
     */
    public void savePreference(String key, boolean value) {
        mPreferencesObject.edit().putBoolean(key, value).commit();
    }

    /**
     * To get the boolean value from the shared preferences.
     * @param key
     * @param defaultValue
     * @return boolean
     */
    public boolean getPreference(String key, boolean defaultValue) {
        return mPreferencesObject.getBoolean(key, defaultValue);
    }

    /**
     * To save the int value into the shared preferences.
     * @param key
     * @param value
     */
    public void savePreference(String key, int value) {
        mPreferencesObject.edit().putInt(key, value).commit();
    }

    /**
     * To get the int value from the shared preferences.
     * @param key
     * @param defaultValue
     * @return int
     */
    public int getPreference(String key, int defaultValue) {
        return mPreferencesObject.getInt(key, defaultValue);
    }

    /**
     * To save the Set of strings into the shared preferences.
     * @param key
     * @param stringSet
     */
    public void savePreference(String key, Set<String> stringSet) {
        mPreferencesObject.edit().putStringSet(key, stringSet).commit();
    }

    /**
     * To get the set of strings from the shared preferences.
     * @param key
     * @return set
     */
    public Set<String> getPreference(String key) {
        return mPreferencesObject.getStringSet(key, null);
    }

    /**
     * To check whether the value with the given key exists or not.
     * @param key
     * @return boolean
     */
    public boolean contains(String key) {
        return mPreferencesObject.contains(key);
    }

    /**
     * To remove the value with the given key from the shared preferences.
     * @param key
     * @return boolean
     */
    public boolean remove(String key) {
        return mPreferencesObject.edit().remove(key).commit();
    }

}
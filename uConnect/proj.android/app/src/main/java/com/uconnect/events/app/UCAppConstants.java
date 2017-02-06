package com.uconnect.events.app;

/**
 * Created by jaggu on 4/19/2015.
 */
public interface UCAppConstants {

    int SPLASH_SCREEN_DELAY = 2000;

    String SELECTED_COUNTRY = "SelectedCountry";
    String SELECTED_COUNTRY_ID = "SelectedCountry-ID";

    interface RequestCode {
        int START_COUNTRY_LIST = 100;
    }

    interface ResultCode {
        int FINISH_COUNTRY_LIST = 1000;
    }

    interface BundleConstants {

        String UC_USER_PRIMARY_EMAIL = "Uc-User-Primary-Email";
        String UC_USER_PHONE_NO = "Uc-User-Phone-No";
    }

    interface UCSharedPreferences {
        String IS_USER_EXISTS = "UC-User-Exists";
    }

    interface UCSQLiteDB {
        int DATABASE_VERSION = 1;
        String DATABASE_NAME = "uConnect.db";
    }
}

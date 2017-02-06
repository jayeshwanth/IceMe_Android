package com.uconnect.events.network;

/**
 * Created by jagadeesh on 3/5/15.
 */
public interface UCCloudConstants {
    //Timeout constants.
    int CONNECTION_TIMEOUT = 30000; // 30 seconds
    int SO_TIMEOUT = 30000;

    /**
     * Network status constants.
     * */
    public interface NetworkStatusConstants {
        byte HTTP_NO_NETWORK_CONNECTION = 0;
        byte HTTP_SERVICE_ERROR = 1;
        byte HTTP_CONNECTION_TIME_OUT = 2;
        byte HTTP_FAILURE = 3;
        byte HTTP_SUCCESS = 4;
        byte HTTP_LOGIN_ERROR = 5;
    }

    /**
     * Request and response content types.
     * */
    public interface ContentType {
        String CONTENT_TYPE_HTML = "text/html";
        String CONTENT_TYPE_JSON = "application/json";
    }

    /**
     * Request header constants.
     * */
    public interface Header {
        String HEADER_OATH_TOKEN = "Auth-Token";
    }

    /**
     * Api constants.
     * */
    public interface Api {
        char URL_PARAMETER_SEPERATOR = '?';
        char PARAMETER_DELIMITER = '&';
        char PARAMETER_EQUALS_CHAR = '=';

        String BASE_URL = "http://54.68.191.122/common/";
        //String BASE_URL = "http://192.168.2.145:8080/alesco/"; //Local
        //String BASE_URL = "http://202.65.136.24:8080/alesco/"; //Server
        String API_VALIDATE_PHONE = BASE_URL + "validatephone.php";
        String API_LOGIN = BASE_URL + "authentication/login";
        String API_SIGN_UP = BASE_URL + "authentication/signup";
        String API_FORGOT_PASSWORD = BASE_URL + "authentication/forgotPassword";
        String API_UPDATE_PROFILE = BASE_URL + "user/updateProfile";
        String API_GET_USER_PROFILE = BASE_URL + "user/getProfile";
        String API_RECOMMENDATIONS_LIST = BASE_URL + "recommendation";
        String API_RECOMMENDATIONS_CATEGORIES = BASE_URL + "recommendation/getCategory";
        String API_TRENDING_LIST = BASE_URL + "trending";

        String API_RATEIT_LIST = BASE_URL + "rate-it/sg";
        String API_WISH_LIST = BASE_URL + "wish-list";
        String API_TRENDING__LATEST_LIST = BASE_URL + "trending/latestApplication";
        String API_TRENDING_WEEK_LIST = BASE_URL + "trending/weekApplication";
        String API_TRENDING__HOT_LIST = BASE_URL + "trending/hotApplication";
        String API_CHANGE_PASSWORD = BASE_URL + "user/changePassword";
        String API_INSTALLED_APPS = BASE_URL + "authentication/installed-app";
        String API_USER_ACTION_FOR_RECOMMENDATIONS = BASE_URL + "user/recommended-app";
        String API_GET_COUNTRIES = BASE_URL + "authentication/getCountries";
        String API_POST_USER_ACTION = BASE_URL + "user/action-ldw";
    }

}

//***************************************************************************************************
//***************************************************************************************************
//      Project name                    		: Alesco Happy
//      Class Name                              : UserServices
//      Author                                  : PurpleTalk, Inc.
//***************************************************************************************************
//      Class Description: Defines API calls relating to User data.
//***************************************************************************************************
//***************************************************************************************************

package com.uconnect.events.network.services;

import android.content.Context;
import android.util.Log;

import com.uconnect.events.app.UCAppConstants;
import com.uconnect.events.network.UCCloudConstants;
import com.uconnect.events.network.UCCloudHelper;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


/**
 * Defines API calls relating to ---.
 */
public class UCUserServices extends BaseService implements UCAppConstants, UCCloudConstants {

    private Context mContext;

    public UCUserServices(Context context) {
        super(context);
        mContext = context;
    }

    /**
     * Calling the validatePhone API.
     * @param mobile
     * @param uniqueId
     */
    public void validatePhone(String mobile, String uniqueId, final DataUpdateCallback callback) {
        List<NameValuePair> nameValuePairList = new ArrayList<>();
        nameValuePairList.add(new BasicNameValuePair("mobile", "+" + mobile));
        nameValuePairList.add(new BasicNameValuePair("unique_id", uniqueId));

        mCloudHelper.getData(mContext, Api.API_VALIDATE_PHONE, nameValuePairList, false, new UCCloudHelper.JSONCallback(){
            @Override
            public void getJsonData(byte status, JSONObject data, String statusMsg) {
                if(data != null) {
                    parseValidatePhone(data, callback, statusMsg);
                } else {
                    if(callback != null) {
                        callback.dataLoaded(status, statusMsg, true, null);
                    }
                }
            }
        });
    }

    /**
     * Parsing the validatePhone API result.
     */
    private void parseValidatePhone(JSONObject data, DataUpdateCallback callback, String statusMsg) {
        /*if (mCloudHelper.hasServiceError(data, callback)) {
            return;
        }*/
        try {
            String uniqueId = data.getString("user_id");
            String message = data.getString("message");
            Log.v("UC_TAG", "U connect uniqueId : " + uniqueId + "   message : " + message);

            if (callback != null) {
                callback.dataLoaded(NetworkStatusConstants.HTTP_SUCCESS, statusMsg, true, null);
            }
        } catch (Exception e) {
            if (callback != null) {
                callback.dataLoaded(NetworkStatusConstants.HTTP_FAILURE, "Exception Occurred", true, null);
            }
            e.printStackTrace();
        }
    }
}

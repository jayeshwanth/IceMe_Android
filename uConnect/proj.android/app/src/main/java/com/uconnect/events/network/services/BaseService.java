//***************************************************************************************************
//***************************************************************************************************
//      Project name                    		: Alesco Happy
//      Class Name                              : BaseService
//      Author                                  : PurpleTalk, Inc.
//***************************************************************************************************
//      Class Description: BaseService.
//***************************************************************************************************
//***************************************************************************************************

package com.uconnect.events.network.services;

import android.content.Context;

import com.uconnect.events.network.UCCloudHelper;

/**
* BaseService.
* */
public class BaseService {

    final UCCloudHelper mCloudHelper;

    public BaseService(Context context) {
        mCloudHelper = new UCCloudHelper(context);
    }

    public interface DataUpdateCallback {
        public void dataLoaded(byte status, String responseString, boolean loadStatus, String statusTitle);
    }
}

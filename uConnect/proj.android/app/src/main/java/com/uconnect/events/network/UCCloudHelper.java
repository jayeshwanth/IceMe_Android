//***************************************************************************************************
//***************************************************************************************************
//      Project name                    		: Alesco Happy
//      Class Name                              : CloudAdapter
//      Author                                  : PurpleTalk, Inc.
//***************************************************************************************************
//      Class Description: Used to make all API calls in an AsyncTask with appropriate callbacks.
//***************************************************************************************************
//***************************************************************************************************

package com.uconnect.events.network;

import android.content.Context;
import android.content.res.Resources;
import android.os.AsyncTask;
import android.util.Log;

import com.uconnect.events.R;
import com.uconnect.events.network.services.BaseService;
import com.uconnect.events.utils.UCAppUtils;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ConnectTimeoutException;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.SocketTimeoutException;
import java.net.URLEncoder;
import java.util.List;

/**
 * Used to make all API calls in an AsyncTask with appropriate callbacks.
 */
public class UCCloudHelper implements UCCloudConstants {

    public static String OAUTH_TOKEN;
    private final byte CM_URL = 0;
    private final byte CM_PARAM = 1;
    private final byte CM_REQ_DATA_CALL = 2;
    private final byte CM_CALLBACK = 3;
    private final Context mContext;
    private int mStatusCode = 0;

    public UCCloudHelper(Context context) {
        mContext = context;
    }

    /**
    * Creating the query string for the given list of parameters.
    * */
    private static String createQueryStringForParameters(List<NameValuePair> entityData) {
        StringBuilder parametersAsQueryString = new StringBuilder();
        if (entityData != null) {
            boolean firstParameter = true;
            parametersAsQueryString.append(Api.URL_PARAMETER_SEPERATOR);
            for (NameValuePair parameter : entityData) {
                if (!firstParameter) {
                    parametersAsQueryString.append(Api.PARAMETER_DELIMITER);
                } else {
                    firstParameter = false;
                }

                try {
                    if (parameter != null) parametersAsQueryString.append(parameter.getName())
                            .append(Api.PARAMETER_EQUALS_CHAR)
                            .append(URLEncoder.encode(parameter.getValue(), "UTF-8"));
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
            }
        }
        return parametersAsQueryString.toString();
    }

    public void getData(Context context, String url, List<NameValuePair> entity, boolean reqDataCall, JSONCallback callback) {
        if (UCAppUtils.getInstance().isNetworkAvailable(context)) {
            new GetData().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, url, entity, reqDataCall, callback);
        } else {
            callback.getJsonData(NetworkStatusConstants.HTTP_NO_NETWORK_CONNECTION, null, getStatusMsg(NetworkStatusConstants.HTTP_NO_NETWORK_CONNECTION));
        }
    }

    public void postData(Context context, String url, List<NameValuePair> entity, boolean reqDataCall, JSONCallback callback) {
        if (UCAppUtils.getInstance().isNetworkAvailable(context)) {
            new PostData().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, url, entity, reqDataCall, callback);
        } else {
            callback.getJsonData(NetworkStatusConstants.HTTP_NO_NETWORK_CONNECTION, null, getStatusMsg(NetworkStatusConstants.HTTP_NO_NETWORK_CONNECTION));
        }
    }

    /**
    * Initiating the status message based on the status of the API call.
    * */
    String getStatusMsg(byte status) {
        Resources resources = mContext.getResources();
        String statusMsg;
        switch (status) {
            case NetworkStatusConstants.HTTP_NO_NETWORK_CONNECTION:
                statusMsg = resources.getString(R.string.dg_msg_http_no_network_connection);
                break;
            case NetworkStatusConstants.HTTP_CONNECTION_TIME_OUT:
            case NetworkStatusConstants.HTTP_FAILURE:
                statusMsg = resources.getString(R.string.dg_msg_http_failure);
                break;
            default:
                statusMsg = "";
                break;
        }
        return statusMsg;
    }

    /**
    * converting the API response as string.
    * */
    private String getResponseAsString(HttpResponse response) throws Exception {
        if (response != null) {
            InputStream is = response.getEntity().getContent();
            InputStreamReader isr = new InputStreamReader(is);
            BufferedReader br = new BufferedReader(isr);
            StringBuilder responseStrBuilder = new StringBuilder();
            String inputStr;
            while ((inputStr = br.readLine()) != null) {
                responseStrBuilder.append(inputStr);
            }
            br.close();
            isr.close();
            is.close();
            return responseStrBuilder.toString();
        }
        return null;
    }

    /**
    * Checking for service error,
    * */
    public boolean hasServiceError(JSONObject data, BaseService.DataUpdateCallback callback) {
        try {
            JSONObject obj = data.getJSONObject("baseResponse");
            String statusCode = obj.getString("statusCode");
            String title = null;
            if (obj.has("statusTitle")) {
                title = obj.getString("statusTitle");
            }
            if (!statusCode.equals("200")) {
                if (callback != null) {
                    byte status = NetworkStatusConstants.HTTP_SERVICE_ERROR;
                    if (statusCode.equals("400")) {
                        status = NetworkStatusConstants.HTTP_LOGIN_ERROR;
                    }
                    callback.dataLoaded(status, obj.getString("statusMessage"), true, title);
                    return true;
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return false;
    }

    public interface JSONCallback {
        public void getJsonData(byte status, JSONObject data, String statusMsg);
    }

    /**
    * AsyncTask that uses HTTP GET to make an API call.
    * */
    private final class GetData extends AsyncTask<Object, Integer, Byte> {
        private JSONCallback jsonCallback;
        private HttpClient client;
        private HttpGet mHttpGet = null;
        private String respData;

        @Override
        protected void onPostExecute(Byte result) {
            super.onPostExecute(result);
            JSONObject jObj = null;
            try {
                if (jsonCallback != null) {
                    if (respData != null) {
                        jObj = new JSONObject(respData);
                    }
                }
            } catch (JSONException e) {
                e.printStackTrace();
                result = NetworkStatusConstants.HTTP_FAILURE;
            }
            jsonCallback.getJsonData(result, jObj, getStatusMsg(result));
        }

        @Override
        protected Byte doInBackground(Object... params) {
            List<NameValuePair> entityData = (List<NameValuePair>) params[CM_PARAM];
            String url = params[CM_URL] + createQueryStringForParameters(entityData);
            jsonCallback = (JSONCallback) params[CM_CALLBACK];
            boolean reqDataCall = (boolean) params[CM_REQ_DATA_CALL];

            /*if (!AppUtils.getInstance().isNetworkAvailable(mContext)) {
                return NetworkStatusConstants.HTTP_NO_NETWORK_CONNECTION;
            }*/
            Log.v("UC_TAG", "Alesco Signup API GetData url........." + url);
            try {
                HttpParams httpParameters = new BasicHttpParams();
                HttpConnectionParams.setConnectionTimeout(httpParameters, CONNECTION_TIMEOUT);
                HttpConnectionParams.setSoTimeout(httpParameters, CONNECTION_TIMEOUT);

                client = new DefaultHttpClient(httpParameters);
                mHttpGet = new HttpGet(url);
                mHttpGet.addHeader("Content-Type", ContentType.CONTENT_TYPE_HTML);
                mHttpGet.addHeader("Accept", ContentType.CONTENT_TYPE_JSON);
                if (reqDataCall) {
                    Log.v("UC_TAG", "Alesco Header GetData OAUTH_TOKEN : " + OAUTH_TOKEN);
                    mHttpGet.addHeader(Header.HEADER_OATH_TOKEN, OAUTH_TOKEN);
                }

                //LogUtil.verbose("Headers::::::::::::::::::" + mHttpGet.getAllHeaders().toString());

                HttpResponse response = client.execute(mHttpGet);
                //TODO : if status code is 5XX and 4XX(if base uri is wrong) need to handle this
                mStatusCode = response.getStatusLine().getStatusCode();
                respData = getResponseAsString(response);
                //String TAG = "CloudAdapter";
                //LogUtil.verbose(TAG + " Parsing Result ::: :::: " + respData + " :::::::: code ::: " + mStatusCode);
            } catch (SocketTimeoutException e) {
                e.printStackTrace();
                return NetworkStatusConstants.HTTP_FAILURE;
            } catch (ConnectTimeoutException e) {
                e.printStackTrace();
                return NetworkStatusConstants.HTTP_CONNECTION_TIME_OUT;
            } catch (ClientProtocolException e) {
                e.printStackTrace();
                return NetworkStatusConstants.HTTP_FAILURE;
            } catch (IOException e) {
                e.printStackTrace();
                return NetworkStatusConstants.HTTP_FAILURE;
            } catch (Exception e) {
                e.printStackTrace();
                return NetworkStatusConstants.HTTP_FAILURE;
            }

            return NetworkStatusConstants.HTTP_SUCCESS;
        }
    }

    /**
    * AsyncTask that uses HTTP POST to make an API call.
    * */
    private final class PostData extends AsyncTask<Object, Integer, Byte> {
        private JSONCallback jsonCallback;
        private HttpClient httpClient;
        private HttpPost mHttPost = null;
        private String respData;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(Byte result) {
            super.onPostExecute(result);
            JSONObject jObj = null;
            try {
                if (jsonCallback != null) {
                    if (respData != null) {
                        jObj = new JSONObject(respData);
                    }
                }
            } catch (JSONException e) {
                e.printStackTrace();
                result = NetworkStatusConstants.HTTP_FAILURE;
            }
            jsonCallback.getJsonData(result, jObj, getStatusMsg(result));
        }

        @SuppressWarnings("unchecked")
        @Override
        protected Byte doInBackground(Object... params) {
            List<NameValuePair> entityData = (List<NameValuePair>) params[CM_PARAM];
            String url = params[CM_URL] + createQueryStringForParameters(entityData);
            jsonCallback = (JSONCallback) params[CM_CALLBACK];
            boolean reqDataCall = (boolean) params[CM_REQ_DATA_CALL];

            /*if (!AppUtils.getInstance().isNetworkAvailable(mContext)) {
                return NetworkStatusConstants.HTTP_NO_NETWORK_CONNECTION;
            }*/
            Log.v("UC_TAG", "Alesco API PostData url........." + url);

            try {
                HttpParams httpParameters = new BasicHttpParams();
                HttpConnectionParams.setConnectionTimeout(httpParameters, CONNECTION_TIMEOUT);
                HttpConnectionParams.setSoTimeout(httpParameters, SO_TIMEOUT);

                httpClient = new DefaultHttpClient(httpParameters);
                mHttPost = new HttpPost(url);
                mHttPost.addHeader("Content-Type", ContentType.CONTENT_TYPE_HTML);
                mHttPost.addHeader("Accept", ContentType.CONTENT_TYPE_JSON);
                if (reqDataCall) {
                    Log.v("UC_TAG", "Alesco Header PostData OAUTH_TOKEN : " + OAUTH_TOKEN);
                    mHttPost.addHeader(Header.HEADER_OATH_TOKEN, OAUTH_TOKEN);
                }

                org.apache.http.Header[] headers = mHttPost.getAllHeaders();
                /*for (int i = 0; i < headers.length; i++) {
                    LogUtil.verbose("header:::::::::::" + headers[i].getName() + ":::::::::::" + headers[i].getValue());
                }*/

                HttpResponse response = httpClient.execute(mHttPost);
                mStatusCode = response.getStatusLine().getStatusCode();
                respData = getResponseAsString(response);
                Log.v("UC_TAG", "Alesco RateIT API Parsing Result ::: :::: " + respData + " :::::::: code ::: " + mStatusCode);
            } catch (SocketTimeoutException e) {
                e.printStackTrace();
                return NetworkStatusConstants.HTTP_FAILURE;
            } catch (ConnectTimeoutException e) {
                e.printStackTrace();
                return NetworkStatusConstants.HTTP_CONNECTION_TIME_OUT;
            } catch (ClientProtocolException e) {
                e.printStackTrace();
                return NetworkStatusConstants.HTTP_FAILURE;
            } catch (IOException e) {
                e.printStackTrace();
                return NetworkStatusConstants.HTTP_FAILURE;
            } catch (Exception e) {
                e.printStackTrace();
                return NetworkStatusConstants.HTTP_FAILURE;
            }
            return NetworkStatusConstants.HTTP_SUCCESS;
        }

    }
}

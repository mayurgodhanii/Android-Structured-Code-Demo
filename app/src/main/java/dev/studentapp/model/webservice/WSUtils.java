package dev.studentapp.model.webservice;

import android.content.Context;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import dev.studentapp.util.CommonMethods;
import dev.studentapp.util.Constants;
import dev.studentapp.util.Log;
import dev.studentapp.util.PrefData;

/**
 * Created by Nirav Dangi on 13/07/16.
 *
 * This is the core part of the model. It defines the Server info, Webservice urls, request etc and it has callVolley() (we can say it heart of model) method, which call the server for response.
 *
 */
public class WSUtils {

    public static final int STATUS = 200;
    private static final String SERVER = "52.25.249.220";
    public static final String XAPI = "jwZryPtnDm5WFpmDhl2o750G5gVFVSYhpXbg7tlmO";

    protected static final String END_POINT = "http://"+SERVER+"/collegeapp/restapis";
    protected static final String LOGIN = "/login.json";
    protected static final String AFTER_LOGIN = "/afterlogin.json";
    protected static final String POSTS = "/posts";
    protected static final String UPLOADFILE = "/uploadfile";
    protected static final String POST_ACTION = "/post_action";
    protected static final String POST_COMMENT = "/comments";
    protected static final String NOTIFICATIONS = "/notifications";

    private static RequestQueue requestQueue;

    protected static void callVolley(Context context, final int method, final String requestUrl, final Map<String, String> bodyParams, final OnWebResponseCallback onWebResponseCallback) {

        if (CommonMethods.haveNetworkConnection(context)) {

            if (requestQueue == null) {
                requestQueue = Volley.newRequestQueue(context);
            }

            StringRequest postRequest = new StringRequest(method, requestUrl,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            if (Constants.DEBUG_MODE)
                                Log.d("------ WEBSERVICE LOG ---------" + "\nRequest url : \n" + requestUrl + "\n---\nResponse : \n" + response + "\n------------------");

                            try {
                                if (response!=null && (new JSONObject(response).has("status")) && (new JSONObject(response).has("message")))
                                {
                                    if (new JSONObject(response).getInt("status") == STATUS) {
                                        onWebResponseCallback.onSuccess(new JSONObject(response).getString("message"), response);
                                    }
                                    else {
                                        onWebResponseCallback.onFailure(new JSONObject(response).getString("message"));
                                    }
                                }
                                else {
                                    onWebResponseCallback.onDataParsingError();
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                                onWebResponseCallback.onDataParsingError();
                            }
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            // TODO Auto-generated method stub
                            Log.d("===> " + error.getMessage());
                            onWebResponseCallback.onVolleyError(error);
                        }
                    }
            ) {
                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {
                    return getHeader();
                }

                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    if (bodyParams != null)
                        return bodyParams;
                    else
                        return super.getParams();
                }
            };

            postRequest.setRetryPolicy(new DefaultRetryPolicy(
                    100000,
                    DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                    DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

            requestQueue.add(postRequest);
        }
        else {
            onWebResponseCallback.onInternetConnectionFailure();
        }
    }

    private static Map<String, String> getHeader() {

        Map<String, String>  headerParams = new HashMap<String, String>();
        headerParams.put("Xapi", XAPI);

        // This will be fetched from shared pref //
        long mUserId = PrefData.getUserId();
        String mAccessToken = PrefData.getAccessToken(); //"3e65815939507c7965cdede3dc2344351467968813";

        if (mUserId!=-1  && mAccessToken!=null && !mAccessToken.isEmpty()) {
            headerParams.put("Id", mUserId+"");
            headerParams.put("access_token", mAccessToken);
        }

        return headerParams;
    }

    protected interface OnWebResponseCallback {
        void onSuccess(String message, String jsonResponse);
        void onFailure(String message);
        void onVolleyError(VolleyError error);
        void onInternetConnectionFailure();
        void onDataParsingError();
    }
}

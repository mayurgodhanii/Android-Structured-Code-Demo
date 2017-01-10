package dev.studentapp.model.webservice;

import android.content.Context;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import dev.studentapp.util.CommonMethods;
import dev.studentapp.util.Constants;
import dev.studentapp.util.Log;

/**
 * Created by Nirav-Dangi on 06/09/16.
 */
public class WSUtilsUserData {

    private static final String SERVER = "blooming-retreat-54852.herokuapp.com";
    protected static final String END_POINT = "https://"+SERVER+"/api";

    protected static final String LOGIN = "/v1/login";
    protected static final String REGISTER = "/v1/register";
    protected static final String ORG_REGISTRATION = "/orgs";
    protected static final String CURRENT_USER = "/v1/current_user";
    protected static final String PROFILES = "/profiles";
    protected static final String RESET = "/v1/reset";
    protected static final String FORGOT = "/v1/forgot_password";

    private static RequestQueue requestQueue;

    public static void doRegister(Context context, String username, String email, String password,
                                  String firstname, String lastname, String profileType,
                                  String emailAddress, String orgId, final OnWebResponseCallback onWebResponseCallback) {

        if (CommonMethods.haveNetworkConnection(context)) {

            if (requestQueue == null) {
                requestQueue = Volley.newRequestQueue(context);
            }

            //        {
            //            "user": {
            //                    "username": "toit4@gmail.com",
            //                    "email": "toit4@gmail.com",
            //                    "password": "Picaso123",
            //                    "profile": {
            //                        "first_name": "pito",
            //                        "last_name": "pito",
            //                        "profile_type": "STUDENT",
            //                        "email_address": "toit4@gmail.com",
            //                        "org_id": "2"
            //                    }
            //            }
            //        }

            JSONObject reqestObject = new JSONObject();

            try {
                JSONObject profile = new JSONObject();
                profile.put("first_name", firstname);
                profile.put("last_name", lastname);
                profile.put("profile_type", profileType);
                profile.put("email_address", emailAddress);
                profile.put("org_id", orgId);

                JSONObject user = new JSONObject();
                user.put("username", username);
                user.put("email", email);
                user.put("password", password);
                user.put("profile", profile);

                reqestObject.put("user", user);
            }catch (Exception e) {
                e.printStackTrace();
                onWebResponseCallback.onFailure("Error in building JsonRequest object");
                return;
            }



            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, END_POINT + REGISTER, reqestObject, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {

                    if (!response.has("email")) {
                        onWebResponseCallback.onFailure("email tag not found in response");
                        return;
                    }
                    if (!response.has("jwt")) {
                        onWebResponseCallback.onFailure("jwt tag not found in response");
                        return;
                    }
                    if (!response.has("name")) {
                        onWebResponseCallback.onFailure("name tag not found in response");
                        return;
                    }
                    if (!response.has("profile_id")) {
                        onWebResponseCallback.onFailure("profile_id tag not found in response");
                        return;
                    }
                    if (!response.has("user_id")) {
                        onWebResponseCallback.onFailure("user_id tag not found in response");
                        return;
                    }
                    if (!response.has("user_name")) {
                        onWebResponseCallback.onFailure("user_name tag not found in response");
                        return;
                    }

                    try {
                        if (response.getString("email") == null || response.getString("email").isEmpty()) {
                            onWebResponseCallback.onFailure("email tag is null or blank");
                            return;
                        }
                        if (response.getString("jwt") == null || response.getString("jwt").isEmpty()) {
                            onWebResponseCallback.onFailure("jwt tag is null or blank");
                            return;
                        }
                        if (response.getString("name") == null || response.getString("name").isEmpty()) {
                            onWebResponseCallback.onFailure("name tag is null or blank");
                            return;
                        }
                        if (response.getString("profile_id") == null || response.getString("profile_id").isEmpty()) {
                            onWebResponseCallback.onFailure("profile_id tag is null or blank");
                            return;
                        }
                        if (response.getString("user_id") == null || response.getString("user_id").isEmpty()) {
                            onWebResponseCallback.onFailure("user_id tag is null or blank");
                            return;
                        }
                        if (response.getString("user_name") == null || response.getString("user_name").isEmpty()) {
                            onWebResponseCallback.onFailure("user_name tag is null or blank");
                            return;
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                        onWebResponseCallback.onFailure("Response was success but valid JSONPARSING ERROR");
                        return;
                    }

                    onWebResponseCallback.onSuccess("Success", response.toString());
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    onWebResponseCallback.onVolleyError(error);
                }
            }) {
                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {

                    Map<String, String> headers = super.getHeaders();

                    if (headers == null
                            || headers.equals(Collections.emptyMap())) {
                        headers = new HashMap<String, String>();
                    }

                    headers.put("Content-Type", "application/json");

                    return headers;
                }
            };

            if (requestQueue == null) {
                requestQueue = Volley.newRequestQueue(context);
            }

            jsonObjectRequest.setRetryPolicy(new DefaultRetryPolicy(
                    100000,
                    DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                    DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

            requestQueue.add(jsonObjectRequest);
        }
        else {
            onWebResponseCallback.onInternetConnectionFailure();
        }
    }

    public static void doLogin(Context context, String email, String password, final OnWebResponseCallback onWebResponseCallback) {

        if (CommonMethods.haveNetworkConnection(context)) {

            if (requestQueue == null) {
                requestQueue = Volley.newRequestQueue(context);
            }
            //        {
            //            "session": {
            //                    "email": "toit4@gmail.com",
            //                    "password": "Picaso123"
            //            }
            //        }
            JSONObject reqestObject = new JSONObject();
            try {
                JSONObject session = new JSONObject();

                session.put("email", email);
                session.put("password", password);

                reqestObject.put("session", session);
            }catch (Exception e) {
                e.printStackTrace();
                onWebResponseCallback.onFailure("Error in building JsonRequest object");
                return;
            }


            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, END_POINT + LOGIN, reqestObject, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {

                    if (!response.has("email")) {
                        onWebResponseCallback.onFailure("email tag not found in response");
                        return;
                    }
                    if (!response.has("jwt")) {
                        onWebResponseCallback.onFailure("jwt tag not found in response");
                        return;
                    }
                    if (!response.has("name")) {
                        onWebResponseCallback.onFailure("name tag not found in response");
                        return;
                    }
                    if (!response.has("profile_id")) {
                        onWebResponseCallback.onFailure("profile_id tag not found in response");
                        return;
                    }
                    if (!response.has("user_id")) {
                        onWebResponseCallback.onFailure("user_id tag not found in response");
                        return;
                    }
                    if (!response.has("user_name")) {
                        onWebResponseCallback.onFailure("user_name tag not found in response");
                        return;
                    }

                    try {
                        if (response.getString("email") == null || response.getString("email").isEmpty()) {
                            onWebResponseCallback.onFailure("email tag is null or blank");
                            return;
                        }
                        if (response.getString("jwt") == null || response.getString("jwt").isEmpty()) {
                            onWebResponseCallback.onFailure("jwt tag is null or blank");
                            return;
                        }
                        if (response.getString("name") == null || response.getString("name").isEmpty()) {
                            onWebResponseCallback.onFailure("name tag is null or blank");
                            return;
                        }
                        if (response.getString("profile_id") == null || response.getString("profile_id").isEmpty()) {
                            onWebResponseCallback.onFailure("profile_id tag is null or blank");
                            return;
                        }
                        if (response.getString("user_id") == null || response.getString("user_id").isEmpty()) {
                            onWebResponseCallback.onFailure("user_id tag is null or blank");
                            return;
                        }
                        if (response.getString("user_name") == null || response.getString("user_name").isEmpty()) {
                            onWebResponseCallback.onFailure("user_name tag is null or blank");
                            return;
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                        onWebResponseCallback.onFailure("Response was success but valid JSONPARSING ERROR");
                        return;
                    }

                    onWebResponseCallback.onSuccess("Success", response.toString());
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    onWebResponseCallback.onVolleyError(error);
                }
            }) {
                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {

                    Map<String, String> headers = super.getHeaders();

                    if (headers == null
                            || headers.equals(Collections.emptyMap())) {
                        headers = new HashMap<String, String>();
                    }

                    headers.put("Content-Type", "application/json");

                    return headers;
                }
            };

            if (requestQueue == null) {
                requestQueue = Volley.newRequestQueue(context);
            }

            jsonObjectRequest.setRetryPolicy(new DefaultRetryPolicy(
                    100000,
                    DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                    DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

            requestQueue.add(jsonObjectRequest);
        }
        else {
            onWebResponseCallback.onInternetConnectionFailure();
        }
    }


    public static void getCurrentUser(Context context, final String jwtToken, final OnWebResponseCallback onWebResponseCallback) {

        if (CommonMethods.haveNetworkConnection(context)) {

            if (requestQueue == null) {
                requestQueue = Volley.newRequestQueue(context);
            }

            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, END_POINT + CURRENT_USER + "?jwt=" + jwtToken, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {

                    if (!response.has("data")) {
                        onWebResponseCallback.onFailure("data tag not found in response");
                        return;
                    }

                    try {

                        JSONObject dataResponse = response.getJSONObject("data");
                        if (dataResponse == null) {
                            onWebResponseCallback.onFailure("data tag is null");
                        }

                        if (!dataResponse.has("email")) {
                            onWebResponseCallback.onFailure("email tag not found in response");
                            return;
                        }
                        if (!dataResponse.has("id")) {
                            onWebResponseCallback.onFailure("id tag not found in response");
                            return;
                        }
                        if (!dataResponse.has("profile")) {
                            onWebResponseCallback.onFailure("profile tag not found in response");
                            return;
                        }

                        JSONObject profileData = dataResponse.getJSONObject("profile");
                        if (profileData == null) {
                            onWebResponseCallback.onFailure("profile tag is null");
                        }

                        if (!profileData.has("id")) {
                            onWebResponseCallback.onFailure("profile > id tag not found in response");
                            return;
                        }


                    } catch (Exception e) {
                        e.printStackTrace();
                        onWebResponseCallback.onFailure("Response was success but valid JSONPARSING ERROR");
                        return;
                    }

                    onWebResponseCallback.onSuccess("Success", response.toString());
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    onWebResponseCallback.onVolleyError(error);
                }
            }) {
                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {

                    Map<String, String> headers = super.getHeaders();

                    if (headers == null
                            || headers.equals(Collections.emptyMap())) {
                        headers = new HashMap<String, String>();
                    }

                    headers.put("Accept", "application/json");
                    headers.put("Authorization", jwtToken);

                    return headers;
                }
            };

            if (requestQueue == null) {
                requestQueue = Volley.newRequestQueue(context);
            }

            jsonObjectRequest.setRetryPolicy(new DefaultRetryPolicy(
                    100000,
                    DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                    DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

            requestQueue.add(jsonObjectRequest);
        }
        else {
            onWebResponseCallback.onInternetConnectionFailure();
        }
    }

    public static void getUserProfile(Context context, long id, final OnWebResponseCallback onWebResponseCallback) {

        if (CommonMethods.haveNetworkConnection(context)) {

            if (requestQueue == null) {
                requestQueue = Volley.newRequestQueue(context);
            }

            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, END_POINT + PROFILES + "/" + id, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {

                    if (!response.has("data")) {
                        onWebResponseCallback.onFailure("data tag not found in response");
                        return;
                    }

                    try {

                        JSONObject dataResponse = response.getJSONObject("data");
                        if (dataResponse == null) {
                            onWebResponseCallback.onFailure("data tag is null");
                        }

                        if (!dataResponse.has("id")) {
                            onWebResponseCallback.onFailure("id tag not found in response");
                            return;
                        }

                    } catch (Exception e) {
                        e.printStackTrace();
                        onWebResponseCallback.onFailure("Response was success but valid JSONPARSING ERROR");
                        return;
                    }

                    onWebResponseCallback.onSuccess("Success", response.toString());
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    onWebResponseCallback.onVolleyError(error);
                }
            }) {
                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {

                    Map<String, String> headers = super.getHeaders();

                    if (headers == null
                            || headers.equals(Collections.emptyMap())) {
                        headers = new HashMap<String, String>();
                    }

                    headers.put("Content-Type", "application/json");
                    return headers;
                }
            };

            if (requestQueue == null) {
                requestQueue = Volley.newRequestQueue(context);
            }

            jsonObjectRequest.setRetryPolicy(new DefaultRetryPolicy(
                    100000,
                    DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                    DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

            requestQueue.add(jsonObjectRequest);
        }
        else {
            onWebResponseCallback.onInternetConnectionFailure();
        }
    }

    public static void updateProfile(Context context, String id,
                                  String firstname, String lastname, String profileType,
                                  String username, String summary, final OnWebResponseCallback onWebResponseCallback) {

        if (CommonMethods.haveNetworkConnection(context)) {

            if (requestQueue == null) {
                requestQueue = Volley.newRequestQueue(context);
            }

//        {
//            "profile": {
//                    "first_name": "pito1",
//                    "last_name": "pito1",
//                    "profile_type": "STUDENT",
//                    "email_address": "toit4@gmail.com@gmail.com"
//             }
//        }


            JSONObject reqestObject = new JSONObject();

            try {
                JSONObject profile = new JSONObject();
                profile.put("first_name", firstname);
                profile.put("last_name", lastname);
                profile.put("profile_type", profileType);
                profile.put("username", username);
                profile.put("summary", summary);


                reqestObject.put("profile", profile);
            }catch (Exception e) {
                e.printStackTrace();
                onWebResponseCallback.onFailure("Error in building JsonRequest object");
                return;
            }

            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.PUT, END_POINT + PROFILES + "/" + id, reqestObject, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {

                    if (!response.has("data")) {
                        onWebResponseCallback.onFailure("data tag not found in response");
                        return;
                    }

                    try {

                        JSONObject dataResponse = response.getJSONObject("data");
                        if (dataResponse == null) {
                            onWebResponseCallback.onFailure("data tag is null");
                        }

                        if (!dataResponse.has("id")) {
                            onWebResponseCallback.onFailure("id tag not found in response");
                            return;
                        }

                    } catch (Exception e) {
                        e.printStackTrace();
                        onWebResponseCallback.onFailure("Response was success but valid JSONPARSING ERROR");
                        return;
                    }

                    onWebResponseCallback.onSuccess("Success", response.toString());
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    onWebResponseCallback.onVolleyError(error);
                }
            }) {
                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {

                    Map<String, String> headers = super.getHeaders();

                    if (headers == null
                            || headers.equals(Collections.emptyMap())) {
                        headers = new HashMap<String, String>();
                    }

                    headers.put("Content-Type", "application/json");

                    return headers;
                }
            };

            if (requestQueue == null) {
                requestQueue = Volley.newRequestQueue(context);
            }

            jsonObjectRequest.setRetryPolicy(new DefaultRetryPolicy(
                    100000,
                    DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                    DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

            requestQueue.add(jsonObjectRequest);
        }
        else {
            onWebResponseCallback.onInternetConnectionFailure();
        }
    }


    public static void resetPassword(Context context, final String jwtToken, String email,
                                     String oldPassword, String newPassword, final OnWebResponseCallback onWebResponseCallback) {

        if (CommonMethods.haveNetworkConnection(context)) {

            if (requestQueue == null) {
                requestQueue = Volley.newRequestQueue(context);
            }

//            {
//                  "email": "amol@gmail.com",
//                    "old": "Picaso234",
//                    "new": "Picaso123"
//            }

            JSONObject reqestObject = new JSONObject();
            try {

                reqestObject.put("email", email);
                reqestObject.put("old", oldPassword);
                reqestObject.put("new", newPassword);

            }catch (Exception e) {
                e.printStackTrace();
                onWebResponseCallback.onFailure("Error in building JsonRequest object");
                return;
            }

            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, END_POINT + RESET, reqestObject, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {

                    if (Constants.DEBUG_MODE)
                        Log.d("------ WEBSERVICE LOG ---------" + "\nRequest url : \n" + RESET + "\n---\nResponse : \n" + response + "\n------------------");


                    if (!response.has("return_message")) {
                        onWebResponseCallback.onFailure("return_message tag not found in response");
                        return;
                    }

                    try {

                        String dataResponse = response.getString("return_message");

                        if (dataResponse == null) {
                            onWebResponseCallback.onFailure("return_message tag is null");
                        }
                        else {
                            onWebResponseCallback.onSuccess(dataResponse, response.toString());
                        }

                    } catch (Exception e) {
                        e.printStackTrace();
                        onWebResponseCallback.onFailure("Response was success but valid JSONPARSING ERROR");
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    onWebResponseCallback.onVolleyError(error);
                }
            }) {
                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {

                    Map<String, String> headers = super.getHeaders();

                    if (headers == null
                            || headers.equals(Collections.emptyMap())) {
                        headers = new HashMap<String, String>();
                    }

                    headers.put("Content-Type", "application/json");
                    headers.put("Authorization", jwtToken);

                    return headers;
                }
            };

            jsonObjectRequest.setRetryPolicy(new DefaultRetryPolicy(
                    100000,
                    DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                    DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

            requestQueue.add(jsonObjectRequest);
        }
        else {
            onWebResponseCallback.onInternetConnectionFailure();
        }
    }

    public static void forgotPassword(Context context, String email, final OnWebResponseCallback onWebResponseCallback) {

        if (CommonMethods.haveNetworkConnection(context)) {

            if (requestQueue == null) {
                requestQueue = Volley.newRequestQueue(context);
            }

//            {
//                  "email": "amol@gmail.com",
//                    "old": "Picaso234",
//                    "new": "Picaso123"
//            }

            JSONObject reqestObject = new JSONObject();
            try {

                reqestObject.put("email", email);

            }catch (Exception e) {
                e.printStackTrace();
                onWebResponseCallback.onFailure("Error in building JsonRequest object");
                return;
            }

            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, END_POINT + FORGOT, reqestObject, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {

                    if (Constants.DEBUG_MODE)
                        Log.d("------ WEBSERVICE LOG ---------" + "\nRequest url : \n" + FORGOT + "\n---\nResponse : \n" + response + "\n------------------");


                    if (!response.has("return_message")) {
                        onWebResponseCallback.onFailure("return_message tag not found in response");
                        return;
                    }

                    try {

                        String dataResponse = response.getString("return_message");

                        if (dataResponse == null) {
                            onWebResponseCallback.onFailure("return_message tag is null");
                        }
                        else {
                            onWebResponseCallback.onSuccess(dataResponse, response.toString());
                        }

                    } catch (Exception e) {
                        e.printStackTrace();
                        onWebResponseCallback.onFailure("Response was success but valid JSONPARSING ERROR");
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    onWebResponseCallback.onVolleyError(error);
                }
            }) {
                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {

                    Map<String, String> headers = super.getHeaders();

                    if (headers == null
                            || headers.equals(Collections.emptyMap())) {
                        headers = new HashMap<String, String>();
                    }

                    headers.put("Content-Type", "application/json");

                    return headers;
                }
            };

            jsonObjectRequest.setRetryPolicy(new DefaultRetryPolicy(
                    100000,
                    DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                    DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

            requestQueue.add(jsonObjectRequest);
        }
        else {
            onWebResponseCallback.onInternetConnectionFailure();
        }
    }

    protected interface OnWebResponseCallback {
        void onSuccess(String message, String jsonResponse);
        void onFailure(String message);
        void onVolleyError(VolleyError error);
        void onInternetConnectionFailure();
        void onDataParsingError();
    }
}

package dev.studentapp.model.webservice;

import android.content.Context;

import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import dev.studentapp.R;
import dev.studentapp.model.listners.JSONResponseCallback;
import dev.studentapp.util.Constants;
import dev.studentapp.util.Log;
import dev.studentapp.util.PrefData;

import static dev.studentapp.model.webservice.WSUtils.AFTER_LOGIN;
import static dev.studentapp.model.webservice.WSUtils.END_POINT;
import static dev.studentapp.model.webservice.WSUtils.NOTIFICATIONS;
import static dev.studentapp.model.webservice.WSUtils.OnWebResponseCallback;
import static dev.studentapp.model.webservice.WSUtils.POSTS;
import static dev.studentapp.model.webservice.WSUtils.POST_ACTION;
import static dev.studentapp.model.webservice.WSUtils.POST_COMMENT;
import static dev.studentapp.model.webservice.WSUtils.UPLOADFILE;
import static dev.studentapp.model.webservice.WSUtils.callVolley;

/**
 * @author Nirav Dangi
 *
 * This class contains all the webservice calling methods.
 *
 */
public class WSMethods {

//    public static void doLoginToServer(final Context context, String username, String password, String deviceToken, String deviceType, final JSONResponseCallback jsonResponseCallback) {
//        String requestUrl = END_POINT + LOGIN;
//
//        Map<String, String>  bodyParams = new HashMap<String, String>();
//        bodyParams.put("username", username);
//        bodyParams.put("password", password);
//        bodyParams.put("device_token", deviceToken);
//        bodyParams.put("device_type", deviceType);
//
//        callVolley(context, Request.Method.POST, requestUrl, bodyParams, new OnWebResponseCallback() {
//            @Override
//            public void onSuccess(String message, String jsonResponse) {
//                jsonResponseCallback.onSuccess(message, jsonResponse);
//            }
//
//            @Override
//            public void onFailure(String message) {
//                jsonResponseCallback.onFailure(message);
//            }
//
//            @Override
//            public void onVolleyError(VolleyError error) {
//                jsonResponseCallback.onFailure(error.getMessage());
//            }
//
//            @Override
//            public void onInternetConnectionFailure() {
//                jsonResponseCallback.onInternetConnectionFailure();
//            }
//
//            @Override
//            public void onDataParsingError() {
//                jsonResponseCallback.onFailure(context.getString(R.string.error_status_message_tag_missing));
//            }
//        });
//
//    }

    public static void doLoginToServer(final Context context, final String username, String password, String deviceToken, String deviceType, final JSONResponseCallback jsonResponseCallback) {

        WSUtilsUserData.doLogin(context, username, password, new WSUtilsUserData.OnWebResponseCallback() {
            @Override
            public void onSuccess(String message, String jsonResponse) {

                try {
                    doAfterLoginToServer(context, new JSONObject(jsonResponse).getString("jwt"), jsonResponseCallback);
                }catch (Exception e) {
                    e.printStackTrace();
                    jsonResponseCallback.onFailure(e.toString());
                }
            }

            @Override
            public void onFailure(String message) {
                jsonResponseCallback.onFailure(message);
            }

            @Override
            public void onVolleyError(VolleyError error) {
                jsonResponseCallback.onFailure(error.getMessage());
            }

            @Override
            public void onInternetConnectionFailure() {
                jsonResponseCallback.onInternetConnectionFailure();
            }

            @Override
            public void onDataParsingError() {
                jsonResponseCallback.onFailure(context.getString(R.string.error_status_message_tag_missing));
            }
        });
    }

    public static void doAfterLoginToServer(final Context context, String jwtToken, final JSONResponseCallback jsonResponseCallback) {
        String requestUrl = END_POINT + AFTER_LOGIN;

        Map<String, String>  bodyParams = new HashMap<String, String>();
        bodyParams.put("jwt", jwtToken);

        callVolley(context, Request.Method.POST, requestUrl, bodyParams, new OnWebResponseCallback() {
            @Override
            public void onSuccess(String message, String jsonResponse) {
                jsonResponseCallback.onSuccess(message, jsonResponse);
            }

            @Override
            public void onFailure(String message) {
                jsonResponseCallback.onFailure(message);
            }

            @Override
            public void onVolleyError(VolleyError error) {
                jsonResponseCallback.onFailure(error.getMessage());
            }

            @Override
            public void onInternetConnectionFailure() {
                jsonResponseCallback.onInternetConnectionFailure();
            }

            @Override
            public void onDataParsingError() {
                jsonResponseCallback.onFailure(context.getString(R.string.error_status_message_tag_missing));
            }
        });
    }

    public static void doRegistrationToServer(final Context context, String username, String email, String password,
                                      String firstname, String lastname, String profileType,
                                      String emailAddress, String orgId, final JSONResponseCallback jsonResponseCallback) {
        WSUtilsUserData.doRegister(context, username, email, password, firstname, lastname, profileType, emailAddress, orgId, new WSUtilsUserData.OnWebResponseCallback() {
            @Override
            public void onSuccess(String message, String jsonResponse) {
                try {
                    doAfterLoginToServer(context, new JSONObject(jsonResponse).getString("jwt"), jsonResponseCallback);
                }catch (Exception e) {
                    e.printStackTrace();
                    jsonResponseCallback.onFailure(e.toString());
                }
            }

            @Override
            public void onFailure(String message) {
                jsonResponseCallback.onFailure(message);
            }

            @Override
            public void onVolleyError(VolleyError error) {
                jsonResponseCallback.onFailure(error.getMessage());
            }

            @Override
            public void onInternetConnectionFailure() {
                jsonResponseCallback.onInternetConnectionFailure();
            }

            @Override
            public void onDataParsingError() {
                jsonResponseCallback.onFailure(context.getString(R.string.error_status_message_tag_missing));
            }
        });
    }

    public static void updateProfileToServer(final Context context, String id,
                                             String firstname, String lastname, String profileType,
                                             String username, String summary, final JSONResponseCallback jsonResponseCallback) {
        WSUtilsUserData.updateProfile(context, id, firstname, lastname, profileType, username, summary, new WSUtilsUserData.OnWebResponseCallback() {
            @Override
            public void onSuccess(String message, String jsonResponse) {
                try {
                    doAfterLoginToServer(context, PrefData.getAccessToken(), jsonResponseCallback);
                }catch (Exception e) {
                    e.printStackTrace();
                    jsonResponseCallback.onFailure(e.toString());
                }
            }

            @Override
            public void onFailure(String message) {
                jsonResponseCallback.onFailure(message);
            }

            @Override
            public void onVolleyError(VolleyError error) {
                jsonResponseCallback.onFailure(error.getMessage());
            }

            @Override
            public void onInternetConnectionFailure() {
                jsonResponseCallback.onInternetConnectionFailure();
            }

            @Override
            public void onDataParsingError() {
                jsonResponseCallback.onFailure(context.getString(R.string.error_status_message_tag_missing));
            }
        });
    }

    public static void resetPasswordToServer(final Context context, String jwtToken, String email,
                                     String oldPassword, String newPassword, final JSONResponseCallback jsonResponseCallback) {
        WSUtilsUserData.resetPassword(context, jwtToken, email, oldPassword, newPassword, new WSUtilsUserData.OnWebResponseCallback() {
            @Override
            public void onSuccess(String message, String jsonResponse) {
                jsonResponseCallback.onSuccess("Your password changed successfully!", jsonResponse);
            }

            @Override
            public void onFailure(String message) {
                jsonResponseCallback.onFailure(message);
            }

            @Override
            public void onVolleyError(VolleyError error) {
                jsonResponseCallback.onFailure(error.getMessage());
            }

            @Override
            public void onInternetConnectionFailure() {
                jsonResponseCallback.onInternetConnectionFailure();
            }

            @Override
            public void onDataParsingError() {
                jsonResponseCallback.onFailure(context.getString(R.string.error_status_message_tag_missing));
            }
        });
    }

    public static void forgotPasswordToServer(final Context context, String email, final JSONResponseCallback jsonResponseCallback) {
        WSUtilsUserData.forgotPassword(context, email, new WSUtilsUserData.OnWebResponseCallback() {
            @Override
            public void onSuccess(String message, String jsonResponse) {
                jsonResponseCallback.onSuccess(message, jsonResponse);
            }

            @Override
            public void onFailure(String message) {
                jsonResponseCallback.onFailure(message);
            }

            @Override
            public void onVolleyError(VolleyError error) {
                jsonResponseCallback.onFailure(error.getMessage());
            }

            @Override
            public void onInternetConnectionFailure() {
                jsonResponseCallback.onInternetConnectionFailure();
            }

            @Override
            public void onDataParsingError() {
                jsonResponseCallback.onFailure(context.getString(R.string.error_status_message_tag_missing));
            }
        });
    }

    public static void viewProfileToServer(final Context context, long id, final JSONResponseCallback jsonResponseCallback) {
        WSUtilsUserData.getUserProfile(context, id, new WSUtilsUserData.OnWebResponseCallback() {
            @Override
            public void onSuccess(String message, String jsonResponse) {
                jsonResponseCallback.onSuccess(message, jsonResponse);
            }

            @Override
            public void onFailure(String message) {
                jsonResponseCallback.onFailure(message);
            }

            @Override
            public void onVolleyError(VolleyError error) {
                jsonResponseCallback.onFailure(error.getMessage());
            }

            @Override
            public void onInternetConnectionFailure() {
                jsonResponseCallback.onInternetConnectionFailure();
            }

            @Override
            public void onDataParsingError() {
                jsonResponseCallback.onFailure(context.getString(R.string.error_status_message_tag_missing));
            }
        });
    }

    public static void getServerFeeds(final Context context, int feedType, long page, int pageLimit, final JSONResponseCallback jsonResponseCallback) {

        String requestUrl = END_POINT + POSTS + "?limit="+pageLimit+"&page="+page;

        callVolley(context, Request.Method.GET, requestUrl, null, new OnWebResponseCallback() {

            @Override
            public void onSuccess(String message, String jsonResponse) {
                jsonResponseCallback.onSuccess(message, jsonResponse);
            }

            @Override
            public void onFailure(String message) {
                jsonResponseCallback.onFailure(message);
            }

            @Override
            public void onVolleyError(VolleyError error) {
                jsonResponseCallback.onFailure(error.getMessage());
            }

            @Override
            public void onInternetConnectionFailure() {
                jsonResponseCallback.onInternetConnectionFailure();
            }

            @Override
            public void onDataParsingError() {
                jsonResponseCallback.onFailure(context.getString(R.string.error_status_message_tag_missing));
            }
        });
    }

    public static void getNotificationFromServer(final Context context, long page, int pageLimit, final JSONResponseCallback jsonResponseCallback) {

        String requestUrl = END_POINT + NOTIFICATIONS + "?limit="+pageLimit+"&page="+page;

        callVolley(context, Request.Method.GET, requestUrl, null, new OnWebResponseCallback() {

            @Override
            public void onSuccess(String message, String jsonResponse) {
                jsonResponseCallback.onSuccess(message, jsonResponse);
            }

            @Override
            public void onFailure(String message) {
                jsonResponseCallback.onFailure(message);
            }

            @Override
            public void onVolleyError(VolleyError error) {
                jsonResponseCallback.onFailure(error.getMessage());
            }

            @Override
            public void onInternetConnectionFailure() {
                jsonResponseCallback.onInternetConnectionFailure();
            }

            @Override
            public void onDataParsingError() {
                jsonResponseCallback.onFailure(context.getString(R.string.error_status_message_tag_missing));
            }
        });
    }

    public static void uploadPhotoToServer(final Context context, File imageFile, final JSONResponseCallback jsonResponseCallback){

        final String requestUrl = END_POINT + UPLOADFILE;

        RequestQueue mQueue = Volley.newRequestQueue(context);
        PhotoMultipartRequest imageUploadReq = new PhotoMultipartRequest(requestUrl, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                jsonResponseCallback.onFailure(error.getMessage());
            }
        }, new Response.Listener<NetworkResponse>() {
            @Override
            public void onResponse(NetworkResponse res) {

                String response;
                try {
                    response = new String(res.data, HttpHeaderParser.parseCharset(res.headers));
                } catch (UnsupportedEncodingException e) {
                    response = new String(res.data);
                }

                if (Constants.DEBUG_MODE)
                    Log.d("------ WEBSERVICE LOG ---------" + "\nRequest url : \n" + requestUrl + "\n---\nResponse : \n" + response + "\n------------------");

                try {
                    if (response!=null && (new JSONObject(response).has("status")) && (new JSONObject(response).has("message")))
                    {
                        if (new JSONObject(response).getInt("status") == WSUtils.STATUS) {
                            jsonResponseCallback.onSuccess(new JSONObject(response).getString("message"), response);
                        }
                        else {
                            jsonResponseCallback.onFailure(new JSONObject(response).getString("message"));
                        }
                    }
                    else {
                        jsonResponseCallback.onFailure(context.getString(R.string.error_status_message_tag_missing));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    jsonResponseCallback.onFailure(context.getString(R.string.error_json_parsing) +": "+ e.toString());
                }
            }
        }, imageFile);
        mQueue.add(imageUploadReq);
    }

    public static void uploadPhotoAmolToServer(final Context context, long id, File imageFile, final JSONResponseCallback jsonResponseCallback){

        final String requestUrl = WSUtilsUserData.END_POINT + WSUtilsUserData.PROFILES + "/" + id;

        RequestQueue mQueue = Volley.newRequestQueue(context);
        PhotoMultipartRequestAmol imageUploadReq = new PhotoMultipartRequestAmol(requestUrl, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                jsonResponseCallback.onFailure(error.getMessage());
            }
        }, new Response.Listener<NetworkResponse>() {
            @Override
            public void onResponse(NetworkResponse res) {

                String response;
                try {
                    response = new String(res.data, HttpHeaderParser.parseCharset(res.headers));
                } catch (UnsupportedEncodingException e) {
                    response = new String(res.data);
                }

                jsonResponseCallback.onSuccess("", response);

//                if (Constants.DEBUG_MODE)
//                    Log.d("------ WEBSERVICE LOG ---------" + "\nRequest url : \n" + requestUrl + "\n---\nResponse : \n" + response + "\n------------------");
//
//                try {
//                    if (response!=null && (new JSONObject(response).has("status")) && (new JSONObject(response).has("message")))
//                    {
//                        if (new JSONObject(response).getInt("status") == WSUtils.STATUS) {
//                            jsonResponseCallback.onSuccess(new JSONObject(response).getString("message"), response);
//                        }
//                        else {
//                            jsonResponseCallback.onFailure(new JSONObject(response).getString("message"));
//                        }
//                    }
//                    else {
//                        jsonResponseCallback.onFailure(context.getString(R.string.error_status_message_tag_missing));
//                    }
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                    jsonResponseCallback.onFailure(context.getString(R.string.error_json_parsing) +": "+ e.toString());
//                }
            }
        }, imageFile);
        mQueue.add(imageUploadReq);
    }

    public static void addFeedToServer(final Context context, String text, String categoryId, String tags,
                                       ArrayList<String> assets, String urlTitle, String urlShortDesc, String urlLink,
                                       String urlImageLink, final JSONResponseCallback jsonResponseCallback) {

        Map<String, String> bodyParams = new HashMap<String, String>();
        bodyParams.put("taxon_id", categoryId==null?"0":categoryId);
        bodyParams.put("text", text);
        bodyParams.put("tags", tags==null?"":tags);

        if (assets!=null) {
            for (int i = 0; i < assets.size(); i++) {
                bodyParams.put("assets[" + i + "]", assets.get(i));
            }
        }

        bodyParams.put("url[short_desc]", urlShortDesc==null?"":urlShortDesc);
        bodyParams.put("url[title]", urlTitle==null?"":urlTitle);
        bodyParams.put("url[link]", urlLink==null?"":urlLink);
        bodyParams.put("url[asset_id]", urlImageLink==null?"":urlImageLink);

        String requestUrl = END_POINT + POSTS;

        callVolley(context, Request.Method.POST, requestUrl, bodyParams, new OnWebResponseCallback() {

            @Override
            public void onSuccess(String message, String jsonResponse) {
                jsonResponseCallback.onSuccess(message, jsonResponse);
            }

            @Override
            public void onFailure(String message) {
                jsonResponseCallback.onFailure(message);
            }

            @Override
            public void onVolleyError(VolleyError error) {
                jsonResponseCallback.onFailure(error.getMessage());
            }

            @Override
            public void onInternetConnectionFailure() {
                jsonResponseCallback.onInternetConnectionFailure();
            }

            @Override
            public void onDataParsingError() {
                jsonResponseCallback.onFailure(context.getString(R.string.error_status_message_tag_missing));
            }
        });
    }

    public static void postActionToServer(final Context context, long id, String action, final JSONResponseCallback jsonResponseCallback) {

        String requestUrl = END_POINT + POSTS + "/"+id + POST_ACTION;

        Map<String, String> bodyParams = new HashMap<String, String>();
        bodyParams.put("type", action);

        callVolley(context, Request.Method.POST, requestUrl, bodyParams, new OnWebResponseCallback() {

            @Override
            public void onSuccess(String message, String jsonResponse) {
                jsonResponseCallback.onSuccess(message, jsonResponse);
            }

            @Override
            public void onFailure(String message) {
                jsonResponseCallback.onFailure(message);
            }

            @Override
            public void onVolleyError(VolleyError error) {
                jsonResponseCallback.onFailure(error.getMessage());
            }

            @Override
            public void onInternetConnectionFailure() {
                jsonResponseCallback.onInternetConnectionFailure();
            }

            @Override
            public void onDataParsingError() {
                jsonResponseCallback.onFailure(context.getString(R.string.error_status_message_tag_missing));
            }
        });
    }

    public static void getFeedDetailFromServer(final Context context, long id, final JSONResponseCallback jsonResponseCallback) {

        String requestUrl = END_POINT + POSTS + "/"+id;

        callVolley(context, Request.Method.GET, requestUrl, null, new OnWebResponseCallback() {

            @Override
            public void onSuccess(String message, String jsonResponse) {
                jsonResponseCallback.onSuccess(message, jsonResponse);
            }

            @Override
            public void onFailure(String message) {
                jsonResponseCallback.onFailure(message);
            }

            @Override
            public void onVolleyError(VolleyError error) {
                jsonResponseCallback.onFailure(error.getMessage());
            }

            @Override
            public void onInternetConnectionFailure() {
                jsonResponseCallback.onInternetConnectionFailure();
            }

            @Override
            public void onDataParsingError() {
                jsonResponseCallback.onFailure(context.getString(R.string.error_status_message_tag_missing));
            }
        });
    }

    public static void getFeedCommentsFromServer(final Context context, long id, final JSONResponseCallback jsonResponseCallback) {

        String requestUrl = END_POINT + POSTS + "/" +id+ "/" + POST_COMMENT;

        callVolley(context, Request.Method.GET, requestUrl, null, new OnWebResponseCallback() {

            @Override
            public void onSuccess(String message, String jsonResponse) {
                jsonResponseCallback.onSuccess(message, jsonResponse);
            }

            @Override
            public void onFailure(String message) {
                jsonResponseCallback.onFailure(message);
            }

            @Override
            public void onVolleyError(VolleyError error) {
                jsonResponseCallback.onFailure(error.getMessage());
            }

            @Override
            public void onInternetConnectionFailure() {
                jsonResponseCallback.onInternetConnectionFailure();
            }

            @Override
            public void onDataParsingError() {
                jsonResponseCallback.onFailure(context.getString(R.string.error_status_message_tag_missing));
            }
        });
    }

    public static void addFeedCommentToServer(final Context context, long id, String text, final JSONResponseCallback jsonResponseCallback) {

        String requestUrl = END_POINT + POSTS + "/" + id + "/" + POST_COMMENT;

        Map<String, String> bodyParams = new HashMap<String, String>();
        bodyParams.put("text", text);

        callVolley(context, Request.Method.POST, requestUrl, bodyParams, new OnWebResponseCallback() {

            @Override
            public void onSuccess(String message, String jsonResponse) {
                jsonResponseCallback.onSuccess(message, jsonResponse);
            }

            @Override
            public void onFailure(String message) {
                jsonResponseCallback.onFailure(message);
            }

            @Override
            public void onVolleyError(VolleyError error) {
                jsonResponseCallback.onFailure(error.getMessage());
            }

            @Override
            public void onInternetConnectionFailure() {
                jsonResponseCallback.onInternetConnectionFailure();
            }

            @Override
            public void onDataParsingError() {
                jsonResponseCallback.onFailure(context.getString(R.string.error_status_message_tag_missing));
            }
        });
    }

    public static void getFeedCommentDetailFromServer(final Context context, long feedId, long feedCommentId, final JSONResponseCallback jsonResponseCallback) {

        String requestUrl = END_POINT + POSTS + "/" + feedId + "/" + POST_COMMENT + "/" + feedCommentId;

        callVolley(context, Request.Method.GET, requestUrl, null, new OnWebResponseCallback() {

            @Override
            public void onSuccess(String message, String jsonResponse) {
                jsonResponseCallback.onSuccess(message, jsonResponse);
            }

            @Override
            public void onFailure(String message) {
                jsonResponseCallback.onFailure(message);
            }

            @Override
            public void onVolleyError(VolleyError error) {
                jsonResponseCallback.onFailure(error.getMessage());
            }

            @Override
            public void onInternetConnectionFailure() {
                jsonResponseCallback.onInternetConnectionFailure();
            }

            @Override
            public void onDataParsingError() {
                jsonResponseCallback.onFailure(context.getString(R.string.error_status_message_tag_missing));
            }
        });

    }
}

package dev.studentapp.model.methods;

import android.content.Context;
import android.provider.Settings;

import org.json.JSONException;
import org.json.JSONObject;

import dev.studentapp.R;
import dev.studentapp.model.dataset.login.LoginDataset;
import dev.studentapp.model.listners.JSONResponseCallback;
import dev.studentapp.model.listners.OnDatasetReceiveCallback;
import dev.studentapp.model.webservice.WSMethods;
import dev.studentapp.util.CommonMethods;

/**
 * Created by Nirav Dangi on 10/08/16.
 *
 * Here all the login related methods are defined.
 */
public class LoginMethods {

    public static void doLogin(final Context context, String username, String password, final OnDatasetReceiveCallback<LoginDataset> onDatasetReceiveCallback) {
        if (!CommonMethods.haveNetworkConnection(context)) {
            onDatasetReceiveCallback.onInternetConnectionFailure();
        }
        else {

            String android_id;
            try {
                android_id = Settings.Secure.getString(context.getContentResolver(),
                        Settings.Secure.ANDROID_ID);
            }catch (Exception e) {
                e.printStackTrace();
                android_id = "0123456789";
            }

            WSMethods.doLoginToServer(context, username, password, android_id, "Android", new JSONResponseCallback() {
                @Override
                public void onSuccess(String message, String jsonResponse) {

                    LoginDataset loginDataset;

                    try {
                        loginDataset = new LoginDataset(new JSONObject(jsonResponse).getJSONObject("data"));

                        if (loginDataset!=null) {
                            onDatasetReceiveCallback.onDatasetReceive(loginDataset);
                        }
                        else {
                            onDatasetReceiveCallback.onFailureToReceiveDataset((context.getString(R.string.error_dataset_rececived_null)));
                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                        onDatasetReceiveCallback.onFailureToReceiveDataset(context.getString(R.string.error_json_parsing) +": "+ e.toString());
                    }
                }

                @Override
                public void onFailure(String message) {
                    onDatasetReceiveCallback.onFailureToReceiveDataset(message);
                }

                @Override
                public void onInternetConnectionFailure() {
                    onDatasetReceiveCallback.onInternetConnectionFailure();
                }
            });
        }
    }


    public static void doRegistration(final Context context, String username, String email, String password,
                                      String firstname, String lastname, String profileType,
                                      String emailAddress, String orgId, final OnDatasetReceiveCallback<LoginDataset> onDatasetReceiveCallback) {
        if (!CommonMethods.haveNetworkConnection(context)) {
            onDatasetReceiveCallback.onInternetConnectionFailure();
        }
        else {

//            String android_id;
//            try {
//                android_id = Settings.Secure.getString(context.getContentResolver(),
//                        Settings.Secure.ANDROID_ID);
//            }catch (Exception e) {
//                e.printStackTrace();
//                android_id = "0123456789";
//            }

            WSMethods.doRegistrationToServer(context, username, email, password, firstname, lastname, profileType, emailAddress, orgId, new JSONResponseCallback() {
                @Override
                public void onSuccess(String message, String jsonResponse) {

                    LoginDataset loginDataset;

                    try {
                        loginDataset = new LoginDataset(new JSONObject(jsonResponse).getJSONObject("data"));

                        if (loginDataset!=null) {
                            onDatasetReceiveCallback.onDatasetReceive(loginDataset);
                        }
                        else {
                            onDatasetReceiveCallback.onFailureToReceiveDataset((context.getString(R.string.error_dataset_rececived_null)));
                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                        onDatasetReceiveCallback.onFailureToReceiveDataset(context.getString(R.string.error_json_parsing) +": "+ e.toString());
                    }
                }

                @Override
                public void onFailure(String message) {
                    onDatasetReceiveCallback.onFailureToReceiveDataset(message);
                }

                @Override
                public void onInternetConnectionFailure() {
                    onDatasetReceiveCallback.onInternetConnectionFailure();
                }
            });
        }
    }

    public static void forgotPassword(final Context context, String email, final OnDatasetReceiveCallback<String> onDatasetReceiveCallback) {
        if (!CommonMethods.haveNetworkConnection(context)) {
            onDatasetReceiveCallback.onInternetConnectionFailure();
        }
        else {

            WSMethods.forgotPasswordToServer(context, email, new JSONResponseCallback() {
                @Override
                public void onSuccess(String message, String jsonResponse) {

                    onDatasetReceiveCallback.onDatasetReceive(message);
                }

                @Override
                public void onFailure(String message) {
                    onDatasetReceiveCallback.onFailureToReceiveDataset(message);
                }

                @Override
                public void onInternetConnectionFailure() {
                    onDatasetReceiveCallback.onInternetConnectionFailure();
                }
            });
        }
    }
}

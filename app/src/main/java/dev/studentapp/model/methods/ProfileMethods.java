package dev.studentapp.model.methods;

import android.content.Context;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;

import dev.studentapp.R;
import dev.studentapp.model.dataset.login.LoginDataset;
import dev.studentapp.model.dataset.viewprofile.ViewProfileDataset;
import dev.studentapp.model.listners.JSONResponseCallback;
import dev.studentapp.model.listners.OnDatasetReceiveCallback;
import dev.studentapp.model.webservice.WSMethods;
import dev.studentapp.util.CommonMethods;

/**
 * Created by Nirav-Dangi on 06/09/16.
 */
public class ProfileMethods {

    public static void updateProfile(final Context context, String id,
                                     String firstname, String lastname, String profileType,
                                     String username, String summary, final OnDatasetReceiveCallback<LoginDataset> onDatasetReceiveCallback) {
        if (!CommonMethods.haveNetworkConnection(context)) {
            onDatasetReceiveCallback.onInternetConnectionFailure();
        }
        else {

            WSMethods.updateProfileToServer(context, id, firstname, lastname, profileType, username, summary,  new JSONResponseCallback() {
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

    public static void resetPassword(final Context context, String jwtToken, String email,
                                     String oldPassword, String newPassword, final OnDatasetReceiveCallback<String> onDatasetReceiveCallback) {
        if (!CommonMethods.haveNetworkConnection(context)) {
            onDatasetReceiveCallback.onInternetConnectionFailure();
        }
        else {

            WSMethods.resetPasswordToServer(context, jwtToken, email, oldPassword, newPassword,  new JSONResponseCallback() {
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

    public static void viewProfile(final Context context, long id, final OnDatasetReceiveCallback<ViewProfileDataset> onDatasetReceiveCallback) {

        if (!CommonMethods.haveNetworkConnection(context)) {
            onDatasetReceiveCallback.onInternetConnectionFailure();
        }
        else {

            WSMethods.viewProfileToServer(context, id, new JSONResponseCallback() {
                @Override
                public void onSuccess(String message, String jsonResponse) {

                    ViewProfileDataset viewProfileDataset;

                    try {
                        viewProfileDataset = new ViewProfileDataset(new JSONObject(jsonResponse).getJSONObject("data"));

                        if (viewProfileDataset!=null) {
                            onDatasetReceiveCallback.onDatasetReceive(viewProfileDataset);
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

    public static void uploadPhoto(final Context context, long id, File imageFile, final OnDatasetReceiveCallback<String> onDatasetReceiveCallback){
        if (!CommonMethods.haveNetworkConnection(context)) {
            onDatasetReceiveCallback.onInternetConnectionFailure();
        }
        else {
            WSMethods.uploadPhotoAmolToServer(context, id, imageFile, new JSONResponseCallback() {
                @Override
                public void onSuccess(String message, String jsonResponse) {

                    onDatasetReceiveCallback.onDatasetReceive(jsonResponse);
//                    try {
//                        String filename = new JSONObject(jsonResponse).getJSONObject("data").getString("image");
//                        onDatasetReceiveCallback.onDatasetReceive(filename);
//                    } catch (JSONException e) {
//                        e.printStackTrace();
//                        onDatasetReceiveCallback.onFailureToReceiveDataset(context.getString(R.string.error_json_parsing) +": "+ e.toString());
//                    }
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

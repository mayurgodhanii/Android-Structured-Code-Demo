package dev.studentapp.model.listners;


/**
 *
 * Created by Nirav Dangi on 08/07/16.
 *
 * An Interface, having callback methods which returns server response (json)
 *
 */
public interface JSONResponseCallback {
        void onSuccess(String message, String jsonResponse);
        void onFailure(String message);
        void onInternetConnectionFailure();
}
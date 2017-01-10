package dev.studentapp.model.methods;

import android.content.Context;

import org.json.JSONException;

import dev.studentapp.R;
import dev.studentapp.model.dataset.notifications.NotificationsDataset;
import dev.studentapp.model.listners.JSONResponseCallback;
import dev.studentapp.model.listners.OnDatasetReceiveCallback;
import dev.studentapp.model.webservice.WSMethods;
import dev.studentapp.util.CommonMethods;

/**
 * Created by Nirav-Dangi on 08/09/16.
 */
public class NotificationMethods {

    private static int PAGE_LIMIT = 20;

    public static void getNotification(final Context context, long page, final OnDatasetReceiveCallback<NotificationsDataset> onDatasetReceiveCallback){

        if (!CommonMethods.haveNetworkConnection(context)) {

            onDatasetReceiveCallback.onInternetConnectionFailure();
        }
        else {
            WSMethods.getNotificationFromServer(context, page, PAGE_LIMIT, new JSONResponseCallback() {

                @Override
                public void onSuccess(String message, String jsonResponse) {

                    NotificationsDataset notificationsDataset = null;

                    try {
                        notificationsDataset = new NotificationsDataset(jsonResponse);

                        if (notificationsDataset!=null)
                            onDatasetReceiveCallback.onDatasetReceive(notificationsDataset);
                        else
                            onDatasetReceiveCallback.onFailureToReceiveDataset(context.getString(R.string.error_dataset_rececived_null));

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
}

package dev.studentapp.model.dataset.notifications;

import android.os.Parcel;
import android.os.Parcelable;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Nirav-Dangi on 08/09/16.
 */
public class NotificationsDataset implements Parcelable{

    private dev.studentapp.model.dataset.notifications.PagingDataset pagingDataset;
    private ArrayList<NotificationItemDataset> notificationItemDatasetArrayList;

    public NotificationsDataset(String strJsonResponse) throws JSONException {

        JSONObject dataObject = new JSONObject(strJsonResponse).getJSONObject("data");

        pagingDataset = new dev.studentapp.model.dataset.notifications.PagingDataset(dataObject.getJSONObject("paging"));

        JSONArray listJsonArray = dataObject.getJSONArray("list");

        notificationItemDatasetArrayList = new ArrayList<NotificationItemDataset>();

        for (int i = 0; i < listJsonArray.length(); i++) {
            notificationItemDatasetArrayList.add(new NotificationItemDataset(listJsonArray.getJSONObject(i)));
        }
    }

    protected NotificationsDataset(Parcel in) {
        pagingDataset = in.readParcelable(PagingDataset.class.getClassLoader());
        notificationItemDatasetArrayList = in.createTypedArrayList(NotificationItemDataset.CREATOR);
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(pagingDataset, flags);
        dest.writeTypedList(notificationItemDatasetArrayList);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<NotificationsDataset> CREATOR = new Creator<NotificationsDataset>() {
        @Override
        public NotificationsDataset createFromParcel(Parcel in) {
            return new NotificationsDataset(in);
        }

        @Override
        public NotificationsDataset[] newArray(int size) {
            return new NotificationsDataset[size];
        }
    };

    public PagingDataset getPagingDataset() {
        return pagingDataset;
    }

    public ArrayList<NotificationItemDataset> getNotificationItemDatasetArrayList() {
        return notificationItemDatasetArrayList;
    }
}

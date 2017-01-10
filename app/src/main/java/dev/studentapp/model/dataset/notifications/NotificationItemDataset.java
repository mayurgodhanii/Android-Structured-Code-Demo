package dev.studentapp.model.dataset.notifications;

import android.os.Parcel;
import android.os.Parcelable;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Nirav-Dangi on 08/09/16.
 */
public class NotificationItemDataset implements Parcelable{

    private long id;
    private long postId;
    private String avatar;
    private String notification;
    private long created;

    public NotificationItemDataset(JSONObject jsonObject) throws JSONException {

        id = jsonObject.getLong("id");
        postId = jsonObject.getLong("post_id");
        avatar = jsonObject.getString("avatar");
        notification = jsonObject.getString("notification");
        created = jsonObject.getLong("created");
    }


    protected NotificationItemDataset(Parcel in) {
        id = in.readLong();
        postId = in.readLong();
        avatar = in.readString();
        notification = in.readString();
        created = in.readLong();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(id);
        dest.writeLong(postId);
        dest.writeString(avatar);
        dest.writeString(notification);
        dest.writeLong(created);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<NotificationItemDataset> CREATOR = new Creator<NotificationItemDataset>() {
        @Override
        public NotificationItemDataset createFromParcel(Parcel in) {
            return new NotificationItemDataset(in);
        }

        @Override
        public NotificationItemDataset[] newArray(int size) {
            return new NotificationItemDataset[size];
        }
    };

    public long getId() {
        return id;
    }

    public long getPostId() {
        return postId;
    }

    public String getAvatar() {
        return avatar;
    }

    public String getNotification() {
        return notification;
    }

    public long getCreated() {
        return created;
    }
}

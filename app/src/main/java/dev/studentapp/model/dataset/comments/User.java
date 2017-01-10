package dev.studentapp.model.dataset.comments;

import android.os.Parcel;
import android.os.Parcelable;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Nirav Dangi on 12/08/16.
 */
public class User implements Parcelable{

    private long id;
    private String fullName;
    private String avatar;

    public User(JSONObject jsonObject) throws JSONException {

        id = jsonObject.getLong("id");
        fullName = jsonObject.getString("full_name");
        avatar = jsonObject.getString("avatar");
    }

    protected User(Parcel in) {
        id = in.readLong();
        fullName = in.readString();
        avatar = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(id);
        dest.writeString(fullName);
        dest.writeString(avatar);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<User> CREATOR = new Creator<User>() {
        @Override
        public User createFromParcel(Parcel in) {
            return new User(in);
        }

        @Override
        public User[] newArray(int size) {
            return new User[size];
        }
    };

    public long getId() {
        return id;
    }

    public String getFullName() {
        return fullName;
    }

    public String getAvatar() {
        return avatar;
    }
}

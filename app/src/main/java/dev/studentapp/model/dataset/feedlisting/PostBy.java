package dev.studentapp.model.dataset.feedlisting;

import android.os.Parcel;
import android.os.Parcelable;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Nirav Dangi on 05/08/16.
 */
public class PostBy implements Parcelable{

    private long id;
    private String avatar;
    private String fullName;
    private long profileType;
    private String currentPosition;
    private String alias;
    private String summary;

    /*
            {
               "id":2,
               "avatar":"",
               "full_name":"Test User",
               "profile_type":2,
               "current_position":"",
               "alias":"",
               "summary":"Test"
            }
     */
    public PostBy(JSONObject jsonObject) throws JSONException {
        id = jsonObject.getLong("id");
        avatar = jsonObject.getString("avatar");
        fullName = jsonObject.getString("full_name");
        profileType = jsonObject.getLong("profile_type");
        currentPosition = jsonObject.getString("current_position");
        alias = jsonObject.getString("alias");
        summary = jsonObject.getString("summary");
    }

    protected PostBy(Parcel in) {
        id = in.readLong();
        avatar = in.readString();
        fullName = in.readString();
        profileType = in.readLong();
        currentPosition = in.readString();
        alias = in.readString();
        summary = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(id);
        dest.writeString(avatar);
        dest.writeString(fullName);
        dest.writeLong(profileType);
        dest.writeString(currentPosition);
        dest.writeString(alias);
        dest.writeString(summary);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<PostBy> CREATOR = new Creator<PostBy>() {
        @Override
        public PostBy createFromParcel(Parcel in) {
            return new PostBy(in);
        }

        @Override
        public PostBy[] newArray(int size) {
            return new PostBy[size];
        }
    };

    public long getId() {
        return id;
    }

    public String getAvatar() {
        return avatar;
    }

    public String getFullName() {
        return fullName;
    }

    public long getProfileType() {
        return profileType;
    }

    public String getCurrentPosition() {
        return currentPosition;
    }

    public String getAlias() {
        return alias;
    }

    public String getSummary() {
        return summary;
    }
}

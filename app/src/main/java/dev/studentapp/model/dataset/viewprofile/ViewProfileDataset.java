package dev.studentapp.model.dataset.viewprofile;

import android.os.Parcel;
import android.os.Parcelable;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Nirav-Dangi on 06/09/16.
 */
public class ViewProfileDataset implements Parcelable{

    private long id;
    private String profileType;
    private String lastName;
    private String firtName;
    private String avatarUrl;

    public ViewProfileDataset(JSONObject jsonObject) throws JSONException {

        id = jsonObject.getLong("id");
        profileType = jsonObject.getString("profile_type");
        lastName = jsonObject.getString("last_name");
        firtName = jsonObject.getString("first_name");
        avatarUrl = jsonObject.getString("avatar_url");
    }


    protected ViewProfileDataset(Parcel in) {
        id = in.readLong();
        profileType = in.readString();
        lastName = in.readString();
        firtName = in.readString();
        avatarUrl = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(id);
        dest.writeString(profileType);
        dest.writeString(lastName);
        dest.writeString(firtName);
        dest.writeString(avatarUrl);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<ViewProfileDataset> CREATOR = new Creator<ViewProfileDataset>() {
        @Override
        public ViewProfileDataset createFromParcel(Parcel in) {
            return new ViewProfileDataset(in);
        }

        @Override
        public ViewProfileDataset[] newArray(int size) {
            return new ViewProfileDataset[size];
        }
    };

    public long getId() {
        return id;
    }

    public String getProfileType() {
        return profileType;
    }

    public String getLastName() {
        return lastName;
    }

    public String getFirtName() {
        return firtName;
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }
}

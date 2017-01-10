package dev.studentapp.model.dataset.login;

import android.os.Parcel;
import android.os.Parcelable;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Nirav Dangi on 10/08/16.
 */
public class LoginDataset implements Parcelable{

    private long userId;
    private String email;
    private long roleId;
    private String accessToken;
    private String name;

    //== New Params ===//
    private String username;
    private String summary;
    private String profileType;
    private String lastName;
    private String firtName;
    private String avatarUrl;


    public LoginDataset(JSONObject jsonObject) throws JSONException{

        userId = jsonObject.getLong("user_id");
        email = jsonObject.getString("email");
        roleId = jsonObject.getLong("role_id");
        accessToken = jsonObject.getString("access_token");
        name = jsonObject.getString("name");

        JSONObject curretUserProfile = jsonObject.getJSONObject("current_user_profile").getJSONObject("data");
        username = curretUserProfile.getString("username");

        JSONObject profile = curretUserProfile.getJSONObject("profile");
        summary = profile.getString("summary");
        profileType = profile.getString("profile_type");
        lastName = profile.getString("last_name");
        firtName = profile.getString("first_name");
        avatarUrl = profile.getString("avatar_url");
    }


    protected LoginDataset(Parcel in) {
        userId = in.readLong();
        email = in.readString();
        roleId = in.readLong();
        accessToken = in.readString();
        name = in.readString();

        username = in.readString();
        summary = in.readString();
        profileType = in.readString();
        lastName = in.readString();
        firtName = in.readString();
        avatarUrl = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(userId);
        dest.writeString(email);
        dest.writeLong(roleId);
        dest.writeString(accessToken);
        dest.writeString(name);

        dest.writeString(username);
        dest.writeString(summary);
        dest.writeString(profileType);
        dest.writeString(lastName);
        dest.writeString(firtName);
        dest.writeString(avatarUrl);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<LoginDataset> CREATOR = new Creator<LoginDataset>() {
        @Override
        public LoginDataset createFromParcel(Parcel in) {
            return new LoginDataset(in);
        }

        @Override
        public LoginDataset[] newArray(int size) {
            return new LoginDataset[size];
        }
    };

    public long getUserId() {
        return userId;
    }

    public String getEmail() {
        return email;
    }

    public long getRoleId() {
        return roleId;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public String getName() {
        return name;
    }

    public String getUsername() {
        return username;
    }

    public String getSummary() {
        return summary;
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

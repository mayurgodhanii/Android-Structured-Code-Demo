package dev.studentapp.model.dataset.feedlisting;

import android.os.Parcel;
import android.os.Parcelable;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Nirav Dangi on 05/08/16.
 */
public class PostUploads implements Parcelable {

    private long id;
    private String name;
    private String thumbUrl;

    /*
        {
           "id":6,
           "name":"http://test.jpg"
           "thumb_url":"http://testmini.jpg"
         }
     */
    public PostUploads(JSONObject jsonObject) throws JSONException {
        id = jsonObject.getLong("id");
        name = jsonObject.getString("name");
        thumbUrl = jsonObject.getString("thumb_url");
    }

    protected PostUploads(Parcel in) {
        id = in.readLong();
        name = in.readString();
        thumbUrl = in.readString();
    }

    public static final Creator<PostUploads> CREATOR = new Creator<PostUploads>() {
        @Override
        public PostUploads createFromParcel(Parcel in) {
            return new PostUploads(in);
        }

        @Override
        public PostUploads[] newArray(int size) {
            return new PostUploads[size];
        }
    };

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getThumbUrl() {
        return thumbUrl;
    }

    /**
     * Describe the kinds of special objects contained in this Parcelable's
     * marshalled representation.
     *
     * @return a bitmask indicating the set of special object types marshalled
     * by the Parcelable.
     */
    @Override
    public int describeContents() {
        return 0;
    }

    /**
     * Flatten this object in to a Parcel.
     *
     * @param dest  The Parcel in which the object should be written.
     * @param flags Additional flags about how the object should be written.
     *              May be 0 or {@link #PARCELABLE_WRITE_RETURN_VALUE}.
     */
    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(id);
        dest.writeString(name);
        dest.writeString(thumbUrl);
    }
}

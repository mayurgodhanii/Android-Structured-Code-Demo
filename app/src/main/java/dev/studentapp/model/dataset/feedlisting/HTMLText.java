package dev.studentapp.model.dataset.feedlisting;

import android.os.Parcel;
import android.os.Parcelable;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Nirav Dangi on 05/08/16.
 */
public class HTMLText implements Parcelable{

    private String shortDesc;
    private String title;
    private String link;
    private String asset;

    /*
            {
              "short_desc": "",
              "title": "",
              "link": "",
              "asset": ""
            }

     */
    public HTMLText(JSONObject jsonObject) throws JSONException {
        shortDesc = jsonObject.getString("short_desc");
        title = jsonObject.getString("title");
        link = jsonObject.getString("link");
        asset = jsonObject.getString("asset");
    }

    protected HTMLText(Parcel in) {
        shortDesc = in.readString();
        title = in.readString();
        link = in.readString();
        asset = in.readString();
    }

    public static final Creator<HTMLText> CREATOR = new Creator<HTMLText>() {
        @Override
        public HTMLText createFromParcel(Parcel in) {
            return new HTMLText(in);
        }

        @Override
        public HTMLText[] newArray(int size) {
            return new HTMLText[size];
        }
    };

    public String getShortDesc() {
        return shortDesc;
    }

    public String getTitle() {
        return title;
    }

    public String getLink() {
        return link;
    }

    public String getAsset() {
        return asset;
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
        dest.writeString(shortDesc);
        dest.writeString(title);
        dest.writeString(link);
        dest.writeString(asset);
    }
}

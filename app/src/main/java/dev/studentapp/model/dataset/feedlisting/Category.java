package dev.studentapp.model.dataset.feedlisting;

import android.os.Parcel;
import android.os.Parcelable;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Nirav Dangi on 05/08/16.
 */
public class Category implements Parcelable{

    private long id;
    private long taxonomyId;
    private String name;

    /*
            {
               "id":1,
               "taxonomy_id":1,
               "name":"Campus"
            }
     */
    public Category(JSONObject jsonObject) throws JSONException {
        id = jsonObject.getLong("id");
        taxonomyId = jsonObject.getLong("taxonomy_id");
        name = jsonObject.getString("name");
    }

    protected Category(Parcel in) {
        id = in.readLong();
        taxonomyId = in.readLong();
        name = in.readString();
    }

    public static final Creator<Category> CREATOR = new Creator<Category>() {
        @Override
        public Category createFromParcel(Parcel in) {
            return new Category(in);
        }

        @Override
        public Category[] newArray(int size) {
            return new Category[size];
        }
    };

    public long getId() {
        return id;
    }

    public long getTaxonomyId() {
        return taxonomyId;
    }

    public String getName() {
        return name;
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
        dest.writeLong(taxonomyId);
        dest.writeString(name);
    }
}

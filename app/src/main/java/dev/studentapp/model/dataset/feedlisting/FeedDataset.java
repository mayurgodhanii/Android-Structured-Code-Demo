package dev.studentapp.model.dataset.feedlisting;

import android.os.Parcel;
import android.os.Parcelable;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Nirav Dangi on 05/08/16.
 */
public class FeedDataset implements Parcelable{

    private PagingDataset pagingDataset;
    private ArrayList<FeedItemDataset> feedItemDatasetArrayList;

    public FeedDataset(String strJsonResponse) throws JSONException {

        JSONObject dataObject = new JSONObject(strJsonResponse).getJSONObject("data");

        pagingDataset = new PagingDataset(dataObject.getJSONObject("paging"));

        JSONArray listJsonArray = dataObject.getJSONArray("list");

        feedItemDatasetArrayList = new ArrayList<FeedItemDataset>();

        for (int i = 0; i < listJsonArray.length(); i++) {
            feedItemDatasetArrayList.add(new FeedItemDataset(listJsonArray.getJSONObject(i)));
        }
    }

    protected FeedDataset(Parcel in) {
        pagingDataset = in.readParcelable(PagingDataset.class.getClassLoader());
        feedItemDatasetArrayList = in.createTypedArrayList(FeedItemDataset.CREATOR);
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(pagingDataset, flags);
        dest.writeTypedList(feedItemDatasetArrayList);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<FeedDataset> CREATOR = new Creator<FeedDataset>() {
        @Override
        public FeedDataset createFromParcel(Parcel in) {
            return new FeedDataset(in);
        }

        @Override
        public FeedDataset[] newArray(int size) {
            return new FeedDataset[size];
        }
    };

    public PagingDataset getPagingDataset() {
        return pagingDataset;
    }

    public ArrayList<FeedItemDataset> getFeedItemDatasetArrayList() {
        return feedItemDatasetArrayList;
    }
}

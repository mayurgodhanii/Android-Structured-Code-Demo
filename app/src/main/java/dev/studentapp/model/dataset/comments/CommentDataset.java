package dev.studentapp.model.dataset.comments;

import android.os.Parcel;
import android.os.Parcelable;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Nirav Dangi on 12/08/16.
 */
public class CommentDataset implements Parcelable{

    private PagingDataset pagingDataset;
    private ArrayList<CommentItemDataset> commentItemDatasetArrayList;

    public CommentDataset(String strJsonResponse) throws JSONException {

        JSONObject dataObject = new JSONObject(strJsonResponse).getJSONObject("data");

        pagingDataset = new PagingDataset(dataObject.getJSONObject("paging"));

        JSONArray listJsonArray = dataObject.getJSONArray("list");

        commentItemDatasetArrayList = new ArrayList<CommentItemDataset>();

        for (int i = 0; i < listJsonArray.length(); i++) {
            commentItemDatasetArrayList.add(new CommentItemDataset(listJsonArray.getJSONObject(i)));
        }
    }

    protected CommentDataset(Parcel in) {
        pagingDataset = in.readParcelable(PagingDataset.class.getClassLoader());
        commentItemDatasetArrayList = in.createTypedArrayList(CommentItemDataset.CREATOR);
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(pagingDataset, flags);
        dest.writeTypedList(commentItemDatasetArrayList);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<CommentDataset> CREATOR = new Creator<CommentDataset>() {
        @Override
        public CommentDataset createFromParcel(Parcel in) {
            return new CommentDataset(in);
        }

        @Override
        public CommentDataset[] newArray(int size) {
            return new CommentDataset[size];
        }
    };

    public PagingDataset getPagingDataset() {
        return pagingDataset;
    }

    public ArrayList<CommentItemDataset> getCommentItemDatasetArrayList() {
        return commentItemDatasetArrayList;
    }
}

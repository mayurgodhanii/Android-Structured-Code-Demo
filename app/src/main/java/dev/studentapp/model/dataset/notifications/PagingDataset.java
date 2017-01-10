package dev.studentapp.model.dataset.notifications;

import android.os.Parcel;
import android.os.Parcelable;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Nirav Dangi on 05/08/16.
 */
public class PagingDataset implements Parcelable{

    private long currentPageIndex;
    private long recordCountInCurrentPage;
    private long recordCountPerPage;
    private long prevPageIndex;
    private long nextPageIndex;
    private long totalPageCount;
    private long totalRecordCount;

    /*
        {
             "page":1,
             "current_page_records":5,
             "total_records":20,
             "perPage":5,
             "prevPage":0,
             "nextPage":2,
             "pageCount":4
        }
     */

    public PagingDataset(JSONObject jsonObject) throws JSONException {
        currentPageIndex = jsonObject.getLong("page");
        recordCountInCurrentPage = jsonObject.getLong("current_page_records");
        recordCountPerPage = jsonObject.getLong("perPage");
        prevPageIndex = jsonObject.getLong("prevPage");
        nextPageIndex = jsonObject.getLong("nextPage");
        totalPageCount = jsonObject.getLong("pageCount");
        totalRecordCount = jsonObject.getLong("total_records");
    }

    protected PagingDataset(Parcel in) {
        currentPageIndex = in.readLong();
        recordCountInCurrentPage = in.readLong();
        recordCountPerPage = in.readLong();
        prevPageIndex = in.readLong();
        nextPageIndex = in.readLong();
        totalPageCount = in.readLong();
        totalRecordCount = in.readLong();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(currentPageIndex);
        dest.writeLong(recordCountInCurrentPage);
        dest.writeLong(recordCountPerPage);
        dest.writeLong(prevPageIndex);
        dest.writeLong(nextPageIndex);
        dest.writeLong(totalPageCount);
        dest.writeLong(totalRecordCount);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<PagingDataset> CREATOR = new Creator<PagingDataset>() {
        @Override
        public PagingDataset createFromParcel(Parcel in) {
            return new PagingDataset(in);
        }

        @Override
        public PagingDataset[] newArray(int size) {
            return new PagingDataset[size];
        }
    };

    public long getCurrentPageIndex() {
        return currentPageIndex;
    }

    public long getRecordCountInCurrentPage() {
        return recordCountInCurrentPage;
    }

    public long getRecordCountPerPage() {
        return recordCountPerPage;
    }

    public long getPrevPageIndex() {
        return prevPageIndex;
    }

    public long getNextPageIndex() {
        return nextPageIndex;
    }

    public long getTotalPageCount() {
        return totalPageCount;
    }

    public long getTotalRecordCount() {
        return totalRecordCount;
    }
}

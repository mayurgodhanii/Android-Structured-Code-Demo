package dev.studentapp.model.listners;

/**
 * Created by Nirav Dangi on 08/07/16.
 *
 * This interface has been designed to parse json response by feeding data into the datasets.
 */
public interface OnDatasetReceiveCallback<T> {

    void onDatasetReceive(T dataset);

    void onFailureToReceiveDataset(String failure);

    void onInternetConnectionFailure();
}

package dev.studentapp.view.feeds.feedlist;

/**
 * Created by Nirav Dangi on 10/08/16.
 */
public interface OnPostAction<T> {
    void onSuccess(T dataset);
    void onFailure();
}

package dev.studentapp.model.methods;

import android.content.Context;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;

import dev.studentapp.R;
import dev.studentapp.model.dataset.comments.CommentDataset;
import dev.studentapp.model.dataset.comments.CommentItemDataset;
import dev.studentapp.model.dataset.feedlisting.FeedDataset;
import dev.studentapp.model.dataset.feedlisting.FeedItemDataset;
import dev.studentapp.model.listners.JSONResponseCallback;
import dev.studentapp.model.listners.OnDatasetReceiveCallback;
import dev.studentapp.model.webservice.WSMethods;
import dev.studentapp.util.CommonMethods;

/**
 * Created by Nirav Dangi on 05/07/16.
 *
 * Here all the feed related methods are defined. This class has been design in order to get the data stored in local and server.
 * For example when we call getFeeds() method, it will check for internet availability, if NOT found then it will return data stored into local
 * or if found then FIRST it will return all local data and after it SECOND it will retrieve & return updated/new records from the server.
 */
public class FeedMethods {

    private static int CAMPUS_FEEDS = 2;
    private static int HOME_FEEDS = 1;
    private static int LECTURER_FEEDS = 0;

    private static int PAGE_LIMIT = 10;

    private static void getFeeds(final Context context,final int feedType, long page, final OnDatasetReceiveCallback<FeedDataset> onDatasetReceiveCallback){

        if (!CommonMethods.haveNetworkConnection(context)) {

            onDatasetReceiveCallback.onInternetConnectionFailure();
        }
        else {
            WSMethods.getServerFeeds(context, feedType, page, PAGE_LIMIT, new JSONResponseCallback() {

                @Override
                public void onSuccess(String message, String jsonResponse) {

                    FeedDataset feedDataset = null;

                    try {
                        feedDataset = new FeedDataset(jsonResponse);

                        if (feedDataset!=null)
                            onDatasetReceiveCallback.onDatasetReceive(feedDataset);
                        else
                            onDatasetReceiveCallback.onFailureToReceiveDataset(context.getString(R.string.error_dataset_rececived_null));

                    } catch (JSONException e) {
                        e.printStackTrace();
                        onDatasetReceiveCallback.onFailureToReceiveDataset(context.getString(R.string.error_json_parsing) +": "+ e.toString());
                    }
                }

                @Override
                public void onFailure(String message) {
                    onDatasetReceiveCallback.onFailureToReceiveDataset(message);
                }

                @Override
                public void onInternetConnectionFailure() {

                    onDatasetReceiveCallback.onInternetConnectionFailure();
                }
            });
        }
    }

    public static void getCampusFeeds(Context context, long page, OnDatasetReceiveCallback<FeedDataset> onDatasetReceiveCallback){

        getFeeds(context, CAMPUS_FEEDS, page, onDatasetReceiveCallback);
    }

    public static void getHomeFeeds(Context context, long page, OnDatasetReceiveCallback<FeedDataset> onDatasetReceiveCallback){

        getFeeds(context, HOME_FEEDS, page, onDatasetReceiveCallback);
    }

    public static void getLecturerFeeds(Context context, long page, OnDatasetReceiveCallback<FeedDataset> onDatasetReceiveCallback){

        getFeeds(context, LECTURER_FEEDS, page, onDatasetReceiveCallback);
    }


    public static void addFeed(Context context, String text, String categoryId, String tags, ArrayList<String> assets, String urlTitle, String urlShortDesc, String urlLink, String urlImageLink, JSONResponseCallback jsonResponseCallback) {
        if (!CommonMethods.haveNetworkConnection(context)) {
            jsonResponseCallback.onInternetConnectionFailure();
        }
        else {
            WSMethods.addFeedToServer(context, text, categoryId, tags, assets, urlTitle, urlShortDesc, urlLink, urlImageLink, jsonResponseCallback);
        }
    }

    public static void uploadPhoto(final Context context, File imageFile, final OnDatasetReceiveCallback<String> onDatasetReceiveCallback){
        if (!CommonMethods.haveNetworkConnection(context)) {
            onDatasetReceiveCallback.onInternetConnectionFailure();
        }
        else {
            WSMethods.uploadPhotoToServer(context, imageFile, new JSONResponseCallback() {
                @Override
                public void onSuccess(String message, String jsonResponse) {

                    try {
                        String filename = new JSONObject(jsonResponse).getJSONObject("data").getString("image");
                        onDatasetReceiveCallback.onDatasetReceive(filename);
                    } catch (JSONException e) {
                        e.printStackTrace();
                        onDatasetReceiveCallback.onFailureToReceiveDataset(context.getString(R.string.error_json_parsing) +": "+ e.toString());
                    }
                }

                @Override
                public void onFailure(String message) {
                    onDatasetReceiveCallback.onFailureToReceiveDataset(message);
                }

                @Override
                public void onInternetConnectionFailure() {

                    onDatasetReceiveCallback.onInternetConnectionFailure();
                }
            });
        }
    }

    public static void getFeedDetail(final Context context, long id, final OnDatasetReceiveCallback<FeedItemDataset> onDatasetReceiveCallback) {
        if (!CommonMethods.haveNetworkConnection(context)) {
            onDatasetReceiveCallback.onInternetConnectionFailure();
        }
        else {
            WSMethods.getFeedDetailFromServer(context, id, new JSONResponseCallback() {
                @Override
                public void onSuccess(String message, String jsonResponse) {
                    FeedItemDataset feedItemDataset;

                    try {

                        JSONObject jsonObject = new JSONObject(jsonResponse).getJSONObject("data");
                        feedItemDataset = new FeedItemDataset(jsonObject);

                        if (feedItemDataset!=null)
                            onDatasetReceiveCallback.onDatasetReceive(feedItemDataset);
                        else
                            onDatasetReceiveCallback.onFailureToReceiveDataset(context.getString(R.string.error_dataset_rececived_null));

                    } catch (JSONException e) {
                        e.printStackTrace();
                        onDatasetReceiveCallback.onFailureToReceiveDataset(context.getString(R.string.error_json_parsing) +": "+ e.toString());
                    }
                }

                @Override
                public void onFailure(String message) {
                    onDatasetReceiveCallback.onFailureToReceiveDataset(message);
                }

                @Override
                public void onInternetConnectionFailure() {

                    onDatasetReceiveCallback.onInternetConnectionFailure();
                }
            });
        }
    }

    private static void postAction(final Context context, final long feedId, final long feedCommentId, String action, final OnDatasetReceiveCallback<CommentItemDataset> onDatasetReceiveCallback) {
        if (!CommonMethods.haveNetworkConnection(context)) {
            onDatasetReceiveCallback.onInternetConnectionFailure();
        }
        else {

            WSMethods.postActionToServer(context, feedCommentId, action, new JSONResponseCallback() {
                @Override
                public void onSuccess(String message, String jsonResponse) {


                    getFeedCommentDetail(context, feedId, feedCommentId, onDatasetReceiveCallback);

                }

                @Override
                public void onFailure(String message) {
                    onDatasetReceiveCallback.onFailureToReceiveDataset(message);
                }

                @Override
                public void onInternetConnectionFailure() {

                    onDatasetReceiveCallback.onInternetConnectionFailure();
                }
            });
        }
    }

    private static void postAction(final Context context, final long id, String action, final OnDatasetReceiveCallback<FeedItemDataset> onDatasetReceiveCallback) {
        if (!CommonMethods.haveNetworkConnection(context)) {
            onDatasetReceiveCallback.onInternetConnectionFailure();
        }
        else {

            WSMethods.postActionToServer(context, id, action, new JSONResponseCallback() {
                @Override
                public void onSuccess(String message, String jsonResponse) {

                    getFeedDetail(context, id, onDatasetReceiveCallback);

                }

                @Override
                public void onFailure(String message) {
                    onDatasetReceiveCallback.onFailureToReceiveDataset(message);
                }

                @Override
                public void onInternetConnectionFailure() {

                    onDatasetReceiveCallback.onInternetConnectionFailure();
                }
            });
        }
    }

    public static void postActionLike(Context context, long id, final OnDatasetReceiveCallback<FeedItemDataset> onDatasetReceiveCallback) {
        postAction(context, id, "post_like", onDatasetReceiveCallback);
    }

    public static void postActionDisLike(Context context, long id, final OnDatasetReceiveCallback<FeedItemDataset> onDatasetReceiveCallback) {
        postAction(context, id, "post_dislike", onDatasetReceiveCallback);
    }

    public static void postActionLikeComment(Context context, long feedId, long feedCommentId, final OnDatasetReceiveCallback<CommentItemDataset> onDatasetReceiveCallback) {
        postAction(context, feedId, feedCommentId, "comment_like", onDatasetReceiveCallback);
    }

    public static void postActionDisLikeComment(Context context, long feedId, long feedCommentId, final OnDatasetReceiveCallback<CommentItemDataset> onDatasetReceiveCallback) {
        postAction(context, feedId, feedCommentId, "comment_dislike", onDatasetReceiveCallback);
    }

    public static void postActionReportAbuse(Context context, long id, final OnDatasetReceiveCallback<FeedItemDataset> onDatasetReceiveCallback) {
        postAction(context, id, "post_abuse", onDatasetReceiveCallback);
    }

    public static void getFeedComments(final Context context, long id, final OnDatasetReceiveCallback<CommentDataset> onDatasetReceiveCallback) {
        if (!CommonMethods.haveNetworkConnection(context)) {
            onDatasetReceiveCallback.onInternetConnectionFailure();
        }
        else {
            WSMethods.getFeedCommentsFromServer(context, id, new JSONResponseCallback() {
                @Override
                public void onSuccess(String message, String jsonResponse) {

                    CommentDataset commentDataset;

                    try {

                        commentDataset = new CommentDataset(jsonResponse);

                        if (commentDataset!=null)
                            onDatasetReceiveCallback.onDatasetReceive(commentDataset);
                        else
                            onDatasetReceiveCallback.onFailureToReceiveDataset(context.getString(R.string.error_dataset_rececived_null));

                    } catch (JSONException e) {
                        e.printStackTrace();
                        onDatasetReceiveCallback.onFailureToReceiveDataset(context.getString(R.string.error_json_parsing) +": "+ e.toString());
                    }
                }

                @Override
                public void onFailure(String message) {
                    onDatasetReceiveCallback.onFailureToReceiveDataset(message);
                }

                @Override
                public void onInternetConnectionFailure() {

                    onDatasetReceiveCallback.onInternetConnectionFailure();
                }
            });
        }
    }

    public static void addFeedComment(final Context context, final long id, String feedText, final OnDatasetReceiveCallback<CommentItemDataset> onDatasetReceiveCallback) {
        if (!CommonMethods.haveNetworkConnection(context)) {
            onDatasetReceiveCallback.onInternetConnectionFailure();
        }
        else {
            WSMethods.addFeedCommentToServer(context, id, feedText, new JSONResponseCallback() {
                @Override
                public void onSuccess(String message, String jsonResponse) {

                    try {
                        long commentId = new JSONObject(jsonResponse).getJSONObject("data").getLong("comment_id");

                        getFeedCommentDetail(context, id, commentId, onDatasetReceiveCallback);

                    } catch (JSONException e) {
                        e.printStackTrace();
                        onDatasetReceiveCallback.onFailureToReceiveDataset(context.getString(R.string.error_json_parsing) +": "+ e.toString());
                    }
                }

                @Override
                public void onFailure(String message) {
                    onDatasetReceiveCallback.onFailureToReceiveDataset(message);
                }

                @Override
                public void onInternetConnectionFailure() {

                    onDatasetReceiveCallback.onInternetConnectionFailure();
                }
            });
        }
    }

    public static void getFeedCommentDetail(final Context context, long feedId, long feedCommentId, final OnDatasetReceiveCallback<CommentItemDataset> onDatasetReceiveCallback) {
        if (!CommonMethods.haveNetworkConnection(context)) {
            onDatasetReceiveCallback.onInternetConnectionFailure();
        }
        else {
            WSMethods.getFeedCommentDetailFromServer(context, feedId, feedCommentId, new JSONResponseCallback() {
                @Override
                public void onSuccess(String message, String jsonResponse) {

                    CommentItemDataset commentItemDataset;

                    try {

                        commentItemDataset = new CommentItemDataset(new JSONObject(jsonResponse).getJSONObject("data"));

                        if (commentItemDataset!=null)
                            onDatasetReceiveCallback.onDatasetReceive(commentItemDataset);
                        else
                            onDatasetReceiveCallback.onFailureToReceiveDataset((context.getString(R.string.error_dataset_rececived_null)));

                    } catch (JSONException e) {
                        e.printStackTrace();
                        onDatasetReceiveCallback.onFailureToReceiveDataset(context.getString(R.string.error_json_parsing) +": "+ e.toString());
                    }
                }

                @Override
                public void onFailure(String message) {
                    onDatasetReceiveCallback.onFailureToReceiveDataset(message);
                }

                @Override
                public void onInternetConnectionFailure() {

                    onDatasetReceiveCallback.onInternetConnectionFailure();
                }
            });
        }
    }
}

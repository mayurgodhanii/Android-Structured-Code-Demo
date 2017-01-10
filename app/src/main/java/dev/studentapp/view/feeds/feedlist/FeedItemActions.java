package dev.studentapp.view.feeds.feedlist;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.support.v4.widget.TextViewCompat;
import android.support.v7.widget.PopupMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import dev.studentapp.R;
import dev.studentapp.model.dataset.comments.CommentItemDataset;
import dev.studentapp.model.dataset.feedlisting.FeedItemDataset;
import dev.studentapp.model.dataset.feedlisting.PostUploads;
import dev.studentapp.model.listners.OnDatasetReceiveCallback;
import dev.studentapp.model.methods.FeedMethods;
import dev.studentapp.util.CommonMethods;
import dev.studentapp.util.PrefData;
import dev.studentapp.view.feeds.feeddetail.FeedDetailActivity;
import dev.studentapp.view.userprofile.EditUserProfileActivity;
import dev.studentapp.view.userprofile.ViewUserProfileActivity;

/**
 * Created by Nirav Dangi on 29/06/16.
 */
public class FeedItemActions {

    private Activity feedsActivity;

    public FeedItemActions(Activity feedsActivity) {
        this.feedsActivity = feedsActivity;
    }

    public void loadUserImage(ImageView imageView, String url) {
        CommonMethods.loadImage(feedsActivity, imageView, url);
    }

    public void loadImage(ImageView imageView, String url) {
        CommonMethods.loadImage(feedsActivity, imageView, url);
    }

    public void displayFeedItemMenu(final View view, final View progressView, int position, final long id, final OnPostAction onPostAction) {

        PopupMenu popup = new PopupMenu(feedsActivity, view, position);
        popup.inflate(R.menu.feed_item);
        popup.show();
        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {

                if (item.getItemId() == R.id.action_report_abuse)
                {
                    progressView.setVisibility(View.VISIBLE);

                    FeedMethods.postActionReportAbuse(feedsActivity, id, new OnDatasetReceiveCallback<FeedItemDataset>() {
                        @Override
                        public void onDatasetReceive(FeedItemDataset dataset) {

                            progressView.setVisibility(View.GONE);
                            onPostAction.onSuccess(dataset);
                            CommonMethods.displayMessageLong(feedsActivity,feedsActivity.getString(R.string.txt_report_feed));
                        }

                        @Override
                        public void onFailureToReceiveDataset(String failure) {

                            progressView.setVisibility(View.GONE);
                            onPostAction.onFailure();
                        }

                        @Override
                        public void onInternetConnectionFailure() {

                            progressView.setVisibility(View.GONE);
                            onPostAction.onFailure();
                        }
                    });

                    return true;
                }
                return false;
            }
        });
    }

    public void likeFeed(long id, final OnPostAction<FeedItemDataset> onPostAction) {
        FeedMethods.postActionLike(feedsActivity, id, new OnDatasetReceiveCallback<FeedItemDataset>() {
            @Override
            public void onDatasetReceive(FeedItemDataset dataset) {
                onPostAction.onSuccess(dataset);
            }

            @Override
            public void onFailureToReceiveDataset(String failure) {
                onPostAction.onFailure();
            }

            @Override
            public void onInternetConnectionFailure() {
                onPostAction.onFailure();
            }
        });
    }

    public void dislikeFeed(long id, final OnPostAction<FeedItemDataset> onPostAction) {
        FeedMethods.postActionDisLike(feedsActivity, id, new OnDatasetReceiveCallback<FeedItemDataset>() {
            @Override
            public void onDatasetReceive(FeedItemDataset dataset) {
                onPostAction.onSuccess(dataset);
            }

            @Override
            public void onFailureToReceiveDataset(String failure) {
                onPostAction.onFailure();
            }

            @Override
            public void onInternetConnectionFailure() {
                onPostAction.onFailure();
            }
        });
    }

    public void likeFeedComment(long feedId, long feedCommentId, final OnPostAction<CommentItemDataset> onPostAction) {
        FeedMethods.postActionLikeComment(feedsActivity, feedId, feedCommentId, new OnDatasetReceiveCallback<CommentItemDataset>() {
            @Override
            public void onDatasetReceive(CommentItemDataset dataset) {
                onPostAction.onSuccess(dataset);
            }

            @Override
            public void onFailureToReceiveDataset(String failure) {
                onPostAction.onFailure();
            }

            @Override
            public void onInternetConnectionFailure() {
                onPostAction.onFailure();
            }
        });
    }

    public void dislikeFeedComment(long feedId, long feedCommentId, final OnPostAction<CommentItemDataset> onPostAction) {
        FeedMethods.postActionLikeComment(feedsActivity, feedId, feedCommentId, new OnDatasetReceiveCallback<CommentItemDataset>() {
            @Override
            public void onDatasetReceive(CommentItemDataset dataset) {
                onPostAction.onSuccess(dataset);
            }

            @Override
            public void onFailureToReceiveDataset(String failure) {
                onPostAction.onFailure();
            }

            @Override
            public void onInternetConnectionFailure() {
                onPostAction.onFailure();
            }
        });
    }

    public void showFeedDetails(FeedItemDataset feedItemDataset, int position) {
        Intent intent = new Intent(feedsActivity, FeedDetailActivity.class);
        intent.putExtra("feed_item_dataset",feedItemDataset);
        intent.putExtra("feed_item_position",position);
        feedsActivity.startActivityForResult(intent, 111);
    }


    public void showMoreText(Button btnShowMore, TextView feedTextView) {

        if(TextViewCompat.getMaxLines(feedTextView)==3)
        {
            feedTextView.setMaxLines(Integer.MAX_VALUE);
            btnShowMore.setText(feedsActivity.getString(R.string.txt_show_less));
        }
        else {
            feedTextView.setMaxLines(3);
            btnShowMore.setText(feedsActivity.getString(R.string.txt_show_more));
        }
    }

    public void openAlbum(int imagePosition, ArrayList<PostUploads> postUploads) {
        Intent intent = new Intent(feedsActivity, FeedImagesViewActivity.class);
        intent.putExtra("index",imagePosition);
        intent.putExtra("post_uploads",postUploads);
        feedsActivity.startActivity(intent);
    }

    public void showUserProfile(long id) {

        if (id == PrefData.getUserId()) {
            Intent intent = new Intent(feedsActivity, EditUserProfileActivity.class);
            feedsActivity.startActivityForResult(intent, 113);
        }
        else {
            Intent intent = new Intent(feedsActivity, ViewUserProfileActivity.class);
            intent.putExtra("id",id);
            feedsActivity.startActivity(intent);
        }
    }

    public void hideFeed() {
        CommonMethods.displayMessage(feedsActivity,"Hide feed");
    }

    public void reportFeed() {
        CommonMethods.displayMessage(feedsActivity,"Report feed");
    }

    public void onPressHtmlView(String url) {
        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
        feedsActivity.startActivity(browserIntent);
    }
}

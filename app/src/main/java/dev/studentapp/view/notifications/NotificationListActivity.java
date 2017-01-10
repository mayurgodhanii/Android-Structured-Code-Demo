package dev.studentapp.view.notifications;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.view.View;

import dev.studentapp.R;
import dev.studentapp.base.BaseAppCompatActivity;
import dev.studentapp.model.dataset.feedlisting.FeedItemDataset;
import dev.studentapp.model.dataset.notifications.NotificationsDataset;
import dev.studentapp.model.listners.OnDatasetReceiveCallback;
import dev.studentapp.model.methods.FeedMethods;
import dev.studentapp.model.methods.NotificationMethods;
import dev.studentapp.util.CommonMethods;
import dev.studentapp.view.component.EndlessRecyclerViewScrollListener;
import dev.studentapp.view.feeds.feeddetail.FeedDetailActivity;

public class NotificationListActivity extends BaseAppCompatActivity {

    private NotificationAdapter notificationAdapter;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private RecyclerView recyclerView;
//    private View mProgressView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification_list);

        // Show the Up button in the action bar.
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        mProgressView = findViewById(R.id.progress);

        //========= Setting up RecylerView - A Feed Listing screen ===========//
        recyclerView = (RecyclerView) findViewById(R.id.recycler_notification);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        // Add the scroll listener
        recyclerView.addOnScrollListener(new EndlessRecyclerViewScrollListener(linearLayoutManager) {
            @Override
            public void onLoadMore(int page, int totalItemsCount) {
                // Triggered only when new data needs to be appended to the list
                // Add whatever code is needed to append new items to the bottom of the list
                customLoadMoreDataFromApi(page+1);
            }
        });

        mSwipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipeRefreshLayout);
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                // Refresh items
                refreshItems();
            }
        });
        mSwipeRefreshLayout.setColorSchemeResources(
                R.color.colorAccent,
                R.color.colorPrimary,
                R.color.colorPrimaryDark);
        feedDataToRecylerView();
    //=================================================
    }

    private void feedDataToRecylerView() {

        mProgressView.setVisibility(View.VISIBLE);

        NotificationMethods.getNotification(this, 1, new OnDatasetReceiveCallback<NotificationsDataset>() {

            @Override
            public void onDatasetReceive(NotificationsDataset notificationsDataset) {
                notificationAdapter = new NotificationAdapter(NotificationListActivity.this, notificationsDataset);
                recyclerView.setAdapter(notificationAdapter);;

                mProgressView.setVisibility(View.GONE);
            }

            @Override
            public void onFailureToReceiveDataset(String failure) {
                CommonMethods.displayMessage(NotificationListActivity.this, failure);

                mProgressView.setVisibility(View.GONE);
            }

            @Override
            public void onInternetConnectionFailure() {
                CommonMethods.displayMessage(NotificationListActivity.this, getString(R.string.no_network_connection_toast));

                mProgressView.setVisibility(View.GONE);
            }
        });
    }

    private void addDataToRecylerView(int page) {

        if (page <= notificationAdapter.getTotalPageCount()) {

            mProgressView.setVisibility(View.VISIBLE);

            NotificationMethods.getNotification(this, page, new OnDatasetReceiveCallback<NotificationsDataset>() {

                @Override
                public void onDatasetReceive(NotificationsDataset notificationsDataset) {
                    notificationAdapter.newNotificationItemsAdded(notificationsDataset);

                    mProgressView.setVisibility(View.GONE);
                }

                @Override
                public void onFailureToReceiveDataset(String failure) {
                    CommonMethods.displayMessage(NotificationListActivity.this, failure);

                    mProgressView.setVisibility(View.GONE);
                }

                @Override
                public void onInternetConnectionFailure() {
                    CommonMethods.displayMessage(NotificationListActivity.this, getString(R.string.no_network_connection_toast));

                    mProgressView.setVisibility(View.GONE);
                }
            });
        }
    }

    void refreshItems() {

        NotificationMethods.getNotification(this, 1, new OnDatasetReceiveCallback<NotificationsDataset>() {

            @Override
            public void onDatasetReceive(NotificationsDataset notificationsDataset) {
                notificationAdapter = null;
                notificationAdapter = new NotificationAdapter(NotificationListActivity.this, notificationsDataset);
                recyclerView.setAdapter(notificationAdapter);
                notificationAdapter.notifyDataSetChanged();

                mSwipeRefreshLayout.setRefreshing(false);
            }

            @Override
            public void onFailureToReceiveDataset(String failure) {
                mSwipeRefreshLayout.setRefreshing(false);
            }

            @Override
            public void onInternetConnectionFailure() {
                mSwipeRefreshLayout.setRefreshing(false);
            }
        });
    }

    public void customLoadMoreDataFromApi(int page) {
        addDataToRecylerView(page);
    }


    public void redirectToFeedDetail(long postId) {

        mProgressView.setVisibility(View.VISIBLE);

        FeedMethods.getFeedDetail(this, postId, new OnDatasetReceiveCallback<FeedItemDataset>() {
            @Override
            public void onDatasetReceive(FeedItemDataset dataset) {
                mProgressView.setVisibility(View.GONE);

                Intent intent = new Intent(NotificationListActivity.this, FeedDetailActivity.class);
                intent.putExtra("feed_item_dataset",dataset);
//                intent.putExtra("feed_item_position",position);
                startActivity(intent);
            }

            @Override
            public void onFailureToReceiveDataset(String failure) {
                mProgressView.setVisibility(View.GONE);

                CommonMethods.displayMessage(NotificationListActivity.this, failure);
            }

            @Override
            public void onInternetConnectionFailure() {
                mProgressView.setVisibility(View.GONE);

                CommonMethods.displayMessage(NotificationListActivity.this, getString(R.string.no_network_connection_toast));
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            // This ID represents the Home or Up button. In the case of this
            // activity, the Up button is shown. Use NavUtils to allow users
            // to navigate up one level in the application structure. For
            // more details, see the Navigation pattern on Android Design:
            //
            // http://developer.android.com/design/patterns/navigation.html#up-vs-back
            //
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

}

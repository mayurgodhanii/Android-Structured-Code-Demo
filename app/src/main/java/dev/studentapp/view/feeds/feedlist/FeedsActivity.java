package dev.studentapp.view.feeds.feedlist;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.makeramen.roundedimageview.RoundedImageView;
import com.mikepenz.actionitembadge.library.ActionItemBadge;

import dev.studentapp.R;
import dev.studentapp.base.BaseAppCompatActivity;
import dev.studentapp.model.dataset.feedlisting.FeedDataset;
import dev.studentapp.model.dataset.feedlisting.FeedItemDataset;
import dev.studentapp.model.listners.OnDatasetReceiveCallback;
import dev.studentapp.model.methods.FeedMethods;
import dev.studentapp.util.CommonMethods;
import dev.studentapp.util.PrefData;
import dev.studentapp.view.component.EndlessRecyclerViewScrollListener;
import dev.studentapp.view.feeds.addfeed.AddFeedActivity;
import dev.studentapp.view.notifications.NotificationListActivity;
import dev.studentapp.view.startup.login.LoginActivity;
import dev.studentapp.view.userprofile.EditUserProfileActivity;

public class FeedsActivity extends BaseAppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private FeedItemRecyclerView feedItemRecyclerView;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private RecyclerView recyclerView;
//    private View mProgressView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feeds);

        setupToolbar();

        mProgressView = findViewById(R.id.progress);

//========= Setting up RecylerView - A Feed Listing screen ===========//
        recyclerView = (RecyclerView) findViewById(R.id.recycler_feeds);

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

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                CommonMethods.displayMessage(FeedsActivity.this,"Add feed");
                startActivityForResult(new Intent(FeedsActivity.this, AddFeedActivity.class),112);
            }
        });
    }

    private void setupToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        setNavUserData();
    }

    private void setNavUserData() {
        View headerLayout =
                ((NavigationView) findViewById(R.id.nav_view)).getHeaderView(0);
        RoundedImageView imageView = (RoundedImageView) headerLayout.findViewById(R.id.imageView);
        CommonMethods.loadImage(this, imageView, PrefData.getAvatarUrl());
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(FeedsActivity.this, EditUserProfileActivity.class);
                startActivityForResult(intent, 113);
            }
        });

        TextView txtName = (TextView) headerLayout.findViewById(R.id.txtName);
        txtName.setText(PrefData.getFirtName() + " " + PrefData.getLastName());
        TextView email = (TextView) headerLayout.findViewById(R.id.email);
        email.setText(PrefData.getEmail());
    }

    private void feedDataToRecylerView() {

        mProgressView.setVisibility(View.VISIBLE);

        FeedMethods.getCampusFeeds(this, 1, new OnDatasetReceiveCallback<FeedDataset>() {

            @Override
            public void onDatasetReceive(FeedDataset feedDataset) {
                feedItemRecyclerView = new FeedItemRecyclerView(new FeedItemActions(FeedsActivity.this), feedDataset);
                recyclerView.setAdapter(feedItemRecyclerView);;

                mProgressView.setVisibility(View.GONE);
            }

            @Override
            public void onFailureToReceiveDataset(String failure) {
                CommonMethods.displayMessage(FeedsActivity.this, failure);

                mProgressView.setVisibility(View.GONE);
            }

            @Override
            public void onInternetConnectionFailure() {
                CommonMethods.displayMessage(FeedsActivity.this, getString(R.string.no_network_connection_toast));

                mProgressView.setVisibility(View.GONE);
            }
        });
    }

    private void addDataToRecylerView(int page) {

        if (page <= feedItemRecyclerView.getTotalPageCount()) {

            mProgressView.setVisibility(View.VISIBLE);

            FeedMethods.getCampusFeeds(this, page, new OnDatasetReceiveCallback<FeedDataset>() {

                @Override
                public void onDatasetReceive(FeedDataset feedDataset) {
                    feedItemRecyclerView.newFeedItemsAdded(feedDataset);

                    mProgressView.setVisibility(View.GONE);
                }

                @Override
                public void onFailureToReceiveDataset(String failure) {
                    CommonMethods.displayMessage(FeedsActivity.this, failure);

                    mProgressView.setVisibility(View.GONE);
                }

                @Override
                public void onInternetConnectionFailure() {
                    CommonMethods.displayMessage(FeedsActivity.this, getString(R.string.no_network_connection_toast));

                    mProgressView.setVisibility(View.GONE);
                }
            });
        }
    }

    void refreshItems() {

        FeedMethods.getCampusFeeds(this, 1, new OnDatasetReceiveCallback<FeedDataset>() {

            @Override
            public void onDatasetReceive(FeedDataset feedDataset) {
                feedItemRecyclerView = null;
                feedItemRecyclerView = new FeedItemRecyclerView(new FeedItemActions(FeedsActivity.this), feedDataset);
                recyclerView.setAdapter(feedItemRecyclerView);
                feedItemRecyclerView.notifyDataSetChanged();

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

    private void invalidateRecyclerView() {
        if (feedItemRecyclerView!=null)
            feedItemRecyclerView.notifyDataSetChanged();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.feeds, menu);

        int badgeCount = 0;

        //you can add some logic (hide it if the count == 0)
//        if (badgeCount > 0) {
            ActionItemBadge.update(this, menu.findItem(R.id.action_badge), getResources().getDrawable(R.drawable.ic_notifications_white_24dp), ActionItemBadge.BadgeStyles.DARK_GREY, null);
//        } else {
//            ActionItemBadge.hide(menu.findItem(R.id.action_badge));
//        }

        //If you want to add your ActionItem programmatically you can do this too. You do the following:
//        new ActionItemBadgeAdder().act(this).menu(menu).title(R.string.sample_2).itemDetails(0, SAMPLE2_ID, 1).showAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS).add(bigStyle, 1);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_refresh) {
//            CommonMethods.displayMessage(this,"Refresh feeds");

            mSwipeRefreshLayout.setRefreshing(true);
            refreshItems();

            return true;
        }
        else if(id == R.id.action_show_recent) {

            return true;
        }
        else if(id == R.id.action_top_rated) {

            return true;
        }
        else if(id == R.id.action_badge) {

            startActivity(new Intent(this, NotificationListActivity.class));

            return true;
        }
        else if(id == R.id.action_logout) {

            PrefData.clearPrefData(this);

            Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            finish();

            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

//        if (id == R.id.nav_camera) {
//            // Handle the camera action
//        } else if (id == R.id.nav_gallery) {
//
//        } else if (id == R.id.nav_slideshow) {
//
//        } else if (id == R.id.nav_manage) {
//
//        } else if (id == R.id.nav_share) {
//
//        } else if (id == R.id.nav_send) {
//
//        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(resultCode==RESULT_OK && requestCode==111 && data.hasExtra("feed_item_dataset") && data.hasExtra("feed_item_position")) {

            FeedItemDataset feedItemDataset = (FeedItemDataset) data.getParcelableExtra("feed_item_dataset");
            int position = data.getIntExtra("feed_item_position",-1);
            if (position!=-1)
                feedItemRecyclerView.refreshFeedItemView(feedItemDataset, position);
        }
        else if (resultCode==RESULT_OK && requestCode==112 && data.hasExtra("feed_item_dataset")){

            FeedItemDataset feedItemDataset = (FeedItemDataset) data.getParcelableExtra("feed_item_dataset");
            feedItemRecyclerView.addFeedItemViewAtTop(feedItemDataset);
        }
        else if (resultCode==RESULT_OK && requestCode==113 && data.hasExtra("update_nav")) {
            setNavUserData();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}

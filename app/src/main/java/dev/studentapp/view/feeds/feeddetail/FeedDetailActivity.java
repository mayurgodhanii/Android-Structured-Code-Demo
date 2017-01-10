package dev.studentapp.view.feeds.feeddetail;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import dev.studentapp.R;
import dev.studentapp.base.BaseAppCompatActivity;
import dev.studentapp.model.dataset.comments.CommentDataset;
import dev.studentapp.model.dataset.comments.CommentItemDataset;
import dev.studentapp.model.dataset.feedlisting.FeedItemDataset;
import dev.studentapp.model.dataset.feedlisting.HTMLText;
import dev.studentapp.model.listners.OnDatasetReceiveCallback;
import dev.studentapp.model.methods.FeedMethods;
import dev.studentapp.util.CommonMethods;
import dev.studentapp.view.feeds.feedlist.BaseFeedItemViewHolder;
import dev.studentapp.view.feeds.feedlist.FeedItemActions;
import dev.studentapp.view.feeds.feedlist.FeedItemHtmlViewHolder;
import dev.studentapp.view.feeds.feedlist.FeedItemNormalViewHolder;
import dev.studentapp.view.feeds.feedlist.FeedItemPhotoViewHolder;
import dev.studentapp.view.feeds.feedlist.OnPostAction;

public class FeedDetailActivity extends BaseAppCompatActivity {

    private FeedItemDataset feedItemDataset;
    private int feedItemPosition;
    private FeedItemActions feedItemActions;

    private View mProgressView;

    private EditText edtComment;

    public RecyclerView recyclerView;

    private CommentRecyclerViewAdapter commentRecyclerViewAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feed_detail);

        feedItemDataset = (FeedItemDataset) getIntent().getParcelableExtra("feed_item_dataset");
        feedItemPosition = getIntent().getIntExtra("feed_item_position", -1);

        feedItemActions = new FeedItemActions(this);

        mProgressView = findViewById(R.id.progress);

        edtComment = (EditText) findViewById(R.id.edtComment);

        recyclerView = (RecyclerView) findViewById(R.id.rclComments);
        //== The most important line ==//
        recyclerView.setLayoutManager(new LinearLayoutManager(FeedDetailActivity.this));
        //=============================//

        if (feedItemDataset!=null) {
            setActionToolBar();

            setDetailViewcontainer();

            setCommentView();
        }
        else {
            CommonMethods.displayMessage(this,"Unexpected Error!");
            finish();
        }
    }

    private void setActionToolBar() {
        // Show the Up button in the action bar.
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }

    private void setDetailViewcontainer() {

        String type = feedItemDataset.getPostType();

        LayoutInflater li = LayoutInflater.from(this);

        View v;
        if(type.equalsIgnoreCase("url")) {
            v = li.inflate(R.layout.feed_item_html, null, false);
            setView(new FeedItemHtmlViewHolder(v));
        }
        else if(type.equalsIgnoreCase("images")) {
            v = li.inflate(R.layout.feed_item_photo, null, false);
            setView(new FeedItemPhotoViewHolder(v));
        }
        else {
            v = li.inflate(R.layout.feed_item_normal, null, false);
            setView(new FeedItemNormalViewHolder(v));
        }

        CardView container = (CardView) findViewById(R.id.container);

        for (int i = 0; i < container.getChildCount(); i++) {
            container.removeViewAt(i);
        }

        container.addView(v);
    }

    private void setView(FeedItemNormalViewHolder viewHolder) {
        setBaseView(viewHolder);
    }

    private void setView(FeedItemHtmlViewHolder feedItemHtmlViewHolder) {
        setBaseView(feedItemHtmlViewHolder);

        HTMLText htmlText = feedItemDataset.getHtmlText();
        if (htmlText.getTitle().isEmpty() && htmlText.getShortDesc().isEmpty())
        {
            feedItemHtmlViewHolder.getHtmlCardView().setVisibility(View.GONE);
        }
        else {

            feedItemHtmlViewHolder.getHtmlCardView().setVisibility(View.VISIBLE);

            String title = htmlText.getTitle();//"Skype | Free calls to friends and family";
            String decr = htmlText.getShortDesc();//"Download Skype and stay in touch with family and friends for free. Get international calling, free online calls and Skype for Business on desktop and mobile.";
            final String url = htmlText.getLink();//"https://www.skype.com/en/";
            String imageUrl = htmlText.getAsset();//"https://secure.skypeassets.com/i/common/images/icons/skype-logo-open-graph.png";

            feedItemHtmlViewHolder.getTxtTitle().setText(title);
            feedItemHtmlViewHolder.getTxtDesc().setText(decr);
            feedItemHtmlViewHolder.getTxtUrl().setText(url);

            if (!imageUrl.isEmpty()) {
                feedItemActions.loadImage(feedItemHtmlViewHolder.getImageView(), imageUrl);
            } else {
                feedItemHtmlViewHolder.getImageView().setVisibility(View.GONE);
            }

            feedItemHtmlViewHolder.getHtmlCardView().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    feedItemActions.onPressHtmlView(url);
                }
            });
        }
    }

    private void setView(FeedItemPhotoViewHolder feedItemPhotoViewHolder) {

        setBaseView(feedItemPhotoViewHolder);

        View.OnClickListener onImageOneClickListner = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                feedItemActions.openAlbum(0,feedItemDataset.getPostUploadsArrayList());
            }
        };

        View.OnClickListener onImageTwoClickListner = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                feedItemActions.openAlbum(1,feedItemDataset.getPostUploadsArrayList());
            }
        };

        View.OnClickListener onImageThreeClickListner = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                feedItemActions.openAlbum(2,feedItemDataset.getPostUploadsArrayList());
            }
        };

        if (feedItemDataset.getPostUploadsArrayList().size() >= 3) {
            feedItemPhotoViewHolder.getLytPhotoThree().setVisibility(View.VISIBLE);
            feedItemPhotoViewHolder.getLytPhotoSingle().setVisibility(View.GONE);
            feedItemPhotoViewHolder.getLytPhotoDouble().setVisibility(View.GONE);

            ImageView imageOne = (ImageView)feedItemPhotoViewHolder.getLytPhotoThree().findViewById(R.id.imageOne);
            imageOne.setOnClickListener(onImageOneClickListner);
            ImageView imageTwo = (ImageView)feedItemPhotoViewHolder.getLytPhotoThree().findViewById(R.id.imageTwo);
            imageTwo.setOnClickListener(onImageTwoClickListner);
            ImageView imageThree = (ImageView)feedItemPhotoViewHolder.getLytPhotoThree().findViewById(R.id.imageThree);
            imageThree.setOnClickListener(onImageThreeClickListner);

            feedItemActions.loadImage(imageOne,feedItemDataset.getPostUploadsArrayList().get(0).getThumbUrl());
            feedItemActions.loadImage(imageTwo,feedItemDataset.getPostUploadsArrayList().get(1).getThumbUrl());
            feedItemActions.loadImage(imageThree,feedItemDataset.getPostUploadsArrayList().get(2).getThumbUrl());

            Button button = (Button) feedItemPhotoViewHolder.getLytPhotoThree().findViewById(R.id.btnExtraImage);
            if (feedItemDataset.getPostUploadsArrayList().size() > 3) {
                button.setText("+"+(feedItemDataset.getPostUploadsArrayList().size()-3));
                button.setVisibility(View.VISIBLE);
            }
            else {
                button.setVisibility(View.GONE);
            }
        }
        else if(feedItemDataset.getPostUploadsArrayList().size()==2){
            feedItemPhotoViewHolder.getLytPhotoThree().setVisibility(View.GONE);
            feedItemPhotoViewHolder.getLytPhotoSingle().setVisibility(View.GONE);
            feedItemPhotoViewHolder.getLytPhotoDouble().setVisibility(View.VISIBLE);

            ImageView imageOne = (ImageView)feedItemPhotoViewHolder.getLytPhotoDouble().findViewById(R.id.imageOne);
            imageOne.setOnClickListener(onImageOneClickListner);
            ImageView imageTwo = (ImageView)feedItemPhotoViewHolder.getLytPhotoDouble().findViewById(R.id.imageTwo);
            imageTwo.setOnClickListener(onImageTwoClickListner);

            feedItemActions.loadImage(imageOne,feedItemDataset.getPostUploadsArrayList().get(0).getThumbUrl());
            feedItemActions.loadImage(imageTwo,feedItemDataset.getPostUploadsArrayList().get(1).getThumbUrl());
        }
        else if(feedItemDataset.getPostUploadsArrayList().size()==1){
            feedItemPhotoViewHolder.getLytPhotoThree().setVisibility(View.GONE);
            feedItemPhotoViewHolder.getLytPhotoSingle().setVisibility(View.VISIBLE);
            feedItemPhotoViewHolder.getLytPhotoDouble().setVisibility(View.GONE);

            ImageView imageOne = (ImageView)feedItemPhotoViewHolder.getLytPhotoSingle().findViewById(R.id.imageOne);
            imageOne.setOnClickListener(onImageOneClickListner);

            feedItemActions.loadImage(imageOne,feedItemDataset.getPostUploadsArrayList().get(0).getThumbUrl());
        }
        else  {
            feedItemPhotoViewHolder.getLytPhotoThree().setVisibility(View.GONE);
            feedItemPhotoViewHolder.getLytPhotoSingle().setVisibility(View.GONE);
            feedItemPhotoViewHolder.getLytPhotoDouble().setVisibility(View.GONE);
        }
    }

    private void setBaseView(final BaseFeedItemViewHolder baseFeedItemViewHolder) {

        baseFeedItemViewHolder.getmMenuButton().setVisibility(View.GONE);
        baseFeedItemViewHolder.getmBtnImgComment().setVisibility(View.GONE);
        baseFeedItemViewHolder.getmBtnSeeMore().setVisibility(View.GONE);

        feedItemActions.loadUserImage(baseFeedItemViewHolder.getmUserPic(), feedItemDataset.getPostBy().getAvatar());

        baseFeedItemViewHolder.getmTxtUsername().setText(feedItemDataset.getPostBy().getFullName());
        baseFeedItemViewHolder.getmTxtFeedCreated().setText(CommonMethods.getCreatedText(feedItemDataset.getCreated()));

        if (feedItemDataset.getLikeCount()==1) {
            baseFeedItemViewHolder.getmBtnTxtLike().setText(""+feedItemDataset.getLikeCount()+" Like");
        }
        else {
            baseFeedItemViewHolder.getmBtnTxtLike().setText(""+feedItemDataset.getLikeCount()+" Likes");
        }

        if (feedItemDataset.getUnlikeCount()==1) {
            baseFeedItemViewHolder.getmBtnTxtDislike().setText(""+feedItemDataset.getUnlikeCount()+" Dislike");
        }
        else {
            baseFeedItemViewHolder.getmBtnTxtDislike().setText(""+feedItemDataset.getUnlikeCount()+" Dislikes");
        }

        if (feedItemDataset.getCommentCount()==1) {
            baseFeedItemViewHolder.getmBtnTxtComment().setText(""+feedItemDataset.getCommentCount()+" Comment");
        }
        else {
            baseFeedItemViewHolder.getmBtnTxtComment().setText(""+feedItemDataset.getCommentCount()+" Comments");
        }

        if (feedItemDataset.getPostLikeByUser()==1) {
            baseFeedItemViewHolder.getmBtnImgLike().setImageResource(R.drawable.ic_thumb_up_green_24dp);
        }
        else {
            baseFeedItemViewHolder.getmBtnImgLike().setImageResource(R.drawable.ic_thumb_up_black_24dp);
        }

        if (feedItemDataset.getPostDislikeByUser()==1) {
            baseFeedItemViewHolder.getmBtnImgDislike().setImageResource(R.drawable.ic_thumb_down_green_24dp);
        }
        else {
            baseFeedItemViewHolder.getmBtnImgDislike().setImageResource(R.drawable.ic_thumb_down_black_24dp);
        }

        if (!feedItemDataset.getText().isEmpty()) {

            baseFeedItemViewHolder.getFeedText().setVisibility(View.VISIBLE);

            baseFeedItemViewHolder.getFeedText().setText(feedItemDataset.getText());

            baseFeedItemViewHolder.getFeedText().post(new Runnable() {
                @Override
                public void run() {
                    int lineCount = baseFeedItemViewHolder.getFeedText().getLineCount();
                    if (lineCount > 3) {
                        baseFeedItemViewHolder.getmBtnSeeMore().setVisibility(View.VISIBLE);
                    } else {
                        baseFeedItemViewHolder.getmBtnSeeMore().setVisibility(View.GONE);
                    }
                }
            });
        }
        else {
            baseFeedItemViewHolder.getFeedText().setVisibility(View.GONE);
            baseFeedItemViewHolder.getmBtnSeeMore().setVisibility(View.GONE);
        }

        baseFeedItemViewHolder.getmBtnImgLike().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                baseFeedItemViewHolder.getProgressView().setVisibility(View.VISIBLE);

                feedItemActions.likeFeed(feedItemDataset.getId(), new OnPostAction<FeedItemDataset>() {
                    @Override
                    public void onSuccess(FeedItemDataset feedItemDataset) {

                        baseFeedItemViewHolder.getProgressView().setVisibility(View.GONE);
                        refreshFeedItemView(feedItemDataset);
                    }

                    @Override
                    public void onFailure() {
                        baseFeedItemViewHolder.getProgressView().setVisibility(View.GONE);
                    }
                });
            }
        });

        baseFeedItemViewHolder.getmBtnImgDislike().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                baseFeedItemViewHolder.getProgressView().setVisibility(View.VISIBLE);

                feedItemActions.dislikeFeed(feedItemDataset.getId(), new OnPostAction<FeedItemDataset>() {
                    @Override
                    public void onSuccess(FeedItemDataset feedItemDataset) {

                        baseFeedItemViewHolder.getProgressView().setVisibility(View.GONE);
                        refreshFeedItemView(feedItemDataset);
                    }

                    @Override
                    public void onFailure() {
                        baseFeedItemViewHolder.getProgressView().setVisibility(View.GONE);
                    }
                });
            }
        });

        baseFeedItemViewHolder.getmLytUserSection().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                feedItemActions.showUserProfile(feedItemDataset.getPostBy().getId());
            }
        });

        baseFeedItemViewHolder.getmBtnSeeMore().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                feedItemActions.showMoreText((Button) v, baseFeedItemViewHolder.getFeedText());
            }
        });
    }

    private void refreshFeedItemView(FeedItemDataset feedItemDataset){
        this.feedItemDataset = feedItemDataset;
        setDetailViewcontainer();
    }

    private void setCommentView() {

        mProgressView.setVisibility(View.VISIBLE);

        FeedMethods.getFeedComments(this, feedItemDataset.getId(), new OnDatasetReceiveCallback<CommentDataset>() {
            @Override
            public void onDatasetReceive(CommentDataset dataset) {

                mProgressView.setVisibility(View.GONE);

                if (dataset.getCommentItemDatasetArrayList()!=null)
                {
                    commentRecyclerViewAdapter = new CommentRecyclerViewAdapter(FeedDetailActivity.this, dataset, feedItemDataset.getId());
                    recyclerView.setAdapter(commentRecyclerViewAdapter);

                    if(dataset.getCommentItemDatasetArrayList().size()==0) {
                        findViewById(R.id.txtNoComments).setVisibility(View.VISIBLE);
                        findViewById(R.id.rclComments).setVisibility(View.GONE);
                    }
                }
                else {
                    findViewById(R.id.txtNoComments).setVisibility(View.VISIBLE);
                    findViewById(R.id.rclComments).setVisibility(View.GONE);
                }

            }

            @Override
            public void onFailureToReceiveDataset(String failure) {
                mProgressView.setVisibility(View.GONE);
            }

            @Override
            public void onInternetConnectionFailure() {
                mProgressView.setVisibility(View.GONE);
            }
        });


    }

    public void addFeedComment(View view) {
        if(edtComment.getText().toString().isEmpty()){
            CommonMethods.displayMessage(this, getString(R.string.txt_enter_comment));
        }
        else {
            mProgressView.setVisibility(View.VISIBLE);
            FeedMethods.addFeedComment(this, feedItemDataset.getId(), edtComment.getText().toString(), new OnDatasetReceiveCallback<CommentItemDataset>() {
                @Override
                public void onDatasetReceive(CommentItemDataset dataset) {

                    edtComment.setText("");

                    if (commentRecyclerViewAdapter!=null) {
                        commentRecyclerViewAdapter.addItem(dataset);

                        //==== Temp Fix/Solution for displaying first time comment ====
                        findViewById(R.id.txtNoComments).setVisibility(View.GONE);
                        findViewById(R.id.rclComments).setVisibility(View.VISIBLE);
                        //==============
                    }

                    // Check if no view has focus:
                    View view = FeedDetailActivity.this.getCurrentFocus();
                    if (view != null) {
                        InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
                    }

                    FeedMethods.getFeedDetail(FeedDetailActivity.this, feedItemDataset.getId(), new OnDatasetReceiveCallback<FeedItemDataset>() {
                        @Override
                        public void onDatasetReceive(FeedItemDataset dataset) {
                            mProgressView.setVisibility(View.GONE);

                            refreshFeedItemView(dataset);
                        }

                        @Override
                        public void onFailureToReceiveDataset(String failure) {
                            mProgressView.setVisibility(View.GONE);
                        }

                        @Override
                        public void onInternetConnectionFailure() {
                            mProgressView.setVisibility(View.GONE);
                        }
                    });
                }

                @Override
                public void onFailureToReceiveDataset(String failure) {
                    mProgressView.setVisibility(View.GONE);
                    CommonMethods.displayMessage(getApplicationContext(), failure);
                }

                @Override
                public void onInternetConnectionFailure() {
                    mProgressView.setVisibility(View.GONE);
                    CommonMethods.displayMessage(getApplicationContext(), getString(R.string.no_network_connection_toast));
                }
            });
        }
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

            finishAct();

            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        // your code.
        finishAct();
    }

    private void finishAct(){
        Intent data = new Intent();
        data.putExtra("feed_item_dataset",feedItemDataset);
        data.putExtra("feed_item_position",feedItemPosition);

        if (getParent() == null) {
            setResult(Activity.RESULT_OK, data);
        } else {
            getParent().setResult(Activity.RESULT_OK, data);
        }
        finish();
    }
}

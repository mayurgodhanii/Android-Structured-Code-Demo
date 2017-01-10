package dev.studentapp.view.feeds.feedlist;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import java.util.ArrayList;

import dev.studentapp.R;
import dev.studentapp.model.dataset.feedlisting.FeedDataset;
import dev.studentapp.model.dataset.feedlisting.FeedItemDataset;
import dev.studentapp.model.dataset.feedlisting.HTMLText;
import dev.studentapp.model.dataset.feedlisting.PagingDataset;
import dev.studentapp.util.CommonMethods;

public class FeedItemRecyclerView extends RecyclerView.Adapter<RecyclerView.ViewHolder>  {

    private FeedItemActions feedItemActions;
    private ArrayList<FeedItemDataset> feedItemDatasetArrayList;
    private PagingDataset pagingDataset;

    public static final int FEED_TYPE_NORMAL = 1;
    public static final int FEED_TYPE_PHOTO = 2;
    public static final int FEED_TYPE_HTML = 3;

    public FeedItemRecyclerView(FeedItemActions feedItemActions, FeedDataset feedDataset) {
        this.feedItemActions = feedItemActions;
        this.feedItemDatasetArrayList = feedDataset.getFeedItemDatasetArrayList();
        this.pagingDataset = feedDataset.getPagingDataset();
    }

    @Override
    public long getItemId(int position) {
        return super.getItemId(position);

    }

    @Override
    public int getItemViewType(int position) {

        String postType = feedItemDatasetArrayList.get(position).getPostType();

        if (postType.equalsIgnoreCase("url"))
            return FEED_TYPE_HTML;
        else if (postType.equalsIgnoreCase("images"))
            return FEED_TYPE_PHOTO;
        else
            return FEED_TYPE_NORMAL;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {

        RecyclerView.ViewHolder viewHolder;

        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());

        View view;

        switch (viewType) {
            case FEED_TYPE_HTML:
                view = inflater.inflate(R.layout.feed_item_html, viewGroup, false);
                viewHolder = new FeedItemHtmlViewHolder(view);
                break;
            case FEED_TYPE_PHOTO:
                view = inflater.inflate(R.layout.feed_item_photo, viewGroup, false);
                viewHolder = new FeedItemPhotoViewHolder(view);
                break;
            default:
                view = inflater.inflate(R.layout.feed_item_normal, viewGroup, false);
                viewHolder = new FeedItemNormalViewHolder(view);
                break;
        }

        return viewHolder;

    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int position) {

        configureBaseFeedItemViewHolder((BaseFeedItemViewHolder) viewHolder, feedItemDatasetArrayList.get(position), position);

        switch (viewHolder.getItemViewType()) {
            case FEED_TYPE_HTML:
                configureFeedItemViewHolder((FeedItemHtmlViewHolder) viewHolder, feedItemDatasetArrayList.get(position), position);
                break;
            case FEED_TYPE_PHOTO:
                configureFeedItemViewHolder((FeedItemPhotoViewHolder) viewHolder, feedItemDatasetArrayList.get(position), position);
                break;
            default:
                configureFeedItemViewHolder((FeedItemNormalViewHolder) viewHolder, feedItemDatasetArrayList.get(position), position);
                break;
        }

    }

    @Override
    public int getItemCount() {
        return feedItemDatasetArrayList.size();
    }

    public long getTotalPageCount() {
        return pagingDataset.getTotalPageCount();
    }

    public long getCurrentPageIndex() {
        return pagingDataset.getCurrentPageIndex();
    }

    private void configureBaseFeedItemViewHolder(final BaseFeedItemViewHolder baseFeedItemViewHolder, final FeedItemDataset feedItemDataset, final int position) {

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

        baseFeedItemViewHolder.getmMenuButton().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                feedItemActions.displayFeedItemMenu(v, baseFeedItemViewHolder.getProgressView(), position, feedItemDataset.getId(), new OnPostAction<FeedItemDataset>() {
                    @Override
                    public void onSuccess(FeedItemDataset feedItemDataset) {
//                       removeFeedItemView(position);
                    }

                    @Override
                    public void onFailure() {

                    }
                });
            }
        });

        baseFeedItemViewHolder.getmBtnImgLike().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                baseFeedItemViewHolder.getProgressView().setVisibility(View.VISIBLE);

                feedItemActions.likeFeed(feedItemDataset.getId(), new OnPostAction<FeedItemDataset>() {
                    @Override
                    public void onSuccess(FeedItemDataset feedItemDataset) {

                        baseFeedItemViewHolder.getProgressView().setVisibility(View.GONE);

                        refreshFeedItemView(feedItemDataset, position);
                    }

                    @Override
                    public void onFailure() {
                        baseFeedItemViewHolder.getProgressView().setVisibility(View.GONE);
                    }
                });
            }
        });

        baseFeedItemViewHolder.getmBtnTxtLike().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

//                baseFeedItemViewHolder.getProgressView().setVisibility(View.VISIBLE);
//
//                feedItemActions.likeFeed(feedItemDataset.getId(), new OnPostAction<FeedItemDataset>() {
//                    @Override
//                    public void onSuccess(FeedItemDataset feedItemDataset) {
//
//                        baseFeedItemViewHolder.getProgressView().setVisibility(View.GONE);
//
//                        refreshFeedItemView(feedItemDataset, position);
//                    }
//
//                    @Override
//                    public void onFailure() {
//                        baseFeedItemViewHolder.getProgressView().setVisibility(View.GONE);
//                    }
//                });
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

                        refreshFeedItemView(feedItemDataset, position);
                    }

                    @Override
                    public void onFailure() {
                        baseFeedItemViewHolder.getProgressView().setVisibility(View.GONE);
                    }
                });
            }
        });

        baseFeedItemViewHolder.getmBtnTxtDislike().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

//                baseFeedItemViewHolder.getProgressView().setVisibility(View.VISIBLE);
//
//                feedItemActions.dislikeFeed(feedItemDataset.getId(), new OnPostAction<FeedItemDataset>() {
//                    @Override
//                    public void onSuccess(FeedItemDataset feedItemDataset) {
//
//                        baseFeedItemViewHolder.getProgressView().setVisibility(View.GONE);
//
//                        refreshFeedItemView(feedItemDataset, position);
//                    }
//
//                    @Override
//                    public void onFailure() {
//                        baseFeedItemViewHolder.getProgressView().setVisibility(View.GONE);
//                    }
//                });
            }
        });

        baseFeedItemViewHolder.getmBtnImgComment().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                feedItemActions.showFeedDetails(feedItemDataset, position);
            }
        });

        baseFeedItemViewHolder.getmBtnTxtComment().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                feedItemActions.showFeedDetails(feedItemDataset, position);
            }
        });

        baseFeedItemViewHolder.getmBtnSeeMore().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                feedItemActions.showMoreText((Button) v, baseFeedItemViewHolder.getFeedText());
            }
        });

        baseFeedItemViewHolder.getmLytUserSection().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                feedItemActions.showUserProfile(feedItemDataset.getPostBy().getId());
            }
        });

        baseFeedItemViewHolder.getmTopView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                feedItemActions.showFeedDetails(feedItemDataset, position);
            }
        });
    }

    private void configureFeedItemViewHolder(FeedItemNormalViewHolder feedItemNormalViewHolder,  FeedItemDataset feedItemDataset, final int position) {

    }

    private void configureFeedItemViewHolder(FeedItemPhotoViewHolder feedItemPhotoViewHolder, final FeedItemDataset feedItemDataset, final int position) {

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

    private void configureFeedItemViewHolder(final FeedItemHtmlViewHolder feedItemHtmlViewHolder,  FeedItemDataset feedItemDataset, int position) {

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

    public void addFeedItemViewAtTop(FeedItemDataset feedItemDataset){

        feedItemDatasetArrayList.add(0, feedItemDataset);

        notifyItemInserted(0);
        notifyItemRangeChanged(0, feedItemDatasetArrayList.size());

        notifyDataSetChanged();
    }

    public void removeFeedItemView(int position){

        feedItemDatasetArrayList.remove(position);

        notifyItemRemoved(position);
        notifyItemRangeChanged(position, feedItemDatasetArrayList.size());
    }

    public void newFeedItemsAdded(FeedDataset feedDataset) {

        pagingDataset = feedDataset.getPagingDataset();
        feedItemDatasetArrayList.addAll(feedDataset.getFeedItemDatasetArrayList());

        notifyDataSetChanged();
    }

    public void refreshFeedItemView(FeedItemDataset feedItemDataset, int position){

        feedItemDatasetArrayList.remove(position);
        feedItemDatasetArrayList.add(position, feedItemDataset);

        notifyItemChanged(position);
    }
}
package dev.studentapp.view.feeds.feeddetail;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.makeramen.roundedimageview.RoundedImageView;

import java.util.ArrayList;

import dev.studentapp.R;
import dev.studentapp.model.dataset.comments.CommentDataset;
import dev.studentapp.model.dataset.comments.CommentItemDataset;
import dev.studentapp.model.dataset.comments.PagingDataset;
import dev.studentapp.util.CommonMethods;
import dev.studentapp.view.feeds.feedlist.FeedItemActions;
import dev.studentapp.view.feeds.feedlist.OnPostAction;

public class CommentRecyclerViewAdapter
            extends RecyclerView.Adapter<CommentRecyclerViewAdapter.ViewHolder> {

        FeedItemActions feedItemActions;
        ArrayList<CommentItemDataset> commentItemDatasetArrayList;
        PagingDataset pagingDataset;
        FeedDetailActivity feedDetailActivity;
        long feedId;

        public CommentRecyclerViewAdapter(FeedDetailActivity activity, CommentDataset commentDataset, long feedId) {
            feedItemActions = new FeedItemActions(activity);
            commentItemDatasetArrayList = commentDataset.getCommentItemDatasetArrayList();
            pagingDataset = commentDataset.getPagingDataset();
            feedDetailActivity = activity;
            this.feedId = feedId;
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.feed_item_comment_view, parent, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(final ViewHolder holder, final int position) {

            final CommentItemDataset commentItemDataset = commentItemDatasetArrayList.get(position);

            feedItemActions.loadUserImage(holder.getmUserPic(), commentItemDataset.getUser().getAvatar());

            holder.getFeedText().setText(commentItemDataset.getText());

            holder.getFeedText().post(new Runnable() {
                @Override
                public void run() {
                    int lineCount = holder.getFeedText().getLineCount();
                    if(lineCount > 3) {
                        holder.getmBtnSeeMore().setVisibility(View.VISIBLE);
                    }
                    else {
                        holder.getmBtnSeeMore().setVisibility(View.GONE);
                    }
                }
            });

            holder.getTxtUsername().setText(commentItemDataset.getUser().getFullName());
            holder.getmBtnTxtLike().setText(commentItemDataset.getLikeCount()+"");
            holder.getmBtnTxtDislike().setText(commentItemDataset.getUnLikeCount()+"");

            if (commentItemDataset.isCommentLikeByUser())
                holder.getmBtnTxtLike().setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_thumb_up_green_24dp, 0, 0, 0);
            else
                holder.getmBtnTxtLike().setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_thumb_up_black_24dp, 0, 0, 0);


            if (commentItemDataset.isCommentDisLikeByUser())
                holder.getmBtnTxtDislike().setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_thumb_down_green_24dp, 0, 0, 0);
            else
                holder.getmBtnTxtDislike().setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_thumb_down_black_24dp, 0, 0, 0);


            holder.getTxtTimestamp().setText(CommonMethods.getCreatedText(commentItemDataset.getCreated()));

            holder.getmBtnSeeMore().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    feedItemActions.showMoreText((Button) v, holder.getFeedText());
                }
            });

            holder.getmBtnTxtLike().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    holder.getmProgressView().setVisibility(View.VISIBLE);

                    feedItemActions.likeFeedComment(feedId, commentItemDataset.getId(), new OnPostAction<CommentItemDataset>() {
                        @Override
                        public void onSuccess(CommentItemDataset commentItemDataset) {

                            holder.getmProgressView().setVisibility(View.GONE);

                            commentItemDatasetArrayList.remove(position);
                            commentItemDatasetArrayList.add(position, commentItemDataset);

                            notifyItemChanged(position);
                        }

                        @Override
                        public void onFailure() {
                            holder.getmProgressView().setVisibility(View.GONE);
                        }
                    });
                }
            });

            holder.getmUserPic().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    feedItemActions.showUserProfile(commentItemDataset.getUser().getId());
                }
            });

            holder.getTxtUsername().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    feedItemActions.showUserProfile(commentItemDataset.getUser().getId());
                }
            });
        }



        @Override
        public int getItemCount() {
            return commentItemDatasetArrayList.size();
        }

        public void addItem(CommentItemDataset dataset) {
            commentItemDatasetArrayList.add(dataset);
            notifyItemInserted(commentItemDatasetArrayList.size()==0?0:commentItemDatasetArrayList.size()-1);
        }

        public class ViewHolder extends RecyclerView.ViewHolder {

            private View mView;
            private RoundedImageView mUserPic;

            private Button mBtnTxtLike;
            private Button mBtnTxtDislike;

            private TextView feedText;
            private Button mBtnSeeMore;

            private TextView txtUsername;
            private TextView txtTimestamp;

            private View mProgressView;

            public ViewHolder(View itemView) {
                super(itemView);

                setmView(itemView);

                setmUserPic((RoundedImageView) mView.findViewById(R.id.roundImageView_user_pic));

                setmBtnTxtLike((Button) mView.findViewById(R.id.btn_img_like));
                setmBtnTxtDislike((Button) mView.findViewById(R.id.btn_img_dislike));

                setFeedText((TextView) mView.findViewById(R.id.txt_feed));
                setmBtnSeeMore((Button) mView.findViewById(R.id.btn_see_more));

                setTxtUsername((TextView) mView.findViewById(R.id.txt_username));
                setTxtTimestamp((TextView) mView.findViewById(R.id.txt_feed_created));

                setmProgressView(mView.findViewById(R.id.progress));
            }

            public RoundedImageView getmUserPic() {
                return mUserPic;
            }

            public void setmUserPic(RoundedImageView mUserPic) {
                this.mUserPic = mUserPic;
            }

            public TextView getFeedText() {
                return feedText;
            }

            public void setFeedText(TextView feedText) {
                this.feedText = feedText;
            }

            public Button getmBtnTxtLike() {
                return mBtnTxtLike;
            }

            public void setmBtnTxtLike(Button mBtnTxtLike) {
                this.mBtnTxtLike = mBtnTxtLike;
            }

            public Button getmBtnTxtDislike() {
                return mBtnTxtDislike;
            }

            public void setmBtnTxtDislike(Button mBtnTxtDislike) {
                this.mBtnTxtDislike = mBtnTxtDislike;
            }

            public Button getmBtnSeeMore() {
                return mBtnSeeMore;
            }

            public void setmBtnSeeMore(Button mBtnSeeMore) {
                this.mBtnSeeMore = mBtnSeeMore;
            }

            public void setmView(View mView){
                this.mView = mView;
            }

            public View getmView() {
                return mView;
            }

            public TextView getTxtUsername() {
                return txtUsername;
            }

            public void setTxtUsername(TextView txtUsername) {
                this.txtUsername = txtUsername;
            }

            public TextView getTxtTimestamp() {
                return txtTimestamp;
            }

            public void setTxtTimestamp(TextView txtTimestamp) {
                this.txtTimestamp = txtTimestamp;
            }

            public View getmProgressView() {
                return mProgressView;
            }

            public void setmProgressView(View mProgressView) {
                this.mProgressView = mProgressView;
            }
    }
}
package dev.studentapp.view.feeds.feedlist;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.makeramen.roundedimageview.RoundedImageView;

import dev.studentapp.R;

/**
 * Created by Nirav Dangi on 30/06/16.
 */
public class BaseFeedItemViewHolder extends RecyclerView.ViewHolder {

    private ImageButton mMenuButton;
    private RoundedImageView mUserPic;
    private TextView mTxtUsername;
    private TextView mTxtFeedCreated;

    private Button mBtnTxtLike;
    private Button mBtnTxtDislike;
    private Button mBtnTxtComment;
    private ImageButton mBtnImgLike;
    private ImageButton mBtnImgDislike;
    private ImageButton mBtnImgComment;

    private TextView feedText;
    private Button mBtnSeeMore;

    LinearLayout mLytUserSection;

    LinearLayout mTopView;

    private View progressView;

    public BaseFeedItemViewHolder(View view) {
        super(view);

        setmMenuButton((ImageButton) view.findViewById(R.id.imgBtn_menu));
        setmUserPic((RoundedImageView) view.findViewById(R.id.roundImageView_user_pic));
        setmTxtUsername((TextView) view.findViewById(R.id.txt_username));
        setmTxtFeedCreated((TextView) view.findViewById(R.id.txt_feed_created));

        setmBtnTxtLike((Button) view.findViewById(R.id.btn_txt_like));
        setmBtnTxtDislike((Button) view.findViewById(R.id.btn_txt_dislike));
        setmBtnTxtComment((Button) view.findViewById(R.id.btn_txt_comment));
        setmBtnImgLike((ImageButton) view.findViewById(R.id.btn_img_like));
        setmBtnImgDislike((ImageButton) view.findViewById(R.id.btn_img_dislike));
        setmBtnImgComment((ImageButton) view.findViewById(R.id.btn_img_comment));

        setFeedText((TextView) view.findViewById(R.id.txt_feed));
        setmBtnSeeMore((Button) view.findViewById(R.id.btn_see_more));

        setmLytUserSection((LinearLayout) view.findViewById(R.id.lyt_user_section));
        setmTopView((LinearLayout) view.findViewById(R.id.topView));

        setProgressView(view.findViewById(R.id.progress));
    }

    public ImageButton getmMenuButton() {
        return mMenuButton;
    }

    public void setmMenuButton(ImageButton mMenuButton) {
        this.mMenuButton = mMenuButton;
    }

    public RoundedImageView getmUserPic() {
        return mUserPic;
    }

    public void setmUserPic(RoundedImageView mUserPic) {
        this.mUserPic = mUserPic;
    }

    public TextView getmTxtUsername() {
        return mTxtUsername;
    }

    public void setmTxtUsername(TextView mTxtUsername) {
        this.mTxtUsername = mTxtUsername;
    }

    public TextView getmTxtFeedCreated() {
        return mTxtFeedCreated;
    }

    public void setmTxtFeedCreated(TextView mTxtFeedCreated) {
        this.mTxtFeedCreated = mTxtFeedCreated;
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

    public Button getmBtnTxtComment() {
        return mBtnTxtComment;
    }

    public void setmBtnTxtComment(Button mBtnTxtComment) {
        this.mBtnTxtComment = mBtnTxtComment;
    }

    public ImageButton getmBtnImgLike() {
        return mBtnImgLike;
    }

    public void setmBtnImgLike(ImageButton mBtnImgLike) {
        this.mBtnImgLike = mBtnImgLike;
    }

    public ImageButton getmBtnImgDislike() {
        return mBtnImgDislike;
    }

    public void setmBtnImgDislike(ImageButton mBtnImgDislike) {
        this.mBtnImgDislike = mBtnImgDislike;
    }

    public ImageButton getmBtnImgComment() {
        return mBtnImgComment;
    }

    public void setmBtnImgComment(ImageButton mBtnImgComment) {
        this.mBtnImgComment = mBtnImgComment;
    }

    public Button getmBtnSeeMore() {
        return mBtnSeeMore;
    }

    public void setmBtnSeeMore(Button mBtnSeeMore) {
        this.mBtnSeeMore = mBtnSeeMore;
    }

    public LinearLayout getmLytUserSection() {
        return mLytUserSection;
    }

    public void setmLytUserSection(LinearLayout mLytUserSection) {
        this.mLytUserSection = mLytUserSection;
    }

    public LinearLayout getmTopView() {
        return mTopView;
    }

    public void setmTopView(LinearLayout mTopView) {
        this.mTopView = mTopView;
    }

    public View getProgressView() {
        return progressView;
    }

    public void setProgressView(View progressView) {
        this.progressView = progressView;
    }
}

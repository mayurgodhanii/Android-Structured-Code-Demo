package dev.studentapp.view.feeds.feedlist;

import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import dev.studentapp.R;

public class FeedItemHtmlViewHolder extends BaseFeedItemViewHolder {

    private CardView htmlCardView;
    private TextView txtTitle;
    private TextView txtDesc;
    private TextView txtUrl;
    private ImageView imageView;

    public FeedItemHtmlViewHolder(View view) {
        super(view);

        setHtmlCardView((CardView) view.findViewById(R.id.htmlCardView));
        setTxtTitle((TextView) view.findViewById(R.id.txtTitle));
        setTxtDesc((TextView) view.findViewById(R.id.txtDecr));
        setTxtUrl((TextView) view.findViewById(R.id.txtUrl));
        setImageView((ImageView) view.findViewById(R.id.imageView));
    }

    public CardView getHtmlCardView() {
        return htmlCardView;
    }

    public void setHtmlCardView(CardView htmlCardView) {
        this.htmlCardView = htmlCardView;
    }

    public TextView getTxtTitle() {
        return txtTitle;
    }

    public void setTxtTitle(TextView txtTitle) {
        this.txtTitle = txtTitle;
    }

    public TextView getTxtDesc() {
        return txtDesc;
    }

    public void setTxtDesc(TextView txtDesc) {
        this.txtDesc = txtDesc;
    }

    public TextView getTxtUrl() {
        return txtUrl;
    }

    public void setTxtUrl(TextView txtUrl) {
        this.txtUrl = txtUrl;
    }

    public ImageView getImageView() {
        return imageView;
    }

    public void setImageView(ImageView imageView) {
        this.imageView = imageView;
    }
}
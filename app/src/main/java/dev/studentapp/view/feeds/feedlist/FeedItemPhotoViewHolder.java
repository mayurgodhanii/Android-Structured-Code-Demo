package dev.studentapp.view.feeds.feedlist;

import android.view.View;
import android.widget.LinearLayout;

import dev.studentapp.R;

public class FeedItemPhotoViewHolder extends BaseFeedItemViewHolder {

    private LinearLayout lytPhotoSingle;
    private LinearLayout lytPhotoDouble;
    private LinearLayout lytPhotoThree;

    public FeedItemPhotoViewHolder(View view) {
        super(view);

        setLytPhotoSingle((LinearLayout) view.findViewById(R.id.lyt_photo_single));
        setLytPhotoDouble((LinearLayout) view.findViewById(R.id.lyt_photo_double));
        setLytPhotoThree((LinearLayout) view.findViewById(R.id.lyt_photo_three));
    }

    public LinearLayout getLytPhotoSingle() {
        return lytPhotoSingle;
    }

    public void setLytPhotoSingle(LinearLayout lytPhotoSingle) {
        this.lytPhotoSingle = lytPhotoSingle;
    }

    public LinearLayout getLytPhotoDouble() {
        return lytPhotoDouble;
    }

    public void setLytPhotoDouble(LinearLayout lytPhotoDouble) {
        this.lytPhotoDouble = lytPhotoDouble;
    }

    public LinearLayout getLytPhotoThree() {
        return lytPhotoThree;
    }

    public void setLytPhotoThree(LinearLayout lytPhotoThree) {
        this.lytPhotoThree = lytPhotoThree;
    }
}
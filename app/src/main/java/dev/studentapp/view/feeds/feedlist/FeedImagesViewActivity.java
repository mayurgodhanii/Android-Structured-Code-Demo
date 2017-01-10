package dev.studentapp.view.feeds.feedlist;

import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v7.app.ActionBar;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import java.util.ArrayList;

import dev.studentapp.R;
import dev.studentapp.base.BaseAppCompatActivity;
import dev.studentapp.model.dataset.feedlisting.PostUploads;
import dev.studentapp.util.CommonMethods;
import dev.studentapp.view.component.photosviewpager.ExtendedViewPager;
import dev.studentapp.view.component.photosviewpager.TouchImageView;

public class FeedImagesViewActivity extends BaseAppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feed_images_view);

        setupToolbar();
    }

    private void setupToolbar() {

        // Show the Up button in the action bar.
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setTitle("FEEDS");
        }

        ArrayList<PostUploads> postUploadses = new ArrayList<PostUploads>();

        if(getIntent().hasExtra("post_uploads")) {
            postUploadses = getIntent().getParcelableArrayListExtra("post_uploads");
            if (postUploadses==null)
                postUploadses = new ArrayList<PostUploads>();
        }

        ExtendedViewPager mViewPager = (ExtendedViewPager) findViewById(R.id.view_pager);
        mViewPager.setAdapter(new TouchImageAdapter(postUploadses));

        if(getIntent().hasExtra("index")) {
            mViewPager.setCurrentItem(getIntent().getIntExtra("index",0));
        }
    }

    static class TouchImageAdapter extends PagerAdapter {

        ArrayList<PostUploads> postUploadses;

        public TouchImageAdapter(ArrayList<PostUploads> postUploadses){
            this.postUploadses = postUploadses;
        }

        @Override
        public int getCount() {
            return postUploadses.size();
        }

        @Override
        public View instantiateItem(ViewGroup container, int position) {
            TouchImageView img = new TouchImageView(container.getContext());
//            img.setImageResource(images[position]);
            CommonMethods.loadImageHD(container.getContext(), img, postUploadses.get(position).getName());
            container.addView(img, LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
            return img;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
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
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}

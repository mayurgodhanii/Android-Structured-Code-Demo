package dev.studentapp.view.feeds.addfeed;

import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.util.Linkify;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import com.leocardz.link.preview.library.LinkPreviewCallback;
import com.leocardz.link.preview.library.SourceContent;
import com.leocardz.link.preview.library.TextCrawler;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import dev.studentapp.R;
import dev.studentapp.base.BaseAppCompatActivity;
import dev.studentapp.model.dataset.feedlisting.FeedItemDataset;
import dev.studentapp.model.listners.JSONResponseCallback;
import dev.studentapp.model.listners.OnDatasetReceiveCallback;
import dev.studentapp.model.methods.FeedMethods;
import dev.studentapp.util.CommonMethods;
import dev.studentapp.util.Log;
import dev.studentapp.view.component.button.ButtonOpenSansBold;

/**
 * Created by Nirav Dangi on 25/07/16.
 */
public class AddFeedActivity extends BaseAppCompatActivity{

    private EditText edtFeedText;
    private EditText edtShareLink;

    private String htmlMetaTitle = "";
    private String htmlMetaDecr = "";
    private String htmlMetaUrl = "";
    private String htmlMetaImageUrl = "";

    private ArrayList<File> filePathAssets = new ArrayList<File>();

    private GalleryPhotosViewAdapter adapter;

    private View mProgressView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_feed);

        mProgressView = findViewById(R.id.progress);

        setActionToolBar();

        setupRecyclerView();

        //====
        edtFeedText = (EditText) findViewById(R.id.editText);
        edtShareLink = (EditText) findViewById(R.id.edtShareLink);

        Linkify.addLinks(edtFeedText, Linkify.WEB_URLS);
        Linkify.addLinks(edtShareLink, Linkify.WEB_URLS);

        hidePreview();

        edtShareLink.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            private Timer timer=new Timer();
            private final long DELAY = 500; // milliseconds

            @Override
            public void afterTextChanged(final Editable s) {

                timer.cancel();
                timer = new Timer();
                timer.schedule(
                        new TimerTask() {
                            @Override
                            public void run() {
                                // TODO: do what you need here (refresh list)
                                // you will probably need to use runOnUiThread(Runnable action) for some specific actions
                                AddFeedActivity.this.runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        Log.d(s.toString());
                                        List<String> urls = CommonMethods.extractUrls(s.toString());
                                        if (urls != null && urls.size() > 0) {
                                            showPreview();
                                            Log.d("Url : " + urls.get(urls.size()-1));
                                            String urlToLoad = urls.get(urls.size()-1);

                                            if(urlToLoad.contains("http://") || urlToLoad.contains("https://"))
                                                makePreview(urls.get(urls.size()-1));
                                            else
                                                makePreview("http://"+urls.get(urls.size()-1));
                                        }
                                        else {
                                            hidePreview();
                                        }
                                    }
                                });
                            }
                        },
                        DELAY
                );
            }
        });

        //====
    }

    private void setupRecyclerView() {

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.rclPhotos);
        adapter = new GalleryPhotosViewAdapter();
        recyclerView.setAdapter(adapter);

        StaggeredGridLayoutManager staggeredGridLayoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(staggeredGridLayoutManager);
    }

    public class GalleryPhotosViewAdapter
            extends RecyclerView.Adapter<GalleryPhotosViewAdapter.ViewHolder> {

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.add_feed_recycler_photo_view, parent, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(final ViewHolder holder, final int position) {

            loadBitmap(holder.imageView, filePathAssets.get(position).getPath());
            holder.btnRemove.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    removeAt(position);
                }
            });
        }

        @Override
        public int getItemCount() {
            return filePathAssets.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder {

            public View mView;
            public ImageButton btnRemove;
            public ImageView imageView;

            public ViewHolder(View view) {
                super(view);

                mView = view;
                btnRemove = (ImageButton) view.findViewById(R.id.btnRemove);
                imageView = (ImageView) view.findViewById(R.id.imageView);
            }
        }

        public void removeAt(int position) {

            filePathAssets.remove(position);
            notifyItemRemoved(position);
            notifyItemRangeChanged(position, filePathAssets.size());
        }

        public void loadBitmap(ImageView imageView, String imagePath) {
            CommonMethods.loadBitmap(imageView, imagePath, 150, 150);
        }
    }

    private void setActionToolBar() {

        // Show the Up button in the action bar.
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }

    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.add_feed_menu_items, menu);

        // Get the root inflator.
        LayoutInflater baseInflater = (LayoutInflater)getBaseContext()
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        // Inflate your custom view.
        ButtonOpenSansBold button = new ButtonOpenSansBold(this);
        android.app.ActionBar.LayoutParams params = new android.app.ActionBar.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        button.setLayoutParams(params);
        button.setTextColor(Color.WHITE);
        button.setText("POST");
        button.setBackgroundColor(Color.TRANSPARENT);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addFeed();
            }
        });

        menu.findItem(R.id.post_feed).setActionView(button);

        return true;
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

    private int PICK_IMAGE_REQUEST = 1;

    public void openGallery(View v) {

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.

            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 1);

            return;
        }

        Intent intent = new Intent(Intent.ACTION_PICK,android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, PICK_IMAGE_REQUEST);
    }

    public void openLink(View v) {


    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {

            Uri uri = data.getData();

            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);

                if(bitmap!=null) {
                    filePathAssets.add(new File(CommonMethods.getPath(this, uri)));
                    adapter.notifyDataSetChanged();
                }
            } catch (Exception e) {
                e.printStackTrace();
                CommonMethods.displayMessage(this, getString(R.string.error_select_image));
            }

//**************** Another way to grap bitmap *****************//

//            Uri selectedImage = data.getData();
//            String[] filePathColumn = { MediaStore.Images.Media.DATA };
//            Cursor cursor = getContentResolver().query(selectedImage,
//                    filePathColumn, null, null, null);
//
//            if (cursor == null || cursor.getCount() < 1) {
//                return; // no cursor or no record. DO YOUR ERROR HANDLING
//            }
//
//            cursor.moveToFirst();
//            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
//
//            if(columnIndex < 0) // no column index
//                return; // DO YOUR ERROR HANDLING
//
//            String picturePath = cursor.getString(columnIndex);
//
//            cursor.close(); // close cursor
//
//            assets.add(BitmapFactory.decodeFile(picturePath.toString()));
//            filePathAssets.add(new File(picturePath));
//
//            adapter.notifyDataSetChanged();
        }
    }

    void makePreview(String url){

        new TextCrawler().makePreview(new LinkPreviewCallback() {

            @Override
            public void onPre() {
                ((ProgressBar) findViewById(R.id.progressBar)).setVisibility(View.VISIBLE);
            }

            @Override
            public void onPos(final SourceContent sourceContent, boolean b) {

                if (b || sourceContent.getFinalUrl().equals("")) {
                    hidePreview();
                }
                else {

                    ((ProgressBar) findViewById(R.id.progressBar)).setVisibility(View.GONE);

                    htmlMetaTitle = (sourceContent.getTitle()!=null && !sourceContent.getTitle().isEmpty())?sourceContent.getTitle():sourceContent.getFinalUrl();
                    htmlMetaDecr = (sourceContent.getDescription()!=null && !sourceContent.getDescription().isEmpty())?sourceContent.getDescription():"";
                    htmlMetaUrl = sourceContent.getFinalUrl();
                    htmlMetaImageUrl = (sourceContent.getImages()!=null && sourceContent.getImages().size() > 0)?sourceContent.getImages().get(0):"";


                    ((TextView) findViewById(R.id.txtTitle)).setText(htmlMetaTitle);
                    ((TextView) findViewById(R.id.txtDecr)).setText(htmlMetaDecr);
                    ((TextView) findViewById(R.id.txtUrl)).setText(htmlMetaUrl);

                    if(!htmlMetaImageUrl.isEmpty()) {

                        ((ImageView) findViewById(R.id.imageView)).setVisibility(View.VISIBLE);

                        Glide.with(getApplicationContext())
                                .load(htmlMetaImageUrl)
                                .centerCrop()
                                .crossFade()
                                .into((ImageView) findViewById(R.id.imageView));
                    }
                    else {
                        ((ImageView) findViewById(R.id.imageView)).setVisibility(View.GONE);
                    }
                }
            }
        },url);
    }

    void hidePreview() {

        htmlMetaTitle = "";
        htmlMetaDecr = "";
        htmlMetaUrl = "";
        htmlMetaImageUrl = "";

        ((CardView) findViewById(R.id.htmlCardView)).setVisibility(View.GONE);
    }

    void showPreview() {
        ((CardView) findViewById(R.id.htmlCardView)).setVisibility(View.VISIBLE);
    }


    private void addFeed(){
        if (filePathAssets.size() > 0) {
            uploadPhotos();
        }
        else {
            postFeed();
        }
    }

    private void uploadPhotos(){
        uploadPhoto(filePathAssets.size()-1);
    }

    private ArrayList<String> uploadedPhotosName;

    ProgressDialog mProgressDialog;

    private void uploadPhoto(final int position) {

        mProgressDialog = null;
        mProgressDialog = new ProgressDialog(AddFeedActivity.this);
        mProgressDialog.setMessage("Uploading image "+((filePathAssets.size()-position))+" of "+ filePathAssets.size());
        mProgressDialog.setIndeterminate(true);
        mProgressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        mProgressDialog.setCancelable(false);
        mProgressDialog.show();

        if(uploadedPhotosName==null)
            uploadedPhotosName = new ArrayList<String>();

        FeedMethods.uploadPhoto(this, filePathAssets.get(position), new OnDatasetReceiveCallback<String>() {
            @Override
            public void onDatasetReceive(String filename) {

                    mProgressDialog.dismiss();

                    uploadedPhotosName.add(filename);

                    if (position>0) {
                        uploadPhoto(position-1);
                    }
                    else {
                        postFeed();
                    }
            }

            @Override
            public void onFailureToReceiveDataset(String failure) {
                    mProgressDialog.dismiss();
                    CommonMethods.displayMessage(AddFeedActivity.this, failure);
            }

            @Override
            public void onInternetConnectionFailure() {
                    mProgressDialog.dismiss();
                    CommonMethods.displayMessage(AddFeedActivity.this, getString(R.string.no_network_connection_toast));
            }
        });
    }

    private void postFeed() {
        String feedText = edtFeedText.getText().toString();
        String shareLink = edtShareLink.getText().toString();
        if (feedText.isEmpty() && shareLink.isEmpty() && (uploadedPhotosName==null || uploadedPhotosName.size()==0)) {
            edtFeedText.setError(getString(R.string.enter_feed_text));
            return;
        }

        String categoryId = "1"; // taxon_id

        mProgressView.setVisibility(View.VISIBLE);
        FeedMethods.addFeed(this, feedText, categoryId,
                null, uploadedPhotosName,
                htmlMetaTitle.isEmpty()?null:htmlMetaTitle, htmlMetaDecr.isEmpty()?null:htmlMetaDecr, htmlMetaUrl.isEmpty()?null:htmlMetaUrl, htmlMetaImageUrl.isEmpty()?null:htmlMetaImageUrl,
                new JSONResponseCallback() {
                    @Override
                    public void onSuccess(String message, String jsonResponse) {

                        CommonMethods.displayMessage(AddFeedActivity.this, message);

                        try {

                            long feedId = new JSONObject(jsonResponse).getJSONObject("data").getLong("post_id");

                            FeedMethods.getFeedDetail(getApplicationContext(), feedId, new OnDatasetReceiveCallback<FeedItemDataset>() {
                                @Override
                                public void onDatasetReceive(FeedItemDataset dataset) {
                                    mProgressView.setVisibility(View.GONE);

                                    Intent data = new Intent();
                                    data.putExtra("feed_item_dataset",dataset);

                                    if (getParent() == null) {
                                        setResult(Activity.RESULT_OK, data);
                                    } else {
                                        getParent().setResult(Activity.RESULT_OK, data);
                                    }
                                    finish();
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

                        } catch (JSONException e) {
                            e.printStackTrace();
                            mProgressView.setVisibility(View.GONE);
                            finish();
                        }
                    }

                    @Override
                    public void onFailure(String message) {
                        mProgressView.setVisibility(View.GONE);
                        CommonMethods.displayMessage(AddFeedActivity.this, message);
                    }

                    @Override
                    public void onInternetConnectionFailure() {
                        mProgressView.setVisibility(View.GONE);
                        CommonMethods.displayMessage(AddFeedActivity.this, getString(R.string.no_network_connection_toast));
                    }
        });
    }
}

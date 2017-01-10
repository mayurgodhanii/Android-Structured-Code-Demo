package dev.studentapp.view.userprofile;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.makeramen.roundedimageview.RoundedImageView;

import dev.studentapp.R;
import dev.studentapp.base.BaseAppCompatActivity;
import dev.studentapp.model.dataset.viewprofile.ViewProfileDataset;
import dev.studentapp.model.listners.OnDatasetReceiveCallback;
import dev.studentapp.model.methods.ProfileMethods;
import dev.studentapp.util.CommonMethods;
import dev.studentapp.util.PrefData;

public class ViewUserProfileActivity extends BaseAppCompatActivity {

//    FlowLayout chipsBoxLayout;
//
//    ArrayList<String> selectedTag = new ArrayList<String>();

    private TextView txtName;
    private TextView txtType;
    private EditText edtAbout;
    private EditText edtOrg;
    private EditText edtClass;

    private RoundedImageView roundedImageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_user_profile);

        // Show the Up button in the action bar.
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        mProgressView = findViewById(R.id.progress);

        txtName = (TextView) findViewById(R.id.txtName);
        txtType = (TextView) findViewById(R.id.txtType);
        edtAbout = (EditText) findViewById(R.id.edtAbout);
        edtOrg = (EditText) findViewById(R.id.edtOrg);
        edtClass = (EditText) findViewById(R.id.edtClass);

        roundedImageView = (RoundedImageView) findViewById(R.id.imgUser);

        setUserData();

//        chipsBoxLayout = (FlowLayout)findViewById(R.id.chips_box_layout);
//
//        selectedTag.add("Android");
//        selectedTag.add("Graph Theory");
//        selectedTag.add("Virtual Reality");
//        selectedTag.add("Mathamatical");
//        selectedTag.add("Spanish");
//        selectedTag.add("WWDC");
//        selectedTag.add("Swift");
//
//        setupChipView(selectedTag);
    }

    private void setUserData() {

        mProgressView.setVisibility(View.VISIBLE);

        ProfileMethods.viewProfile(this, getIntent().getLongExtra("id", -1), new OnDatasetReceiveCallback<ViewProfileDataset>() {
            @Override
            public void onDatasetReceive(ViewProfileDataset dataset) {

                mProgressView.setVisibility(View.GONE);

                txtName.setText(dataset.getFirtName() + " " + dataset.getLastName());

                txtType.setText(dataset.getProfileType());
//        edtPhone.setText(dataset.get());
//                edtAbout.setText(dataset.getSummary());
//        edtOrg.setText(dataset.get());
//        edtClass.setText(dataset.get());

                CommonMethods.loadImage(ViewUserProfileActivity.this,roundedImageView,PrefData.getAvatarUrl());
            }

            @Override
            public void onFailureToReceiveDataset(String failure) {

                mProgressView.setVisibility(View.GONE);

                if (failure==null || failure.isEmpty())
                    CommonMethods.displayMessage(getApplicationContext(), "Sorry, we are unable to fetch user profile. Please try later!");
                else
                    CommonMethods.displayMessage(getApplicationContext(), failure);

                finish();
            }

            @Override
            public void onInternetConnectionFailure() {

                mProgressView.setVisibility(View.GONE);

                CommonMethods.displayMessage(getApplicationContext(), "No Internet Connection Found !");

                finish();
            }
        });


    }

//    private void setupChipView(ArrayList<String> selectedData) {
//
//        chipsBoxLayout.removeAllViews();
//
//        for (int i = 0; i < selectedData.size(); i++) {
//
//            chipsBoxLayout.addView(getChipView(selectedData.get(i)));
//        }
//    }
//
//    private View getChipView(final String text) {
//
//        LayoutInflater inflater = (LayoutInflater)getApplicationContext().getSystemService
//                (Context.LAYOUT_INFLATER_SERVICE);
//        final CardView view = (CardView) inflater.inflate(R.layout.chipview,null);
//        FlowLayout.LayoutParams params = new FlowLayout.LayoutParams(FlowLayout.LayoutParams.WRAP_CONTENT, FlowLayout.LayoutParams.WRAP_CONTENT);
//        params.setMargins(10,10,10,10);
//        view.setLayoutParams(params);
//        view.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDark));
//
//        TextView txtText = (TextView) view.findViewById(R.id.text);
//        txtText.setText(text);
//
//        view.findViewById(R.id.btnClose).setVisibility(View.GONE);
//
//
//        return view;
//    }

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

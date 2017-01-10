package dev.studentapp.view.userprofile;

import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.ActionBar;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import com.makeramen.roundedimageview.RoundedImageView;

import java.io.File;

import dev.studentapp.R;
import dev.studentapp.base.BaseAppCompatActivity;
import dev.studentapp.model.dataset.login.LoginDataset;
import dev.studentapp.model.listners.OnDatasetReceiveCallback;
import dev.studentapp.model.methods.ProfileMethods;
import dev.studentapp.util.CommonMethods;
import dev.studentapp.util.PrefData;
import dev.studentapp.view.feeds.feedlist.FeedsActivity;

public class EditUserProfileActivity extends BaseAppCompatActivity {

//    FlowLayout chipsBoxLayout;
//
//    ArrayList<String> selectedTag = new ArrayList<String>();
//    final ArrayList<String> tags = new ArrayList<String>();

    private EditText edtUsername;
    private EditText edtFirstname;
    private EditText edtLastname;
    private EditText edtEmail;
    private EditText edtPhone;
    private EditText edtAbout;
    private EditText edtOrg;
    private EditText edtClass;

    private RoundedImageView roundedImageView;

    private File imageToUpload;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_user_profile);

        // Show the Up button in the action bar.
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        mProgressView = findViewById(R.id.progress);

        edtUsername = (EditText) findViewById(R.id.edtUsername);
        edtFirstname = (EditText) findViewById(R.id.edtFirstname);
        edtLastname = (EditText) findViewById(R.id.edtLastname);
        edtEmail = (EditText) findViewById(R.id.edtEmail);
        edtPhone = (EditText) findViewById(R.id.edtPhone);
        edtAbout = (EditText) findViewById(R.id.edtAbout);
        edtOrg = (EditText) findViewById(R.id.edtOrg);
        edtClass = (EditText) findViewById(R.id.edtClass);

        roundedImageView = (RoundedImageView) findViewById(R.id.imgUser);

        roundedImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openGallery();
            }
        });

        setUserData();

//        chipsBoxLayout = (FlowLayout)findViewById(R.id.chips_box_layout);
//
//        setupChipView(selectedTag);
    }

    private void setUserData() {
        edtUsername.setText(PrefData.getUsername());
        edtFirstname.setText(PrefData.getFirtName());
        edtLastname.setText(PrefData.getLastName());
        edtEmail.setText(PrefData.getEmail());
//        edtPhone.setText(PrefData.get());
        edtAbout.setText(PrefData.getSummary());
//        edtOrg.setText(PrefData.get());
//        edtClass.setText(PrefData.get());

        CommonMethods.loadImage(this,roundedImageView,PrefData.getAvatarUrl());
    }

//    public void showSelectTagPopup(View view) {
//
//        if (tags.size()==0) {
//            tags.add("Android");
//            tags.add("Graph Theory");
//            tags.add("Virtual Reality");
//            tags.add("Mathamatical");
//            tags.add("Spanish");
//            tags.add("WWDC");
//            tags.add("Material Design");
//            tags.add("Google Place API");
//            tags.add("REST Api");
//            tags.add("SOAP");
//            tags.add("BIG DATA");
//        }
//
//        String _items[] = tags.toArray(new String[tags.size()]);
//        boolean[] mSelection = new boolean[_items.length];
//
//        for (int i = 0; i < selectedTag.size(); i++) {
//
//            for (int j = 0; j < tags.size(); j++) {
//                if (selectedTag.get(i).equalsIgnoreCase(tags.get(j)))
//                {
//                    mSelection[j] = true;
//                    break;
//                }
//            }
//        }
//
//
//        final AlertDialog.Builder builder = new AlertDialog.Builder(EditUserProfileActivity.this);
//        builder.setMultiChoiceItems(_items, mSelection, new DialogInterface.OnMultiChoiceClickListener() {
//            @Override
//            public void onClick(DialogInterface dialog, int which, boolean isChecked) {
//                if (isChecked)
//                    selectedTag.add(tags.get(which));
//                else
//                    selectedTag.remove(tags.get(which));
//            }
//        });
//
//        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface arg0, int arg1)
//            {
//                setupChipView(selectedTag);
//            }
//        });
//
//        builder.setOnCancelListener(new DialogInterface.OnCancelListener() {
//            @Override
//            public void onCancel(DialogInterface dialog) {
//                setupChipView(selectedTag);
//            }
//        });
//
//        builder.show();
//    }
//
//    private void setupChipView(ArrayList<String> selectedData) {
//
//        if (selectedTag.size() > 0) {
//
//            chipsBoxLayout.setVisibility(View.VISIBLE);
//            ((TextView) findViewById(R.id.txtAddTopics)).setVisibility(View.GONE);
//
//            chipsBoxLayout.removeAllViews();
//
//            for (int i = 0; i < selectedData.size(); i++) {
//
//                chipsBoxLayout.addView(getChipView(selectedData.get(i)));
//            }
//        }
//        else {
//            chipsBoxLayout.setVisibility(View.GONE);
//            ((TextView) findViewById(R.id.txtAddTopics)).setVisibility(View.VISIBLE);
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
//        Button btnClose = (Button) view.findViewById(R.id.btnClose);
//        btnClose.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                selectedTag.remove(text);
//                chipsBoxLayout.removeView(view);
//
//                if (selectedTag.size() == 0) {
//                    chipsBoxLayout.setVisibility(View.GONE);
//                    ((TextView) findViewById(R.id.txtAddTopics)).setVisibility(View.VISIBLE);
//                }
//            }
//        });
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
            screenRedirect();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

//    public boolean onCreateOptionsMenu(Menu menu) {
//
//        MenuInflater inflater = getMenuInflater();
//        inflater.inflate(R.menu.edit_profile_menu, menu);
//
//        // Get the root inflator.
//        LayoutInflater baseInflater = (LayoutInflater)getBaseContext()
//                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//
//        // Inflate your custom view.
//        ButtonOpenSansBold button = new ButtonOpenSansBold(this);
//        android.app.ActionBar.LayoutParams params = new android.app.ActionBar.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
//        button.setLayoutParams(params);
//        button.setTextColor(Color.WHITE);
//        button.setText("SKIP");
//        button.setBackgroundColor(Color.TRANSPARENT);
//        button.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                screenRedirect();
//            }
//        });
//
//        menu.findItem(R.id.skip).setActionView(button);
//
//        return true;
//    }

    private int PICK_IMAGE_REQUEST = 1;

    public void openGallery() {

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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {

            Uri uri = data.getData();

            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);

                if(bitmap!=null) {
                    imageToUpload = new File(CommonMethods.getPath(this, uri));

                    if (imageToUpload==null)
                        CommonMethods.displayMessage(this, getString(R.string.error_select_image));
                    else {
                        CommonMethods.loadBitmap(roundedImageView, new File(CommonMethods.getPath(this, uri)).getPath(), 250, 250);
                    }
                }

            } catch (Exception e) {
                e.printStackTrace();
                CommonMethods.displayMessage(this, getString(R.string.error_select_image));
            }
        }
    }


    private void uploadPhotoAndSaveProfileData() {

        final ProgressDialog mProgressDialog = new ProgressDialog(EditUserProfileActivity.this);
        mProgressDialog.setMessage("Uploading image..");
        mProgressDialog.setIndeterminate(true);
        mProgressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        mProgressDialog.setCancelable(false);
        mProgressDialog.show();

        ProfileMethods.uploadPhoto(this, PrefData.getUserId(), imageToUpload, new OnDatasetReceiveCallback<String>() {
            @Override
            public void onDatasetReceive(String filename) {

                CommonMethods.displayMessage(EditUserProfileActivity.this, filename);
                mProgressDialog.dismiss();

                saveProfileData();
            }

            @Override
            public void onFailureToReceiveDataset(String failure) {
                mProgressDialog.dismiss();
                CommonMethods.displayMessage(EditUserProfileActivity.this, failure);
            }

            @Override
            public void onInternetConnectionFailure() {
                mProgressDialog.dismiss();
                CommonMethods.displayMessage(EditUserProfileActivity.this, getString(R.string.no_network_connection_toast));
            }
        });
    }

    private void screenRedirect() {

        boolean flag = getIntent().getBooleanExtra("from_registration", false);

        if (flag)
            startActivity(new Intent(this, FeedsActivity.class));
        else {
            Intent data = new Intent();
            data.putExtra("update_nav",true);

            if (getParent() == null) {
                setResult(Activity.RESULT_OK, data);
            } else {
                getParent().setResult(Activity.RESULT_OK, data);
            }
        }

        finish();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        screenRedirect();
    }

    public void onEditProfile(View v) {

        if (edtFirstname.getText().toString().isEmpty()) {
            edtFirstname.setError(getResources().getString(R.string.error_field_required));
            return;
        }
        else {
            edtFirstname.setError(null);
        }

        if (edtLastname.getText().toString().isEmpty()) {
            edtLastname.setError(getResources().getString(R.string.error_field_required));
            return;
        }
        else {
            edtLastname.setError(null);
        }

        if (edtUsername.getText().toString().isEmpty()) {
            edtUsername.setError(getResources().getString(R.string.error_field_required));
            return;
        }
        else {
            edtUsername.setError(null);
        }

//        if (imageToUpload==null) {
            saveProfileData();
//        }
//        else {
//            uploadPhotoAndSaveProfileData();
//        }
    }

    private void saveProfileData() {

        mProgressView.setVisibility(View.VISIBLE);

        ProfileMethods.updateProfile(this, PrefData.getUserId() + "", edtFirstname.getText().toString(), edtLastname.getText().toString(),
                PrefData.getProfileType(), edtUsername.getText().toString(), edtAbout.getText().toString(), new OnDatasetReceiveCallback<LoginDataset>() {
                    @Override
                    public void onDatasetReceive(LoginDataset dataset) {

                        mProgressView.setVisibility(View.GONE);

                        PrefData.setUserData(getApplicationContext(), dataset);

                        screenRedirect();

                        CommonMethods.displayMessage(getApplicationContext(), "Your profile has been updated!");
                    }

                    @Override
                    public void onFailureToReceiveDataset(String failure) {

                        mProgressView.setVisibility(View.GONE);

                        if (failure==null || failure.isEmpty())
                            CommonMethods.displayMessage(getApplicationContext(), "Sorry, we are unable to update your profile. Please try later!");
                        else
                            CommonMethods.displayMessage(getApplicationContext(), failure);
                    }

                    @Override
                    public void onInternetConnectionFailure() {

                        mProgressView.setVisibility(View.GONE);

                        CommonMethods.displayMessage(getApplicationContext(), "No Internet Connection Found !");
                    }
                });
    }

    public void openChangePassword(View view) {
        startActivity(new Intent(this, ChangePasswordActivity.class));
    }
}

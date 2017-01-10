package dev.studentapp.view.userprofile;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import dev.studentapp.R;
import dev.studentapp.base.BaseAppCompatActivity;
import dev.studentapp.model.listners.OnDatasetReceiveCallback;
import dev.studentapp.model.methods.ProfileMethods;
import dev.studentapp.util.CommonMethods;
import dev.studentapp.util.PrefData;

public class ChangePasswordActivity extends BaseAppCompatActivity {

    private EditText edtEmail;
    private EditText edtOldPaswword;
    private EditText edtNewPaswword;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        mProgressView = findViewById(R.id.progress);

        edtEmail = (EditText) findViewById(R.id.edtEmail);
        edtEmail.setText(PrefData.getEmail());

        edtOldPaswword = (EditText) findViewById(R.id.edtOldPaswword);
        edtNewPaswword = (EditText) findViewById(R.id.edtNewPaswword);

        checkPasswordValidation();
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

    private void checkPasswordValidation() {

        if (edtNewPaswword!=null) {
            edtNewPaswword.addTextChangedListener(new TextWatcher() {

                public void afterTextChanged(Editable s) {
                    // other stuffs

                }

                public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                    // other stuffs
                }

                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    // other stuffs
                    if (s.toString().length() < 6) {
                        edtNewPaswword.setError("Minimum six character are required");
                    } else {
                        edtNewPaswword.setError(null);
                    }

                }
            });
        }
    }

    public void onChangePassword(View v) {
        if (edtOldPaswword.getText().toString().isEmpty()) {
            edtOldPaswword.setError(getResources().getString(R.string.error_field_required));
            return;
        }
        else {
            edtOldPaswword.setError(null);
        }

        if (edtNewPaswword.getText().toString().isEmpty()) {
            edtNewPaswword.setError(getResources().getString(R.string.error_field_required));
            return;
        }
        else {
            edtNewPaswword.setError(null);
        }

        mProgressView.setVisibility(View.VISIBLE);

        ProfileMethods.resetPassword(this, PrefData.getAccessToken(), PrefData.getEmail(), edtOldPaswword.getText().toString(), edtNewPaswword.getText().toString(), new OnDatasetReceiveCallback<String>() {
            @Override
            public void onDatasetReceive(String dataset) {
                mProgressView.setVisibility(View.GONE);

                CommonMethods.displayMessage(ChangePasswordActivity.this, dataset);

                finish();
            }

            @Override
            public void onFailureToReceiveDataset(String failure) {
                mProgressView.setVisibility(View.GONE);
                CommonMethods.displayMessage(ChangePasswordActivity.this, failure);
            }

            @Override
            public void onInternetConnectionFailure() {
                mProgressView.setVisibility(View.GONE);
                CommonMethods.displayMessage(ChangePasswordActivity.this, "No Internet Connection Found !");
            }
        });
    }
}

package dev.studentapp.view.startup.login;

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
import dev.studentapp.model.methods.LoginMethods;
import dev.studentapp.util.CommonMethods;

public class ForgotPasswordActivity extends BaseAppCompatActivity {

    EditText edtEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);

        // Show the Up button in the action bar.
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }


        mProgressView = findViewById(R.id.progress);

        edtEmail = (EditText) findViewById(R.id.edtEmail);

        checkEmailValidation();
    }

    private void checkEmailValidation() {

        if (edtEmail!=null) {
            edtEmail.addTextChangedListener(new TextWatcher() {

                public void afterTextChanged(Editable s) {
                    // other stuffs

                }

                public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                    // other stuffs
                }

                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    // other stuffs
                    if (android.util.Patterns.EMAIL_ADDRESS.matcher(s.toString()).matches()) {
                        edtEmail.setError(null);
                    } else {
                        edtEmail.setError("Invalid email address");
                    }
                }
            });
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

    public void onRequestForPassword(View view) {

        if (!edtEmail.getText().toString().isEmpty()) {

            mProgressView.setVisibility(View.VISIBLE);

            LoginMethods.forgotPassword(this, edtEmail.getText().toString(), new OnDatasetReceiveCallback<String>() {
                @Override
                public void onDatasetReceive(String dataset) {
                    mProgressView.setVisibility(View.GONE);
                    CommonMethods.displayMessage(ForgotPasswordActivity.this, dataset);
                    finish();
                }

                @Override
                public void onFailureToReceiveDataset(String failure) {
                    mProgressView.setVisibility(View.GONE);
                    CommonMethods.displayMessage(ForgotPasswordActivity.this, failure);
                }

                @Override
                public void onInternetConnectionFailure() {
                    mProgressView.setVisibility(View.GONE);
                    CommonMethods.displayMessage(ForgotPasswordActivity.this, "No Internet connection");
                }
            });
        }
        else {
            edtEmail.setError(getResources().getString(R.string.error_field_required));
        }
    }
}

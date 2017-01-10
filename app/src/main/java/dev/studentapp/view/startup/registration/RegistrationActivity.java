package dev.studentapp.view.startup.registration;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;

import com.crashlytics.android.Crashlytics;

import dev.studentapp.R;
import dev.studentapp.base.BaseAppCompatActivity;
import dev.studentapp.model.dataset.login.LoginDataset;
import dev.studentapp.model.listners.OnDatasetReceiveCallback;
import dev.studentapp.model.methods.LoginMethods;
import dev.studentapp.util.CommonMethods;
import dev.studentapp.util.PrefData;
import dev.studentapp.view.startup.login.LoginActivity;
import dev.studentapp.view.userprofile.EditUserProfileActivity;

public class RegistrationActivity extends BaseAppCompatActivity {

    private EditText edtFirstname;
    private EditText edtLastname;
    private EditText edtEmail;
    private EditText edtPassword;
    private EditText edtPhone;
//    private EditText edtConfrimPassword;
    private RadioButton rdtStudent;
    private RadioButton rdtMentor;
    private EditText edtPlatform;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        // Show the Up button in the action bar.
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        mProgressView = findViewById(R.id.progress);

        edtFirstname = (EditText) findViewById(R.id.edtFirstname);
        edtLastname = (EditText) findViewById(R.id.edtLastname);
        edtEmail = (EditText) findViewById(R.id.edtEmail);
        edtPassword = (EditText) findViewById(R.id.edtPassword);
        edtPhone = (EditText) findViewById(R.id.edtPhone);
//        edtConfrimPassword = (EditText) findViewById(R.id.edtConfrimPassword);
        rdtStudent = (RadioButton) findViewById(R.id.rdtStudent);
        rdtMentor = (RadioButton) findViewById(R.id.rdtMentor);
        edtPlatform = (EditText) findViewById(R.id.edtPlatform);

        checkEmailValidation();

        checkPasswordValidation();
    }

    private boolean checkNotNullValidation(EditText editText) {

        if (editText!=null && !editText.getText().toString().isEmpty()) {
            editText.setError(null);
            return true;
        }

        editText.setError("This field can't be empty");

        return false;
    }

    private void checkPasswordValidation() {

        if (edtPassword!=null) {
            edtPassword.addTextChangedListener(new TextWatcher() {

                public void afterTextChanged(Editable s) {
                    // other stuffs

                }

                public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                    // other stuffs
                }

                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    // other stuffs
                    if (s.toString().length() < 6) {
                        edtPassword.setError("Minimum six character are required");
                    } else {
                        edtPassword.setError(null);
                    }

                }
            });
        }
    }

    private void checkEmailValidation() {

        if (edtEmail!=null) {
            final String email = edtEmail.getText().toString().trim();
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

//    private void checkPasswordValidation() {
//
//        if (edtPassword!=null && edtConfrimPassword!=null) {
//
//            edtPassword.addTextChangedListener(new TextWatcher() {
//                @Override
//                public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//
//                }
//
//                @Override
//                public void onTextChanged(CharSequence s, int start, int before, int count) {
//                    if (!edtConfrimPassword.getText().toString().isEmpty()) {
//                        if (!s.toString().equalsIgnoreCase(edtConfrimPassword.getText().toString())) {
//                            edtConfrimPassword.setError("Password doesn't match");
//                        }
//                        else {
//                            edtConfrimPassword.setError(null);
//                        }
//                    }
//                }
//
//                @Override
//                public void afterTextChanged(Editable s) {
//
//                }
//            });
//
//            edtConfrimPassword.addTextChangedListener(new TextWatcher() {
//                @Override
//                public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//
//                }
//
//                @Override
//                public void onTextChanged(CharSequence s, int start, int before, int count) {
//                    if (!s.toString().equalsIgnoreCase(edtPassword.getText().toString())) {
//                        edtConfrimPassword.setError("Password doesn't match");
//                    }
//                    else {
//                        edtConfrimPassword.setError(null);
//                    }
//                }
//
//                @Override
//                public void afterTextChanged(Editable s) {
//
//                }
//            });
//        }
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

    public void openLogin(View view) {
        startActivity(new Intent(getApplicationContext(), LoginActivity.class));
        finish();
    }

    public void doRegistration(View view) {

        if(edtEmail!=null && edtEmail.getError()!=null) {

            CommonMethods.displayMessage(getApplicationContext(),"Please enter valid email");

            return;
        }


        if (!checkNotNullValidation(edtFirstname) || !checkNotNullValidation(edtLastname)
                || !checkNotNullValidation(edtEmail) || !checkNotNullValidation(edtPassword)
                || !checkNotNullValidation(edtPhone)) {
            return;
        }


        mProgressView.setVisibility(View.VISIBLE);
        LoginMethods.doRegistration(this, edtEmail.getText().toString(), edtEmail.getText().toString(), edtPassword.getText().toString(),
                edtFirstname.getText().toString(), edtLastname.getText().toString(),
                rdtStudent.isChecked() ? "STUDENT" : "MENTOR", edtEmail.getText().toString(), "2",
                new OnDatasetReceiveCallback<LoginDataset>() {

            @Override
            public void onDatasetReceive(LoginDataset dataset) {

                mProgressView.setVisibility(View.GONE);

                Crashlytics.setUserIdentifier(dataset.getUserId()+"");
                Crashlytics.setUserEmail(dataset.getEmail());
                Crashlytics.setUserName(dataset.getName());

                PrefData.setUserData(getApplicationContext(), dataset);

                Intent intent = new Intent(getApplicationContext(), EditUserProfileActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra("from_registration",true);
                startActivity(intent);
                finish();
            }

            @Override
            public void onFailureToReceiveDataset(String failure) {
                mProgressView.setVisibility(View.GONE);
                CommonMethods.displayMessage(getApplicationContext(), "Sorry, we are unable to make your profile. Please try later!");
            }

            @Override
            public void onInternetConnectionFailure() {
                mProgressView.setVisibility(View.GONE);
                CommonMethods.displayMessage(getApplicationContext(), "No Internet Connection Found !");
            }
        });


    }
}

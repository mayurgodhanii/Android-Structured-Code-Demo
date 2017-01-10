package dev.studentapp.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

/**
 * Created by Nirav Dangi on 27/06/16.
 *
 * This is a super activity class. This has been created in order to make a wrapper to AppCompatActivity.
 *
 */
public class BaseAppCompatActivity extends AppCompatActivity {

    public View mProgressView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

//        mProgressView = findViewById(R.id.progress);
    }
}

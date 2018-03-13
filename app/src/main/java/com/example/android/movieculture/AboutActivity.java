package com.example.android.movieculture;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

/**
 * This activity is responsible of showing information about the app.
 */
public class AboutActivity extends AppCompatActivity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_about);
        super.onCreate(savedInstanceState);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

}

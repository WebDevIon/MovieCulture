package com.example.android.movieculture;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

/**
 * This activity is responsible for displaying the {@link SettingsFragment}
 */

public class SettingsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        //noinspection ConstantConditions
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

}

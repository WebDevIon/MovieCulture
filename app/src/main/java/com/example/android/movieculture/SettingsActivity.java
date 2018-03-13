package com.example.android.movieculture;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

/**
 * This activity is responsible for displaying the {@link }
 */

public class SettingsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_settings);
        super.onCreate(savedInstanceState);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

}

package com.example.android.movieculture;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.preference.PreferenceManager;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.example.android.movieculture.model.Movie;
import com.example.android.movieculture.model.MovieResponse;
import com.example.android.movieculture.rest.ApiClient;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DiscoveryActivity extends AppCompatActivity {

    private static final String TAG = DiscoveryActivity.class.getSimpleName();
    public RecyclerView mRecyclerView;
    public ArrayList<Movie> mMovies = new ArrayList<>();
    public static String mSearchParam;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.discovery_activity);

        // Here we initialize the RecyclerView and set the type of LayoutManager.
        mRecyclerView = findViewById(R.id.discovery_activity_rv);
        GridLayoutManager layoutManager =
                new GridLayoutManager(this, 2);
        mRecyclerView.setLayoutManager(layoutManager);

        // Here we check to see if the search parameter was initialized, if not then we set it
        // according to the value we get from SharedPreferences, this way we ensure that the
        // user preferences remain the same even if the app is closed.
        if (mSearchParam == null) {
            SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
            mSearchParam = getSearchParamFromPreferences(prefs);
        }

        // Here we start the Retrofit enqueue and we populate he ArrayList mMovies with Movie
        // objects accordingly to the network response.
        ApiClient.getCall(mSearchParam).enqueue(new Callback<MovieResponse>() {
            @Override
            public void onResponse(@NonNull Call<MovieResponse> call,
                                   @NonNull Response<MovieResponse> response) {
                mMovies = response.body().getResults();
                MovieAdapter adapter = new MovieAdapter(mMovies, getApplicationContext());
                mRecyclerView.setAdapter(adapter);
            }

            @Override
            public void onFailure(@NonNull Call<MovieResponse> call, @NonNull Throwable t) {
                Log.e(TAG, t.toString());
            }
        });

    }

    /**
     * This method is responsible for getting the current preference setting value regarding
     * the movie sort order.
     *
     * @param sharedPreferences the SharedPreferences that contains the setting value.
     *
     * @return the string that contains the value needed to sort the movies.
     */
    public String getSearchParamFromPreferences (SharedPreferences sharedPreferences) {
        return sharedPreferences.getString(getString(R.string.sort_key),
                getString(R.string.sort_by_popularity));
    }

    /**
     * This is where we inflate and set up the menu for this Activity.
     *
     * @param menu The options menu in which you place your items.
     *
     * @return You must return true for the menu to be displayed;
     *         if you return false it will not be shown.
     *
     * @see #onOptionsItemSelected
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Here we create a instance of the Menu Inflater
        MenuInflater inflater = getMenuInflater();

        // We inflate the menu
        inflater.inflate(R.menu.main_menu, menu);

        // We return true so that the menu is shown
        return true;
    }

    /**
     * Callback invoked when a menu item was selected from this Activity's menu.
     *
     * @param item The menu item that was selected by the user
     *
     * @return true if you handle the menu click here, false otherwise
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Here we store the id of the item that was selected by the user
        int id = item.getItemId();

        // If the id matches that of the About menu item then we launch the About Activity
        if (id == R.id.action_about) {
            startActivity(new Intent(this, AboutActivity.class));
            return true;
        }

        // If the id matches that of the Settings menu item then we launch the Menu Activity
        if (id == R.id.action_settings) {
            startActivity(new Intent(this, SettingsActivity.class));
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

}

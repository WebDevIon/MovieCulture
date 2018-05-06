package com.example.android.movieculture;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.example.android.movieculture.model.Movie;
import com.example.android.movieculture.model.MovieResponse;
import com.example.android.movieculture.rest.ApiClient;
import com.example.android.movieculture.rest.TheMovieDatabaseAPI;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DiscoveryActivity extends AppCompatActivity {

    private static final String TAG = DiscoveryActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.discovery_activity);

        final RecyclerView recyclerView = findViewById(R.id.discovery_activity_rv);
        GridLayoutManager layoutManager =
                new GridLayoutManager(this, 2);
        recyclerView.setLayoutManager(layoutManager);

        TheMovieDatabaseAPI theMovieDatabaseAPI = ApiClient.getClient().create(TheMovieDatabaseAPI.class);

        // TODO: Change the query parameter according to the settings menu.
        Call<MovieResponse> call = theMovieDatabaseAPI.getPopularMovies(BuildConfig.API_KEY);
        call.enqueue(new Callback<MovieResponse>() {

            @Override
            public void onResponse(@NonNull Call<MovieResponse> call,
                                   @NonNull Response<MovieResponse> response) {

                if (response.isSuccessful()) {
                    List<Movie> movies = response.body().getResults();
                    Log.d(TAG, "Number of movies: " + movies.size());
                    MovieAdapter adapter = new MovieAdapter(movies, getApplicationContext());
                    recyclerView.setAdapter(adapter);
                } else {
                    Log.d("MovieResponse Callback", "Code: " + response.code()
                            + "Message: " + response.message());
                }
            }

            @Override
            public void onFailure(@NonNull Call<MovieResponse> call, @NonNull Throwable t) {
                Log.e(TAG, t.toString());
            }
        });

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

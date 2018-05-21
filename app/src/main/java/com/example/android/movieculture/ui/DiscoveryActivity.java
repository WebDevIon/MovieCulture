package com.example.android.movieculture.ui;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.preference.PreferenceManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.example.android.movieculture.R;
import com.example.android.movieculture.data.MovieContract;
import com.example.android.movieculture.data.MovieContract.MovieEntry;
import com.example.android.movieculture.model.Movie;
import com.example.android.movieculture.model.MovieAdapter;
import com.example.android.movieculture.model.MovieResponse;
import com.example.android.movieculture.rest.ApiClient;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * This activity is the main activity of the app, it displays the movie grid
 * arranged by the user preference.
 */

public class DiscoveryActivity extends AppCompatActivity implements
        MovieAdapter.MovieAdapterOnClickHandler,
        LoaderManager.LoaderCallbacks<Cursor> {

    private static final String TAG = DiscoveryActivity.class.getSimpleName();
    private static final int ID_MOVIE_LOADER = 112;
    private RecyclerView mRecyclerView;
    private ArrayList<Movie> mMovies = new ArrayList<>();
    public static String mSearchParam;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.discovery_activity);

        // Here we initialize the RecyclerView and set the type of LayoutManager.
        mRecyclerView = findViewById(R.id.discovery_activity_rv);
        GridAutoFitLayoutManager layoutManager =
                new GridAutoFitLayoutManager(this, 500);
        mRecyclerView.setLayoutManager(layoutManager);

        // Here we check to see if the search parameter was initialized, if not then we set it
        // according to the value we get from SharedPreferences, this way we ensure that the
        // user preferences remain the same even if the app is closed.
        if (mSearchParam == null) {
            SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
            mSearchParam = getSearchParamFromPreferences(prefs);
        }

        if (mSearchParam.equals(getString(R.string.sort_by_favorites))) {
            // If the search param is "favorites" then we start a loader and query the database
            // for the movie list.
            getSupportLoaderManager().initLoader(ID_MOVIE_LOADER, null, this);

        } else {
            // Here we start the Retrofit enqueue and we populate the ArrayList mMovies with Movie
            // objects accordingly to the network response.
            ApiClient.getMoviesCall(mSearchParam).enqueue(new Callback<MovieResponse>() {
                @Override
                public void onResponse(@NonNull Call<MovieResponse> call,
                                       @NonNull Response<MovieResponse> response) {
                    mMovies = response.body().getResults();
                    MovieAdapter adapter = new MovieAdapter(mMovies, getApplicationContext(),
                            DiscoveryActivity.this);
                    mRecyclerView.setAdapter(adapter);
                }

                @Override
                public void onFailure(@NonNull Call<MovieResponse> call, @NonNull Throwable t) {
                    Log.e(TAG, t.toString());
                }
            });
        }

    }

    /**
     * Method used to handle the clicks on the objects in the grid.
     * @param movie the movie that the user has clicked on is passed to this method
     *              and from it we transfer all the attributes with the intent as key
     *              value pairs.
     */
    @Override
    public void onClick(Movie movie) {
        String date = reverseDate(movie.getReleaseDate());
        Intent movieDetailIntent = new Intent(DiscoveryActivity.this, DetailActivity.class);
        movieDetailIntent.putExtra(Movie.MOVIE_TITLE, movie.getTitle());
        movieDetailIntent.putExtra(Movie.RELEASE_DATE, date);
        movieDetailIntent.putExtra(Movie.POSTER_PATH, movie.getPosterPath());
        movieDetailIntent.putExtra(Movie.VOTE_AVERAGE, (movie.getVoteAverage()).toString());
        movieDetailIntent.putExtra(Movie.OVERVIEW, movie.getOverview());
        movieDetailIntent.putExtra(Movie.MOVIE_ID, movie.getMovieId());
        startActivity(movieDetailIntent);
    }

    /**
     * Method used to reverse the date string from yyyy-mm-dd to dd-mm-yyyy.
     * @param date the date retrieved from the API
     * @return the reversed date as a String.
     */
    private String reverseDate (String date) {
        String[] separated = date.split("-");
        return separated[2] + "-" + separated[1] + "-" + separated[0];
    }

    /**
     * This method is responsible for getting the current preference setting value regarding
     * the movie sort order.
     * @param sharedPreferences the SharedPreferences that contains the setting value.
     * @return the string that contains the value needed to sort the movies.
     */
    private String getSearchParamFromPreferences(SharedPreferences sharedPreferences) {
        return sharedPreferences.getString(getString(R.string.sort_key),
                getString(R.string.sort_by_popularity));
    }

    /**
     * This is where we inflate and set up the menu for this Activity.
     * @param menu The options menu in which you place your items.
     * @return You must return true for the menu to be displayed;
     *         if you return false it will not be shown.
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
     * @param item The menu item that was selected by the user
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

    /**
     * This method is called when the search parameter is set to favorites by the user.
     * The table movies is queried and the result is stored into a cursor which is passed
     * to the onLoadFinished method.
     * @param loaderId the id of the loader.
     * @param args the arguments that we passed along with the id if there are any.
     * @return a new cursor that contains the whole movies table.
     */
    @NonNull
    @Override
    public Loader<Cursor> onCreateLoader(int loaderId, @Nullable Bundle args) {
        switch (loaderId) {
            case ID_MOVIE_LOADER:
                return new CursorLoader(this,
                        MovieContract.MovieEntry.CONTENT_URI,
                        null,
                        null,
                        null,
                        null);
            default:
                throw new RuntimeException("Loader Not Implemented: " + loaderId);
        }
    }

    /**
     * In this method we check to see if the cursor is not null and it has data in it.
     * If it has then we load each row of data into Movie objects which we store into a
     * ArrayList of Movie objects. This list we pass as an argument into our MovieAdapter
     * constructor and then we set this adapter to the RecyclerView.
     * @param loader the current Loader<Cursor> object.
     * @param data the cursor which contains the movies table data.
     */
    @Override
    public void onLoadFinished(@NonNull Loader<Cursor> loader, Cursor data) {
        if (data != null && (data.getCount() > 0)) {
            try {
                while (data.moveToNext()) {
                    Integer movieIdIndex = data.getColumnIndex(MovieEntry.COLUMN_MOVIE_ID);
                    Integer movieId = data.getInt(movieIdIndex);
                    Integer titleIndex = data.getColumnIndex(MovieEntry.COLUMN_MOVIE_TITLE);
                    String movieTitle = data.getString(titleIndex);
                    Integer posterIndex = data.getColumnIndex(MovieEntry.COLUMN_MOVIE_POSTER_PATH);
                    String posterPath = data.getString(posterIndex);
                    Integer dateIndex = data.getColumnIndex(MovieEntry.COLUMN_RELEASE_DATE);
                    String releaseDate = data.getString(dateIndex);
                    Integer ratingIndex = data.getColumnIndex(MovieEntry.COLUMN_USER_RATING);
                    Double userRating = Double.parseDouble(data.getString(ratingIndex));
                    Integer synopsisIndex = data.getColumnIndex(MovieEntry.COLUMN_SYNOPSIS);
                    String synopsis = data.getString(synopsisIndex);
                    mMovies.add(new Movie(movieTitle, releaseDate, posterPath,
                            userRating, synopsis, movieId));
                }
            } finally {
                data.close();
            }

            MovieAdapter adapter = new MovieAdapter(mMovies, getApplicationContext(),
                    DiscoveryActivity.this);
            mRecyclerView.setAdapter(adapter);
        }

    }

    /**
     * If the loader is reset we set the current loader to be null.
     * @param loader the current Loader<Cursor> object.
     */
    @Override
    public void onLoaderReset(@NonNull Loader<Cursor> loader) {
        loader = null;
    }
}

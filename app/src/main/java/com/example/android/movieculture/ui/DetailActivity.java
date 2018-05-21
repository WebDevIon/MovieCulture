package com.example.android.movieculture.ui;

import android.content.ActivityNotFoundException;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.databinding.DataBindingUtil;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.android.movieculture.R;
import com.example.android.movieculture.data.MovieContract;
import com.example.android.movieculture.databinding.ActivityMovieDetailBinding;
import com.example.android.movieculture.model.Movie;
import com.example.android.movieculture.model.Review;
import com.example.android.movieculture.model.ReviewAdapter;
import com.example.android.movieculture.model.ReviewResponse;
import com.example.android.movieculture.model.Trailer;
import com.example.android.movieculture.model.TrailerAdapter;
import com.example.android.movieculture.model.TrailerResponse;
import com.example.android.movieculture.rest.ApiClient;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * This activity is responsible for displaying the movie details.
 * It uses data binding to set the content of each view with the
 * data provided by the API.
 */
public class DetailActivity extends AppCompatActivity implements
        TrailerAdapter.TrailerAdapterOnClickHandler {

    private static final String TAG = DetailActivity.class.getSimpleName();
    private ActivityMovieDetailBinding mMovieDetailBinding;
    private ArrayList<Trailer> mTrailersList = new ArrayList<>();
    private ArrayList<Review> mReviewsList = new ArrayList<>();
    private Intent mIntent;
    private Integer mMovieID;
    private Uri mMovieWithIdUri;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mMovieDetailBinding = DataBindingUtil.setContentView(
                this, R.layout.activity_movie_detail);

        mIntent = getIntent();

        mMovieDetailBinding.titleInfo.detailActivityTitleTv
                .setText(mIntent.getStringExtra(Movie.MOVIE_TITLE));

        mMovieDetailBinding.primaryInfo.detailActivityReleaseDateTv
                .setText(mIntent.getStringExtra(Movie.RELEASE_DATE));

        mMovieDetailBinding.primaryInfo.detailActivityVoteAverageTv
                .setText(mIntent.getStringExtra(Movie.VOTE_AVERAGE));

        mMovieDetailBinding.secondaryInfo.detailActivityPlotSynopsisTv
                .setText(mIntent.getStringExtra(Movie.OVERVIEW));

        // Here we get the ImageView by it's ID and we set the content using the Picasso library.
        ImageView poster = findViewById(R.id.detail_activity_movie_poster_iv);
        Picasso.get()
                .load(ApiClient.BASE_IMAGE_URL + mIntent.getStringExtra(Movie.POSTER_PATH))
                .into(poster);

        // Here we get the movie id which is passed from the DiscoveryActivity.
        mMovieID = mIntent.getIntExtra(Movie.MOVIE_ID, -1);

        // Here we set the type of LayoutManager for the trailers RecycleView.
        RecyclerView.LayoutManager trailersLayoutManager =
                new LinearLayoutManager(this);
        mMovieDetailBinding.trailerInfo.trailersRv.setLayoutManager(trailersLayoutManager);

        // Here we set the type of LayoutManager for the reviews Recyclerview.
        RecyclerView.LayoutManager reviewsLayoutManager =
                new LinearLayoutManager(this);
        mMovieDetailBinding.reviewInfo.reviewsRv.setLayoutManager(reviewsLayoutManager);

        // Here we check to see if a movie id was passed from the DiscoveryActivity.
        if (mMovieID == -1) {
            Toast.makeText(this, "No movie id was passed", Toast.LENGTH_SHORT).show();
        } else {

            /*
             * Here we start the Retrofit enqueue and we populate the ArrayList mTrailersRecyclerView
             * with Trailer objects accordingly to the network response.
             */
            ApiClient.getTrailersCall(mMovieID).enqueue(new Callback<TrailerResponse>() {
                @Override
                public void onResponse(@NonNull Call<TrailerResponse> call,
                                       @NonNull Response<TrailerResponse> response) {
                    mTrailersList = response.body().getResults();
                    TrailerAdapter adapter = new TrailerAdapter(mTrailersList, getApplicationContext(),
                            DetailActivity.this);
                    mMovieDetailBinding.trailerInfo.trailersRv.setAdapter(adapter);
                }

                @Override
                public void onFailure(@NonNull Call<TrailerResponse> call, @NonNull Throwable t) {
                    Log.e(TAG, t.toString());
                }
            });

            /*
             * Here we start the Retrofit enqueue and we populate the ArrayList mReviewsRecyclerView
             * with Review objects accordingly to the network response.
             */
            ApiClient.getReviewsCall(mMovieID).enqueue(new Callback<ReviewResponse>() {
                @Override
                public void onResponse(@NonNull Call<ReviewResponse> call,
                                       @NonNull Response<ReviewResponse> response) {
                    mReviewsList = response.body().getResults();
                    ReviewAdapter adapter = new ReviewAdapter(mReviewsList, getApplicationContext());
                    mMovieDetailBinding.reviewInfo.reviewsRv.setAdapter(adapter);
                }

                @Override
                public void onFailure(@NonNull Call<ReviewResponse> call, @NonNull Throwable t) {
                    Log.e(TAG, t.toString());
                }
            });
        }

        /*
         * ------------------ Code for the favorite button. ---------------------
         *
         * Here we create a uri with the movie id appended to it's end and we
         * query the database using this uri.
         *
         * The query is made on the main thread because it returns a single value
         * from the database which contains one movie id.
         */
        mMovieWithIdUri = MovieContract.MovieEntry.buildMoviesUriWithId(mMovieID);
        Cursor cursor = getContentResolver().query(
                mMovieWithIdUri,
                null,
                null,
                null,
                null);

        /*
         * If the cursor wi not null and it is not empty we get the id value returned
         * from the database and compare it with the id value from the API. If the values
         * are the same then the movie was added previously in the database and we set the
         * toggle button to the checked state and it's icon to an full star.
         */
        if (cursor != null && (cursor.getCount() > 0)) {
            try{
                cursor.moveToFirst();
                Integer columnIndex = cursor.getColumnIndex(MovieContract.MovieEntry.COLUMN_MOVIE_ID);
                Integer result = cursor.getInt(columnIndex);
                if (result.equals(mMovieID)) {
                    mMovieDetailBinding.primaryInfo.favoriteToggleButton.setChecked(true);
                    mMovieDetailBinding.primaryInfo.favoriteToggleButton
                            .setBackgroundDrawable(ContextCompat.getDrawable
                            (getApplicationContext(), R.drawable.ic_star));
                }
            // We close the cursor after we are done with it.
            } finally {
                cursor.close();
            }
        /*
         * If the id was not the same or it was not present in the database then we set
         * the toggle button to the unchecked state and it's icon to an empty star.
         */
        } else {
            mMovieDetailBinding.primaryInfo.favoriteToggleButton.setChecked(false);
            mMovieDetailBinding.primaryInfo.favoriteToggleButton.setBackgroundDrawable(
                    ContextCompat.getDrawable(getApplicationContext(), R.drawable.ic_star_border));
        }

        /*
         * Here we initialize a change listener on the toggle button that will change it's
         * state and update the database when it is pressed.
         */
        mMovieDetailBinding.primaryInfo.favoriteToggleButton
                .setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                ContentValues contentValues = new ContentValues();

                /*
                 * If the button is pressed by the user when it is unchecked then we change it's
                 * state to checked, it's icon to a full star and we insert a new movie into the
                 * database which has the values of the movie that we are currently watching.
                 * After the insert we show a toast to notify the user that the movie was added
                 * to favorites.
                 */
                if (isChecked) {
                    mMovieDetailBinding.primaryInfo.favoriteToggleButton.setChecked(true);
                    mMovieDetailBinding.primaryInfo.favoriteToggleButton
                            .setBackgroundDrawable(ContextCompat.getDrawable
                                    (getApplicationContext(), R.drawable.ic_star));

                    contentValues.put(MovieContract.MovieEntry.COLUMN_MOVIE_TITLE,
                            mIntent.getStringExtra(Movie.MOVIE_TITLE));
                    contentValues.put(MovieContract.MovieEntry.COLUMN_MOVIE_ID, mMovieID);

                    getContentResolver().insert(
                            MovieContract.MovieEntry.CONTENT_URI,
                            contentValues);

                    Toast.makeText(DetailActivity.this,
                            R.string.toast_movie_added_string,
                            Toast.LENGTH_SHORT).show();

                /*
                 * If the button is pressed by the user when it is checked then we change it's
                 * state to unchecked, it's icon to a empty star and we delete the movie from the
                 * database which has the id of the movie that we are currently watching.
                 * After the delete we show a toast to notify the user that the movie was removed
                 * from favorites.
                 */
                } else {
                    mMovieDetailBinding.primaryInfo.favoriteToggleButton.setChecked(false);
                    mMovieDetailBinding.primaryInfo.favoriteToggleButton
                            .setBackgroundDrawable(ContextCompat.getDrawable
                                    (getApplicationContext(), R.drawable.ic_star_border));

                    getContentResolver().delete(
                            mMovieWithIdUri,
                            null,
                            null);

                    Toast.makeText(DetailActivity.this,
                            R.string.toast_movie_removed_string,
                            Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    /**
     * Method used to handle the clicks on the trailer objects in the list.
     * We launch an explicit intent to the youtube app and if that is not installed then
     * we launch an explicit webIntent.
     * @param trailer the trailer object that was clicked.
     */
    @Override
    public void onClick(Trailer trailer) {
        Intent appIntent = new Intent(Intent.ACTION_VIEW,
                Uri.parse("vnd.youtube:" + trailer.getTrailerKey()));
        Intent webIntent = new Intent(Intent.ACTION_VIEW,
                Uri.parse("http://www.youtube.com/watch?v=" + trailer.getTrailerKey()));
        try {
            startActivity(appIntent);
        } catch (ActivityNotFoundException ex) {
            startActivity(webIntent);
        }
    }
}

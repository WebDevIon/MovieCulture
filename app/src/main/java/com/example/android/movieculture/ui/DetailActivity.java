package com.example.android.movieculture.ui;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.android.movieculture.R;
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
    private RecyclerView mTrailersRecyclerView;
    private RecyclerView mReviewsRecyclerView;
    private ArrayList<Trailer> mTrailersList = new ArrayList<>();
    private ArrayList<Review> mReviewsList = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ActivityMovieDetailBinding mMovieDetailBinding =
                DataBindingUtil.setContentView(this, R.layout.activity_movie_detail);

        Intent intent = getIntent();

        mMovieDetailBinding.titleInfo.detailActivityTitleTv
                .setText(intent.getStringExtra(Movie.MOVIE_TITLE));

        mMovieDetailBinding.primaryInfo.detailActivityReleaseDateTv
                .setText(intent.getStringExtra(Movie.RELEASE_DATE));

        mMovieDetailBinding.primaryInfo.detailActivityVoteAverageTv
                .setText(intent.getStringExtra(Movie.VOTE_AVERAGE));

        mMovieDetailBinding.secondaryInfo.detailActivityPlotSynopsisTv
                .setText(intent.getStringExtra(Movie.OVERVIEW));

        // Here we get the ImageView by it's ID and we set the content using the Picasso library.
        ImageView poster = findViewById(R.id.detail_activity_movie_poster_iv);
        Picasso.get()
                .load(ApiClient.BASE_IMAGE_URL + intent.getStringExtra(Movie.POSTER_PATH))
                .into(poster);

        // Here we get the movie id which is passed from the DiscoveryActivity.
        int movieID = intent.getIntExtra(Movie.MOVIE_ID, -1);

        // Here we initialize the RecyclerView for the trailers and set the type of LayoutManager.
        mTrailersRecyclerView = findViewById(R.id.trailers_rv);
        RecyclerView.LayoutManager trailersLayoutManager =
                new LinearLayoutManager(this);
        mTrailersRecyclerView.setLayoutManager(trailersLayoutManager);

        // Here we initialize the RecyclerView for the reviews and set the type of LayoutManager.
        mReviewsRecyclerView = findViewById(R.id.reviews_rv);
        RecyclerView.LayoutManager reviewsLayoutManager =
                new LinearLayoutManager(this);
        mReviewsRecyclerView.setLayoutManager(reviewsLayoutManager);

        // Here we check to see if a movie id was passed from the DiscoveryActivity.
        if (movieID == -1) {
            Toast.makeText(this, "No movie id was passed", Toast.LENGTH_SHORT).show();
        } else {

            // Here we start the Retrofit enqueue and we populate the ArrayList mTrailersRecyclerView
            // with Trailer objects accordingly to the network response.
            ApiClient.getTrailersCall(movieID).enqueue(new Callback<TrailerResponse>() {
                @Override
                public void onResponse(@NonNull Call<TrailerResponse> call,
                                       @NonNull Response<TrailerResponse> response) {
                    mTrailersList = response.body().getResults();
                    TrailerAdapter adapter = new TrailerAdapter(mTrailersList, getApplicationContext(),
                            DetailActivity.this);
                    mTrailersRecyclerView.setAdapter(adapter);
                }

                @Override
                public void onFailure(@NonNull Call<TrailerResponse> call, @NonNull Throwable t) {
                    Log.e(TAG, t.toString());
                }
            });

            // Here we start the Retrofit enqueue and we populate the ArrayList mReviewsRecyclerView
            // with Review objects accordingly to the network response.
            ApiClient.getReviewsCall(movieID).enqueue(new Callback<ReviewResponse>() {
                @Override
                public void onResponse(@NonNull Call<ReviewResponse> call,
                                       @NonNull Response<ReviewResponse> response) {
                    mReviewsList = response.body().getResults();
                    ReviewAdapter adapter = new ReviewAdapter(mReviewsList, getApplicationContext());
                    mReviewsRecyclerView.setAdapter(adapter);
                }

                @Override
                public void onFailure(@NonNull Call<ReviewResponse> call, @NonNull Throwable t) {
                    Log.e(TAG, t.toString());
                }
            });
        }

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

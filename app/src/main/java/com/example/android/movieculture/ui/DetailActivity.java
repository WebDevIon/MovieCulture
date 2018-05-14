package com.example.android.movieculture.ui;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;

import com.example.android.movieculture.R;
import com.example.android.movieculture.databinding.ActivityMovieDetailBinding;
import com.example.android.movieculture.model.Movie;
import com.example.android.movieculture.rest.ApiClient;
import com.squareup.picasso.Picasso;

/**
 * This activity is responsible for displaying the movie details.
 * It uses data binding to set the content of each view with the
 * data provided by the API.
 */
public class DetailActivity extends AppCompatActivity {

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

    }

}

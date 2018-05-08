package com.example.android.movieculture;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.android.movieculture.model.Movie;
import com.example.android.movieculture.rest.ApiClient;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.MovieViewHolder> {

    private Context context;
    private ArrayList<Movie> movies;

    public class MovieViewHolder extends RecyclerView.ViewHolder {

        ImageView moviePoster;

        public MovieViewHolder(View itemView) {
            super(itemView);

            moviePoster = itemView.findViewById(R.id.discovery_activity_poster_iv);
        }
    }

    public MovieAdapter (ArrayList<Movie> movies, Context context) {
        this.movies = movies;
        this.context = context;
    }

    @NonNull
    @Override
    public MovieViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        int layoutId = R.layout.grid_item_movie;

        View view = LayoutInflater.from(parent.getContext()).inflate(layoutId, parent, false);
        return new MovieViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MovieViewHolder holder, int position) {

        Picasso.get()
                .load(ApiClient.BASE_IMAGE_URL + movies.get(position).getPosterPath())
                .into(holder.moviePoster);
    }

    @Override
    public int getItemCount() {
        return movies.size();
    }

}

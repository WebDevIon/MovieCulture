package com.example.android.movieculture.model;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.android.movieculture.R;
import com.example.android.movieculture.rest.ApiClient;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * This is a custom Adapter which is responsible for loading the movie posters in
 * the Discovery Activity and it also has the interface which is implemented and
 * overwritten in Detail Activity to handle the object clicks.
 */

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.MovieViewHolder> {

    private final Context context;
    private final ArrayList<Movie> movies;
    final private MovieAdapterOnClickHandler clickHandler;

    /**
     * This is the interface responsible for handling the object clicks.
     */
    public interface MovieAdapterOnClickHandler {
        void onClick(Movie movie);
    }

    /**
     * This is the constructor for the MovieAdapter
     * @param movies the ArrayList of Movie objects received from the server.
     * @param context the context of the application.
     * @param clickHandler the click handler.
     */
    public MovieAdapter(ArrayList<Movie> movies, Context context, MovieAdapterOnClickHandler clickHandler) {
        this.movies = movies;
        this.context = context;
        this.clickHandler = clickHandler;
    }

    /**
     * Inner class used for caching the child view of the MovieAdapter. Here we placed the
     * onClickListener because here it has access both to the adapter and the views.
     */
    class MovieViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        final ImageView moviePoster;

        MovieViewHolder(View itemView) {
            super(itemView);

            moviePoster = itemView.findViewById(R.id.discovery_activity_poster_iv);
            itemView.setOnClickListener(this);
        }

        /**
         * Here we override the onClick method. When a child is clicked we fetch the movie
         * object and then we call the clickHandler registered with this adapter and pass
         * in the movie object.
         * @param view the view that was clicked.
         */
        @Override
        public void onClick(View view) {
            int position = getAdapterPosition();
            Movie movie = movies.get(position);
            clickHandler.onClick(movie);
        }
    }

    /**
     * This method creates each ViewHolder.
     * @param parent the ViewGroup that contains these ViewHolders.
     * @param viewType the int which define which kind of viewType we want to populate.
     * @return the MovieViewHolder that holds the View for each grid item.
     */
    @NonNull
    @Override
    public MovieViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        int layoutId = R.layout.grid_item_movie;

        View view = LayoutInflater.from(context).inflate(layoutId, parent, false);
        return new MovieViewHolder(view);
    }

    /**
     * In this method we update the contents of the ViewHolder for that given position.
     * @param holder the ViewHolder that will have it's contents updated.
     * @param position the position within the adapter.
     */
    @Override
    public void onBindViewHolder(@NonNull MovieViewHolder holder, int position) {
        // The Picasso library is used to set the content of the ImageView.
        Picasso.get()
                .load(ApiClient.BASE_IMAGE_URL + movies.get(position).getPosterPath())
                .into(holder.moviePoster);
    }

    /**
     * This item returns the number of items to display.
     * @return the number of items available in our movie grid.
     */
    @Override
    public int getItemCount() {
        return movies.size();
    }

}

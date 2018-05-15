package com.example.android.movieculture.model;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

/**
 * This is a custom Adapter which is responsible for loading the movie trailers in
 * the Detail Activity and it also has the interface which is implemented and
 * overwritten in Detail Activity to handle the object clicks.
 */
public class TrailerAdapter extends RecyclerView.Adapter<TrailerAdapter.TrailerViewHolder> {

    private final Context context;
    private final ArrayList<Trailer> trailers;
    private final TrailerAdapterOnClickHandler clickHandler;

    /**
     * This is the interface responsible for handling the object clicks.
     */
    public interface TrailerAdapterOnClickHandler {
        void onClick(Trailer trailer);
    }

    /**
     * This is the constructor for the TrailerAdapter.
     * @param trailers the ArrayList of Trailer objects received from the server.
     * @param context the context of the application.
     * @param clickHandler the click handler.
     */
    public TrailerAdapter(ArrayList<Trailer> trailers, Context context,
                          TrailerAdapterOnClickHandler clickHandler) {
        this.trailers = trailers;
        this.context = context;
        this.clickHandler = clickHandler;
    }

    /**
     * Inner class used for caching the child view of the TrailerAdapter. Here we placed the
     * onClickListener because here it has access both to the adapter and the views.
     */
    class TrailerViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public TrailerViewHolder(View itemView) {
            super(itemView);

            //TODO: Add the views and set the click handler.
        }

        /**
         * Here we override the onClick method. When a child is clicked we fetch the trailer
         * object and then we call the clickHandler registered with this adapter and pass
         * in the trailer object.
         * @param view the view that was clicked.
         */
        @Override
        public void onClick(View view) {
            int position = getAdapterPosition();
            Trailer trailer = trailers.get(position);
            clickHandler.onClick(trailer);
        }
    }

    @NonNull
    @Override
    public TrailerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull TrailerViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }
}

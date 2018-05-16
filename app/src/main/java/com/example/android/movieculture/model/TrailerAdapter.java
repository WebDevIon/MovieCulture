package com.example.android.movieculture.model;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.android.movieculture.R;

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

        final TextView trailerTitle;

        public TrailerViewHolder(View itemView) {
            super(itemView);

            trailerTitle = itemView.findViewById(R.id.detail_activity_trailer_title_tv);
            itemView.setOnClickListener(this);
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

    /**
     * This method creates each ViewHolder.
     * @param parent the ViewGroup that contains these ViewHolders.
     * @param viewType the int which define which kind of viewType we want to populate.
     * @return the TrailerViewHolder that holds the View for each list item.
     */
    @NonNull
    @Override
    public TrailerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        int layoutId = R.layout.list_item_trailer;

        View view = LayoutInflater.from(context).inflate(layoutId, parent, false);
        return new TrailerViewHolder(view);
    }

    /**
     * In this method we update the contents of the ViewHolder for that given position.
     * @param holder the ViewHolder that will have it's contents updated.
     * @param position the position within the adapter.
     */
    @Override
    public void onBindViewHolder(@NonNull TrailerViewHolder holder, int position) {
        holder.trailerTitle.setText(trailers.get(position).getTrailerName());
    }

    /**
     * This item returns the number of items to display.
     * @return the number of items available in our trailer list.
     */
    @Override
    public int getItemCount() {
        return trailers.size();
    }
}

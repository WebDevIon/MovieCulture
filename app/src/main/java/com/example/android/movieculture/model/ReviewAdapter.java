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
 * This is a custom Adapter which is responsible for loading the movie reviews in
 * the Detail Activity and it also has the interface which is implemented and
 * overwritten in Detail Activity to handle the object clicks.
 */
public class ReviewAdapter extends RecyclerView.Adapter<ReviewAdapter.ReviewViewHolder> {

    private final Context context;
    private final ArrayList<Review> reviews;

    /**
     * This is the constructor for the ReviewAdapter.
     * @param reviews the ArrayList of Review objects received from the server.
     * @param context the context of the application.
     */
    public ReviewAdapter(ArrayList<Review> reviews, Context context) {
        this.reviews = reviews;
        this.context = context;
    }

    /**
     * Inner class used for caching the child view of the ReviewAdapter.
     */
    class ReviewViewHolder extends RecyclerView.ViewHolder {

        final TextView reviewerName;
        final TextView reviewContent;

        public ReviewViewHolder(View itemView) {
            super(itemView);

            reviewerName = itemView.findViewById(R.id.detail_activity_reviewer_name_tv);
            reviewContent = itemView.findViewById(R.id.detail_activity_review_tv);
        }
    }

    /**
     * This method creates each ViewHolder.
     * @param parent the ViewGroup that contains these ViewHolders.
     * @param viewType the int which define which kind of viewType we want to populate.
     * @return the ReviewViewHolder that holds the View for each list item.
     */
    @NonNull
    @Override
    public ReviewViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        int layoutId = R.layout.list_item_review;

        View view = LayoutInflater.from(context).inflate(layoutId, parent, false);
        return new ReviewViewHolder(view);
    }

    /**
     * In this method we update the contents of the ViewHolder for that given position.
     * @param holder the ViewHolder that will have it's contents updated.
     * @param position the position within the adapter.
     */
    @Override
    public void onBindViewHolder(@NonNull ReviewViewHolder holder, int position) {
        holder.reviewerName.setText(reviews.get(position).getAuthor());
        holder.reviewContent.setText(reviews.get(position).getContent());
    }

    /**
     * This item returns the number of items to display.
     * @return the number of items available in our review list.
     */
    @Override
    public int getItemCount() {
        return reviews.size();
    }
}
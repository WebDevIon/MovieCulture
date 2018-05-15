package com.example.android.movieculture.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * This class is responsible for taking the response from the TMDB API and storing the id, number
 * of pages, movies, total results, the current page number and the reviews in a List of reviews.
 */
public class ReviewResponse {
    @SerializedName("id")
    private int id;
    @SerializedName("page")
    private int page;
    @SerializedName("total_results")
    private int totalResults;
    @SerializedName("total_pages")
    private int totalPages;
    @SerializedName("results")
    private ArrayList<Review> results;

    /**
     * Setters and getters for the parameters.
     */
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getTotalResults() {
        return totalResults;
    }

    public void setTotalResults(int totalResults) {
        this.totalResults = totalResults;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(int totalPages) {
        this.totalPages = totalPages;
    }

    public ArrayList<Review> getResults() {
        return results;
    }

    public void setResults(ArrayList<Review> results) {
        this.results = results;
    }
}

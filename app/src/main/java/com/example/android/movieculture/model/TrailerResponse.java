package com.example.android.movieculture.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * This class is responsible for taking the response from the TMDB API and storing the id
 * and the trailers in a List of trailers.
 */
public class TrailerResponse {
    @SerializedName("id")
    private int id;
    @SerializedName("results")
    private ArrayList<Trailer> results;

    /**
     * Setters and getters for the parameters.
     */
    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setResults(ArrayList<Trailer> results) {
        this.results = results;
    }

    public ArrayList<Trailer> getResults() {
        return results;
    }
}

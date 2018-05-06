package com.example.android.movieculture.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Since there are a lot of movies this class is responsible for taking the response from
 * the TMDB API and storing the number of pages, movies, total results, the current page
 * number and the movies in a List of movies.
 */
public class MovieResponse {
    @SerializedName("page")
    private int page;
    @SerializedName("total_results")
    private int totalResults;
    @SerializedName("total_pages")
    private int totalPages;
    @SerializedName("results")
    private List<Movie> results;

    /**
     * Setters and getters for the parameters.
     */
    public void setPage(int page) {
        this.page = page;
    }

    public int getPage() {
        return page;
    }

    public void setTotalResults(int totalResults) {
        this.totalResults = totalResults;
    }

    public int getTotalResults() {
        return totalResults;
    }

    public void setTotalPages(int totalPages) {
        this.totalPages = totalPages;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public void setResults(List<Movie> results) {
        this.results = results;
    }

    public List<Movie> getResults() {
        return results;
    }
}

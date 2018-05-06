package com.example.android.movieculture.model;

import com.google.gson.annotations.SerializedName;

/**
 * This class is responsible for creating Movie objects which will store the needed movie
 * attributes recieved from TMDB API.
 */
public class Movie {
    @SerializedName("title")
    private String title;
    @SerializedName("release_date")
    private String releaseDate;
    @SerializedName("poster_path")
    private String posterPath;
    @SerializedName("vote_average")
    private Double voteAverage;
    @SerializedName("overview")
    private String overview;

    /**
     * Constructor for the Movie class.
     * @param title the title of the movie.
     * @param releaseDate the release date of the movie.
     * @param posterPath the URL path to the movie poster.
     * @param voteAverage the vote average of the movie.
     * @param overview the plot synopsis.
     */
    public Movie(String title, String releaseDate, String posterPath, Double voteAverage, String overview) {
        this.title = title;
        this.releaseDate = releaseDate;
        this.posterPath = posterPath;
        this.voteAverage = voteAverage;
        this.overview = overview;
    }

    /**
     * Here we declare all the setters and getters of the Movie object parameters.
     */
    public void setTitle(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public void setPosterPath(String posterPath) {
        this.posterPath = posterPath;
    }

    public String getPosterPath() {
        return posterPath;
    }

    public void setVoteAverage(Double voteAverage) {
        this.voteAverage = voteAverage;
    }

    public Double getVoteAverage() {
        return voteAverage;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public String getOverview() {
        return overview;
    }
}

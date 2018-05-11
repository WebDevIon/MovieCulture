package com.example.android.movieculture.model;

import com.google.gson.annotations.SerializedName;

/**
 * This class is responsible for creating Movie objects which will store the needed movie
 * attributes recieved from TMDB API.
 */
public class Movie {

    public static final String MOVIE_TITLE = "title";
    public static final String RELEASE_DATE = "release_date";
    public static final String POSTER_PATH = "poster_path";
    public static final String VOTE_AVERAGE = "vote_average";
    public static final String OVERVIEW = "overview";

    @SerializedName(MOVIE_TITLE)
    private String title;
    @SerializedName(RELEASE_DATE)
    private String releaseDate;
    @SerializedName(POSTER_PATH)
    private String posterPath;
    @SerializedName(VOTE_AVERAGE)
    private Double voteAverage;
    @SerializedName(OVERVIEW)
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

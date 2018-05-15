package com.example.android.movieculture.model;

import com.google.gson.annotations.SerializedName;

/**
 * This class is responsible for creating Trailer objects which will store the needed trailer
 * attributes received from TMDB API.
 */
public class Trailer {

    public static final String TRAILER_NAME = "name";
    public static final String TRAILER_KEY = "key";

    @SerializedName(TRAILER_NAME)
    private String trailerName;
    @SerializedName(TRAILER_KEY)
    private String trailerKey;

    /**
     * Constructor fot the Trailer object.
     * @param trailerName the name of the trailer.
     * @param trailerKey the key used to complete the trailer url.
     */
    public Trailer(String trailerName, String trailerKey) {
        this.trailerName = trailerName;
        this.trailerKey = trailerKey;
    }

    /**
     * Here we declare all the setters and getters of the Trailer object parameters.
     */
    public String getTrailerName() {
        return trailerName;
    }

    public void setTrailerName(String trailerName) {
        this.trailerName = trailerName;
    }

    public String getTrailerKey() {
        return trailerKey;
    }

    public void setTrailerKey(String trailerKey) {
        this.trailerKey = trailerKey;
    }
}

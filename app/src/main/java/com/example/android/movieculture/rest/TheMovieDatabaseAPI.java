package com.example.android.movieculture.rest;

import com.example.android.movieculture.model.MovieResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * This interface is responsible for holding the endpoints and the details of the parameters
 * and the request method for the Retrofit.
 */
public interface TheMovieDatabaseAPI {
    @GET("movie/top_rated")
    Call<MovieResponse> getTopRatedMovies(@Query("api_key") String api_key);

    @GET("movie/popular")
    Call<MovieResponse> getPopularMovies(@Query("api_key") String api_key);
}

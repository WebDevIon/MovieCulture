package com.example.android.movieculture.rest;

import com.example.android.movieculture.model.MovieResponse;
import com.example.android.movieculture.model.ReviewResponse;
import com.example.android.movieculture.model.TrailerResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * This interface is responsible for holding the endpoints and the details of the parameters
 * and the request method for the Retrofit.
 */
interface TheMovieDatabaseAPI {
    @GET("movie/{sortBy}")
    Call<MovieResponse> getMovies(@Path("sortBy") String sortBy,
                                  @Query(ApiClient.API_KEY_PARAM) String api_key);

    @GET("movie/{id}/videos")
    Call<TrailerResponse> getTrailers(@Path("id") String movieId,
                                      @Query(ApiClient.API_KEY_PARAM) String api_key);

    @GET("movie/{id}/reviews")
    Call<ReviewResponse> getReviews(@Path("id") String movieId,
                                    @Query(ApiClient.API_KEY_PARAM) String api_key);
}

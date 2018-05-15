package com.example.android.movieculture.rest;

import com.example.android.movieculture.BuildConfig;
import com.example.android.movieculture.model.MovieResponse;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * This class is the Retrofit builder class.
 */
public class ApiClient {

    private static final String TAG = ApiClient.class.getSimpleName();
    private static final String BASE_URL = "http://api.themoviedb.org/3/";
    public static final String BASE_IMAGE_URL = "http://image.tmdb.org/t/p/w185/";
    public static final String API_KEY_PARAM = "api_key";
    private static final String API_KEY = BuildConfig.API_KEY;
    private static Retrofit retrofit = null;

    /**
     * Method used to create the Retrofit object.
     *
     * @return the Retrofit object.
     */
    private static Retrofit getClient() {
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }

    /**
     * Method used to return the Call<MovieResponse> object used in the enqueue method.
     *
     * @param searchParam is the parameter that we use to sort the results (either by popularity
     *                    or by top rating)
     *
     * @return the Call<MovieResponse> object
     */
    public static Call<MovieResponse> getMoviesCall(String searchParam) {
        TheMovieDatabaseAPI api = getClient().create(TheMovieDatabaseAPI.class);
        return api.getMovies(searchParam, API_KEY);
    }
}

package com.example.android.movieculture.data;

import android.net.Uri;
import android.provider.BaseColumns;

/**
 * This class holds the uri, table name and the column names.
 */
public final class MovieContract {

    public static final String CONTENT_AUTHORITY = "com.example.android.movieculture";
    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);
    public static final String PATH_MOVIES = "movies";

    private MovieContract() {}

    // Here we define the contents of the movies table.
    public static class MovieEntry implements BaseColumns {

        // The base CONTENT_URI used to query the movies table from the content provider
        public static final Uri CONTENT_URI = BASE_CONTENT_URI.buildUpon()
                .appendPath(PATH_MOVIES)
                .build();
        // Table and columns name.
        public static final String TABLE_NAME = "movies";
        public static final String COLUMN_MOVIE_TITLE = "movie_title";
        public static final String COLUMN_MOVIE_POSTER_PATH = "movie_poster";
        public static final String COLUMN_RELEASE_DATE = "release_date";
        public static final String COLUMN_USER_RATING = "user_rating";
        public static final String COLUMN_SYNOPSIS = "synopsis";
        public static final String COLUMN_MOVIE_ID = "movie_id";

        /*
        Table sample:

        movies
         - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
        | _id |  movie_title  | movie_poster | release_date | user_rating | synopsis | movie_id |
         - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
        |  1  | Black Panther | /uxzz....jpg |  13-02-2018  |     7.3     | King.... |  284054  |
         - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
        |  2  |  Red Sparrow  | /vLCo....jpg |  01-03-2018  |     6.4     | Prim.... |  401981  |
         - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
        .
        .
        .
         - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
        |  20 |    Rampage    | /30oX....jpg |  12-04-2018  |     5.9     | Prim.... |  427641  |
         - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -

         */

        /**
         * We use this method to append the movie id to the end of the uri.
         * @param id the id of the movie that we get from the TMDB API.
         * @return the uri with the movie id added to it's end.
         */
        public static Uri buildMoviesUriWithId(int id) {
            return CONTENT_URI.buildUpon()
                    .appendPath(Integer.toString(id))
                    .build();
        }
    }
}

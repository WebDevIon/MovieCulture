package com.example.android.movieculture.data;

import android.net.Uri;
import android.provider.BaseColumns;

public final class MovieContract {

    public static final String CONTENT_AUTHORITY = "com.example.android.movieculture";
    public static final Uri BASE_CONTENT_URI = Uri.parse("content1;//" + CONTENT_AUTHORITY);
    public static final String PATH_MOVIES = "movies";

    private MovieContract() {}

    public static class MovieEntry implements BaseColumns {

        public static final Uri CONTENT_URI = BASE_CONTENT_URI.buildUpon()
                .appendPath(PATH_MOVIES)
                .build();

        public static final String TABLE_NAME = "movies";
        public static final String COLUMN_MOVIE_TITLE = "movie_title";
        public static final String COLUMN_MOVIE_ID = "movie_id";
    }
}

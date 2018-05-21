package com.example.android.movieculture.data;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

/**
 * This is the custom content provider which will provide access to the movies database.
 */
public class MovieProvider extends ContentProvider {

    // Code for the entire movies table.
    private static final int CODE_MOVIE = 100;
    // Code for a single entry in the movies table which has a unique id.
    private static final int CODE_MOVIE_ID = 101;

    private static final UriMatcher sUriMatcher = buildUriMatcher();
    private MovieDbHelper mOpenHelper;

    /**
     * This UriMatcher will check and see which path we want to access.
     * @return the final matcher corresponding to the selected path.
     */
    public static UriMatcher buildUriMatcher() {
        final UriMatcher matcher = new UriMatcher(UriMatcher.NO_MATCH);
        final String authority = MovieContract.CONTENT_AUTHORITY;

        matcher.addURI(authority, MovieContract.PATH_MOVIES, CODE_MOVIE);
        matcher.addURI(authority, MovieContract.PATH_MOVIES + "/#", CODE_MOVIE_ID);

        return matcher;
    }

    /**
     * Here we initialise the MovieDbHelper.
     * @return we return true after the MovieDbHelper was initialised.
     */
    @Override
    public boolean onCreate() {
        mOpenHelper = new MovieDbHelper(getContext());
        return true;
    }

    /**
     * This method will handle query requests.
     * @param uri the uri that was passed by the activity accessing the database.
     * @param projection the columns that we want to select.
     * @param selection the filter that declares which rows to return.
     * @param selectionArgs the search parameters that will find the entry which will be queried.
     * @param sortOrder the sort order of the queried data.
     * @return the cursor which contains the queried data.
     */
    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection, @Nullable String[] selectionArgs, @Nullable String sortOrder) {

        Cursor cursor;

        switch (sUriMatcher.match(uri)) {
            // Here we return a specific id from the database.
            case CODE_MOVIE_ID: {
                String movieId = uri.getLastPathSegment();
                cursor = mOpenHelper.getReadableDatabase().query(
                        MovieContract.MovieEntry.TABLE_NAME,
                        new String[]{MovieContract.MovieEntry.COLUMN_MOVIE_ID},
                        MovieContract.MovieEntry.COLUMN_MOVIE_ID + " LIKE ? ",
                        new String[]{movieId},
                        null,
                        null,
                        sortOrder);
                break;
            }
            // Here we return all the movies from the database.
            case CODE_MOVIE: {
                cursor = mOpenHelper.getReadableDatabase().query(
                        MovieContract.MovieEntry.TABLE_NAME,
                        projection,
                        selection,
                        selectionArgs,
                        null,
                        null,
                        sortOrder);
                break;
            }
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }

        cursor.setNotificationUri(getContext().getContentResolver(), uri);
        return cursor;
    }

    /**
     * We do not need this method so we will not implement it.
     * @param uri the uri that was passed by the activity accessing the database.
     * @return we do not return anything.
     */
    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        throw new RuntimeException("Get type not implemented.");
    }

    /**
     * This method will insert a new row into the database.
     * @param uri the uri that was passed by the activity accessing the database.
     * @param values the values that are used to update the data.
     * @return the new uri that was created for the data.
     */
    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues values) {
        final SQLiteDatabase db = mOpenHelper.getWritableDatabase();
        Uri returnUri;

        switch (sUriMatcher.match(uri)) {
            case CODE_MOVIE:
                long _id = db.insert(MovieContract.MovieEntry.TABLE_NAME, null, values);
                if (_id > 0) {
                    returnUri = ContentUris.withAppendedId(MovieContract.MovieEntry.CONTENT_URI, _id);
                } else {
                    throw new android.database.SQLException("Failed to insert row into: " + uri);
                }
                break;
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }

        // Here we notify the resolver that the uri has been changed.
        getContext().getContentResolver().notifyChange(uri, null);
        return returnUri;
    }

    /**
     * This method will delete a row in the database.
     * @param uri the uri that was passed by the activity accessing the database.
     * @param selection the filter that declares which rows to return.
     * @param selectionArgs the search parameters that will find the entry which will be deleted.
     * @return the number of rows that we deleted.
     */
    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {
        int numRowsDeleted;
        // Here we check to se if there is a selection passed, if not then we delete all the data
        // present in the table and return the number of rows deleted.
        if (null == selection) selection = "1";

        switch (sUriMatcher.match(uri)){
            // Here we delete a row with a specific movie id.
            case CODE_MOVIE_ID:
                String movieId = uri.getLastPathSegment();
                numRowsDeleted = mOpenHelper.getWritableDatabase().delete(
                        MovieContract.MovieEntry.TABLE_NAME,
                        MovieContract.MovieEntry.COLUMN_MOVIE_ID + " LIKE ? ",
                        new String[]{movieId});
                break;
            // Here we delete a row in the database or all the rows if we pass null to
            // selection and selectionArgs.
            case CODE_MOVIE:
                numRowsDeleted = mOpenHelper.getWritableDatabase().delete(
                        MovieContract.MovieEntry.TABLE_NAME,
                        selection,
                        selectionArgs);
                break;
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }

        // If there were any rows deleted we notify that a change has occurred to this URI.
        if (numRowsDeleted != 0) {
            getContext().getContentResolver().notifyChange(uri, null);
        }

        // We return the number of rows deleted.
        return numRowsDeleted;
    }

    /**
     * We do not need this method so we will not implement it.
     * @param uri the uri that was passed by the activity accessing the database.
     * @param values the values that are used to update the data.
     * @param selection the filter that declares which rows to return.
     * @param selectionArgs the search parameters that will find the entry which will be updated.
     * @return we do not return anything.
     */
    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues values, @Nullable String selection, @Nullable String[] selectionArgs) {
        throw new RuntimeException("Update not implemented.");
    }
}

package com.giroux.kevin.kevingirouxportfolio.database;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.giroux.kevin.kevingirouxportfolio.Utils.Utility;

/**
 * Created by kevin on 06/06/2016. Kevin Giroux Portfolio
 */

public class MovieContentProvider extends ContentProvider {

    private MovieDbHelper movieDbHelper;
    private static final UriMatcher sUriMatcher = buildUriMatcher();
    static final int MOVIE = 100;
    static final int MOVIE_ALL = 101;
    static final int MOVIE_BY_FAVORITE = 102;
    static final int FAVORITE = 150;

    @Override
    public boolean onCreate() {
        movieDbHelper = new MovieDbHelper(getContext());
        return true;
    }

    static UriMatcher buildUriMatcher() {


        final UriMatcher matcher = new UriMatcher(UriMatcher.NO_MATCH);
        final String authority = MovieContractor.CONTENT_AUTHORITY;
        matcher.addURI(authority, MovieContractor.PATH_MOVIE, MOVIE);
        matcher.addURI(authority, MovieContractor.PATH_FAVORITE + "/*", MOVIE_ALL);
        matcher.addURI(authority, MovieContractor.PATH_FAVORITE, MOVIE_BY_FAVORITE);
        return matcher;
    }



    @Nullable
    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {

        final int match = sUriMatcher.match(uri);
        switch (match){
            case MOVIE :
                break;
            case MOVIE_ALL :
                break;
            case MOVIE_BY_FAVORITE :
                break;

        }


        return null;
    }

    @Nullable
    @Override
    public String getType(@NonNull final Uri uri) {
        final int match = sUriMatcher.match(uri);
        switch (match) {
            case MOVIE:
                return MovieContractor.MovieEntry.CONTENT_TYPE;
            case MOVIE_ALL:
                return MovieContractor.FavoriteEntry.CONTENT_TYPE;
            case MOVIE_BY_FAVORITE:
                return MovieContractor.FavoriteEntry.CONTENT_TYPE;
            default:
                throw new UnsupportedOperationException("Unkown uri : " + uri);
        }
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, ContentValues values) {
        final SQLiteDatabase db = movieDbHelper.getWritableDatabase();
        final int match = sUriMatcher.match(uri);
        Uri returnUri;

        switch (match) {
            case MOVIE: {
                long _id = db.insert(MovieContractor.MovieEntry.TABLE_NAME, null, values);
                if (_id > 0)
                    returnUri = MovieContractor.MovieEntry.buildMovieUri(_id);
                else
                    throw new android.database.SQLException("Failed to insert row into " + uri);
                break;
            }
            case FAVORITE: {
                normalizeDate(values);
                long _id = db.insert(MovieContractor.FavoriteEntry.TABLE_NAME, null, values);
                if (_id > 0) {
                    returnUri = MovieContractor.FavoriteEntry.buildFavorityById(_id);
                } else {
                    throw new android.database.SQLException("Failed to insert row into " + uri);
                }
                break;
            }
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }
        if(getContext() != null && getContext().getContentResolver() != null)
            getContext().getContentResolver().notifyChange(uri, null);
        db.close();
        return returnUri;
    }

    private Cursor getMovieById(Uri uri, String[] projection, String sortOrder) {




        return null;
    }

    private Cursor getMovieAllMovie() {
        SQLiteQueryBuilder builder = new SQLiteQueryBuilder();
        builder.setTables(MovieContractor.MovieEntry.TABLE_NAME);
        return builder.query(movieDbHelper.getReadableDatabase(),null,null,null,null,null,null);
    }

    private Cursor getMovieByFavorite(Uri uri, String[] projection, String sortOrder) {
        SQLiteQueryBuilder builder = new SQLiteQueryBuilder();
        builder.setTables(MovieContractor.FavoriteEntry.TABLE_NAME + " INNER JOIN "
                +  MovieContractor.MovieEntry.TABLE_NAME + "ON "+ MovieContractor.FavoriteEntry.COLUMN_MOVIE_ID + " = " + MovieContractor.MovieEntry._ID);
        return  builder.query(movieDbHelper.getReadableDatabase(),projection,null,null,null,null,null);
    }



    @Override
    public int delete(@NonNull Uri uri, String selection, String[] selectionArgs) {
        return 0;
    }

    @Override
    public int update(@NonNull Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        return 0;
    }

    private void normalizeDate(ContentValues values){
        if(values.containsKey(MovieContractor.FavoriteEntry.COLUMN_FAVORITE_DATE)){
            long date = values.getAsLong(MovieContractor.FavoriteEntry.COLUMN_FAVORITE_DATE);
            values.put(MovieContractor.FavoriteEntry.COLUMN_FAVORITE_DATE, Utility.normaliseDate(date));
        }
    }
}

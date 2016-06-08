package com.giroux.kevin.kevingirouxportfolio.database;

import android.annotation.TargetApi;
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
    static final int MOVIE_LASTEST = 102;
    static final int MOVIE_BY_FAVORITE = 103;
    static final int FAVORITE = 150;


    private static final String sMovieById =
            MovieContractor.MovieEntry.TABLE_NAME +
                    "." + MovieContractor.MovieEntry._ID + " = ? ";


    @Override
    public boolean onCreate() {
        movieDbHelper = new MovieDbHelper(getContext());
        return true;
    }

    private static UriMatcher buildUriMatcher() {


        final UriMatcher matcher = new UriMatcher(UriMatcher.NO_MATCH);
        final String authority = MovieContractor.CONTENT_AUTHORITY;
        matcher.addURI(authority, MovieContractor.PATH_MOVIE, MOVIE);
        matcher.addURI(authority, MovieContractor.PATH_MOVIE + "/lastest",MOVIE_LASTEST);
        matcher.addURI(authority, MovieContractor.PATH_FAVORITE + "/*", MOVIE_ALL);
        matcher.addURI(authority, MovieContractor.PATH_FAVORITE, FAVORITE);
        matcher.addURI(authority, MovieContractor.PATH_FAVORITE + "/*", MOVIE_BY_FAVORITE);
        return matcher;
    }



    @Nullable
    @Override
    public Cursor query(@NonNull  Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {

        final int match = sUriMatcher.match(uri);
        Cursor retCursor;
        switch (match){

            case MOVIE :
                retCursor = getMovieById(uri,projection);
                break;
            case MOVIE_ALL :
                retCursor = getMovieAllMovie();
                break;

            case MOVIE_LASTEST :
                retCursor = getLastestMovieOrder(uri,projection);
                break;

            case MOVIE_BY_FAVORITE :
                retCursor = getMovieByFavorite(projection);
                break;

            case FAVORITE :
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);


        }
        if(getContext() != null && getContext().getContentResolver() != null)
            retCursor.setNotificationUri(getContext().getContentResolver(),uri);

        return retCursor;
    }

    @Nullable
    @Override
    public String getType(@NonNull final Uri uri) {
        final int match = sUriMatcher.match(uri);
        switch (match) {
            case MOVIE:
                return MovieContractor.MovieEntry.CONTENT_TYPE;
            case MOVIE_ALL:
                return MovieContractor.MovieEntry.CONTENT_TYPE;
            case MOVIE_LASTEST :
                return MovieContractor.MovieEntry.CONTENT_TYPE;
            case MOVIE_BY_FAVORITE:
                return MovieContractor.MovieEntry.CONTENT_TYPE;
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

    private Cursor getMovieById(Uri uri, String[] projection) {
        SQLiteQueryBuilder builder = new SQLiteQueryBuilder();
        builder.setTables(MovieContractor.MovieEntry.TABLE_NAME);
        String[] selectionArgs;
        String selection;
        long id = MovieContractor.MovieEntry.getIdFromUri(uri);
        if(id == 0){
            selection = null;
            selectionArgs = null;
        }else{
            selection = sMovieById;
            selectionArgs = new String[] {Long.toString(id)};
        }

        return builder.query(movieDbHelper.getReadableDatabase(),projection,selection,selectionArgs,null,null,null);
    }

    private Cursor getMovieAllMovie() {
        SQLiteQueryBuilder builder = new SQLiteQueryBuilder();
        builder.setTables(MovieContractor.MovieEntry.TABLE_NAME);
        return builder.query(movieDbHelper.getReadableDatabase(),null,null,null,null,null,null);
    }

    private Cursor getMovieByFavorite(String[] projection) {
        SQLiteQueryBuilder builder = new SQLiteQueryBuilder();
        builder.setTables(MovieContractor.FavoriteEntry.TABLE_NAME + " INNER JOIN "
                +  MovieContractor.MovieEntry.TABLE_NAME + "ON "+ MovieContractor.FavoriteEntry.COLUMN_MOVIE_ID + " = " + MovieContractor.MovieEntry._ID);
        return  builder.query(movieDbHelper.getReadableDatabase(),projection,null,null,null,null,null);
    }

    private Cursor getLastestMovieOrder(Uri uri, String [] projection){
        SQLiteQueryBuilder builder = new SQLiteQueryBuilder();
        builder.setTables(MovieContractor.MovieEntry.TABLE_NAME);
        return builder.query(movieDbHelper.getReadableDatabase(),projection,null,null,null,null, MovieContractor.MovieEntry.COLUMN_MOVIE_DATE_QUERY_MOVIEDB + " ASC", "20");


    }



    @Override
    public int delete(@NonNull Uri uri, String selection, String[] selectionArgs) {
        final SQLiteDatabase db = movieDbHelper.getWritableDatabase();
        final int match = sUriMatcher.match(uri);
        int rowsDeleted;

        if (null == selection) selection = "1";

        switch (match) {
            case FAVORITE: {
                rowsDeleted = db.delete(MovieContractor.FavoriteEntry.TABLE_NAME, selection, selectionArgs);
                break;
            }
            case MOVIE: {
                rowsDeleted = db.delete(MovieContractor.MovieEntry.TABLE_NAME, selection, selectionArgs);
                break;
            }
            default:
                throw new UnsupportedOperationException("Unkown URI : " + uri);
        }
        if (rowsDeleted != 0 && getContext() != null && getContext().getContentResolver() != null)
            getContext().getContentResolver().notifyChange(uri, null);
        db.close();
        return rowsDeleted;
    }

    @Override
    public int update(@NonNull Uri uri, ContentValues values, String selection, String[] selectionArgs) {

        final SQLiteDatabase db = movieDbHelper.getWritableDatabase();
        final int match = sUriMatcher.match(uri);
        int rowsUpdated;

        switch (match){
            case MOVIE :
                rowsUpdated = db.update(MovieContractor.MovieEntry.TABLE_NAME,values,selection,selectionArgs);
                break;

            case FAVORITE :
                rowsUpdated = db.update(MovieContractor.FavoriteEntry.TABLE_NAME,values,selection,selectionArgs);
                break;
            default:
                throw new UnsupportedOperationException("Unknown URI : " + uri);
        }
        if (rowsUpdated != 0 && getContext() != null && getContext().getContentResolver() != null) {
            getContext().getContentResolver().notifyChange(uri, null);
        }
        db.close();
        return rowsUpdated;
    }

    private void normalizeDate(ContentValues values){
        if(values.containsKey(MovieContractor.FavoriteEntry.COLUMN_FAVORITE_DATE)){
            long date = values.getAsLong(MovieContractor.FavoriteEntry.COLUMN_FAVORITE_DATE);
            values.put(MovieContractor.FavoriteEntry.COLUMN_FAVORITE_DATE, Utility.normaliseDate(date));
        }
    }

    @Override
    public int bulkInsert(@NonNull  Uri uri, @NonNull ContentValues[] values) {
        final SQLiteDatabase db = movieDbHelper.getWritableDatabase();
        final int match = sUriMatcher.match(uri);
        switch (match) {
            case MOVIE:
                db.beginTransaction();
                int returnCount = 0;
                try {
                    for (ContentValues value : values) {
                        normalizeDate(value);
                        long _id = db.insert(MovieContractor.MovieEntry.TABLE_NAME, null, value);
                        if (_id != -1) {
                            returnCount++;
                        }
                    }
                    db.setTransactionSuccessful();
                } finally {
                    db.endTransaction();
                }
                if(getContext() != null && getContext().getContentResolver() != null)
                    getContext().getContentResolver().notifyChange(uri, null);
                return returnCount;
            default:
                return super.bulkInsert(uri, values);
        }
    }


    @Override
    @TargetApi(11)
    public void shutdown() {
        movieDbHelper.close();
        super.shutdown();
    }
}

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

import com.giroux.kevin.kevingirouxportfolio.utils.Utility;

/**
 * Created by kevin on 06/06/2016. Kevin Giroux Portfolio
 */

public class MovieContentProvider extends ContentProvider {

    private MovieDbHelper movieDbHelper;
    private static final UriMatcher sUriMatcher = buildUriMatcher();
    private static final int MOVIE = 100;
    private static final int MOVIE_ALL = 101;
    private static final int MOVIE_LASTEST = 102;
    private static final int MOVIE_BY_FAVORITE = 103;
    private static final int MOVIE_ID = 104;
    private static final int MOVIE_SETTINGS = 105;
    private static final int FAVORITE = 150;
    private static final int TRAILER = 200;
    private static final int TRAILER_BY_MOVIE = 201;
    private static  final int REVIEW = 300;
    private static final int REVIEW_BY_MOVIE = 301;


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
        matcher.addURI(authority, MovieContractor.PATH_MOVIE , MOVIE);
        matcher.addURI(authority, MovieContractor.PATH_MOVIE + "/#", MOVIE_ID);
        matcher.addURI(authority, MovieContractor.PATH_MOVIE + "/settings", MOVIE_SETTINGS);
        matcher.addURI(authority, MovieContractor.PATH_MOVIE + "/lastest",MOVIE_LASTEST);
        matcher.addURI(authority, MovieContractor.PATH_FAVORITE + "/*", MOVIE_ALL);
        matcher.addURI(authority, MovieContractor.PATH_FAVORITE, FAVORITE);
        matcher.addURI(authority, MovieContractor.PATH_FAVORITE + "/*", MOVIE_BY_FAVORITE);
        matcher.addURI(authority, MovieContractor.PATH_TRAILER,TRAILER);
        matcher.addURI(authority, MovieContractor.PATH_TRAILER +"/*",TRAILER_BY_MOVIE);
        matcher.addURI(authority, MovieContractor.PATH_REVIEW,REVIEW);
        matcher.addURI(authority, MovieContractor.PATH_REVIEW +"/*",REVIEW_BY_MOVIE);
        return matcher;
    }



    @Nullable
    @Override
    public Cursor query(@NonNull  Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {

        final int match = sUriMatcher.match(uri);
        Cursor retCursor;
        switch (match){

            case MOVIE_SETTINGS :
                retCursor = getMovieBySetting(projection,selection,selectionArgs,sortOrder);
                break;
            case MOVIE_ALL :
                retCursor = getMovieAllMovie();
                break;
            case MOVIE_LASTEST :
                retCursor = getLastestMovieOrder(projection);
                break;
            case MOVIE_BY_FAVORITE :
                retCursor = getMovieByFavorite(projection);
                break;
            case MOVIE_ID :
                retCursor = getMovieById(uri,projection);
                break;
            case TRAILER:
                retCursor = getTrailerByMovie(projection,selection,selectionArgs);
                break;
            case REVIEW:
                retCursor = getReviewByMovie(projection,selection,selectionArgs);
                break;
            case FAVORITE :
            case MOVIE :
            case REVIEW_BY_MOVIE:
            case TRAILER_BY_MOVIE:
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
            case TRAILER :
                return  MovieContractor.TrailerEntry.CONTENT_TYPE;
            case TRAILER_BY_MOVIE :
                return MovieContractor.TrailerEntry.CONTENT_TYPE;
            case MOVIE_SETTINGS :
                return MovieContractor.MovieEntry.CONTENT_TYPE;
            case REVIEW:
                return MovieContractor.TrailerEntry.CONTENT_TYPE;
            case REVIEW_BY_MOVIE:
                return MovieContractor.ReviewEntry.CONTENT_TYPE;

            default:
                throw new UnsupportedOperationException("Unkown uri : " + uri);
        }
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, ContentValues values) {
        final SQLiteDatabase db = movieDbHelper.getWritableDatabase();
        final int match = sUriMatcher.match(uri);
        long _id;
        Uri returnUri;

        switch (match) {
            case MOVIE: {
                _id = db.insert(MovieContractor.MovieEntry.TABLE_NAME, null, values);
                if (_id > 0)
                    returnUri = MovieContractor.MovieEntry.buildMovieUri(_id);
                else
                    throw new android.database.SQLException("Failed to insert row into " + uri);
                break;
            }
            case FAVORITE: {
                normalizeDate(values);
                 _id = db.insert(MovieContractor.FavoriteEntry.TABLE_NAME, null, values);
                if (_id > 0) {
                    returnUri = MovieContractor.FavoriteEntry.buildFavorityById(_id);
                } else {
                    throw new android.database.SQLException("Failed to insert row into " + uri);
                }
                break;
            }
            case TRAILER :
                _id = db.insert(MovieContractor.TrailerEntry.TABLE_NAME,null,values);
                if(_id>0){
                    returnUri = MovieContractor.TrailerEntry.buildTrailerUri(_id);
                }else{
                    throw new android.database.SQLException("Failed to insert row into" + uri);
                }
                break;

            case REVIEW :
                _id = db.insert(MovieContractor.ReviewEntry.TABLE_NAME,null,values);
                if(_id>0){
                    returnUri = MovieContractor.TrailerEntry.buildTrailerUri(_id);
                }else{
                    throw new android.database.SQLException("Failed to insert row into" + uri);
                }
                break;
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }
        if(getContext() != null && getContext().getContentResolver() != null)
            getContext().getContentResolver().notifyChange(uri, null);
        return returnUri;
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
            case TRAILER :{
                rowsDeleted = db.delete(MovieContractor.TrailerEntry.TABLE_NAME,selection,selectionArgs);
                break;
            }
            case REVIEW :{
                rowsDeleted = db.delete(MovieContractor.ReviewEntry.TABLE_NAME,selection,selectionArgs);
                break;
            }
            default:
                throw new UnsupportedOperationException("Unkown URI : " + uri);
        }
        if (rowsDeleted != 0 && getContext() != null && getContext().getContentResolver() != null)
            getContext().getContentResolver().notifyChange(uri, null);
        return rowsDeleted;
    }

    @Override
    public int update(@NonNull Uri uri, ContentValues values, String selection, String[] selectionArgs) {

        final SQLiteDatabase db = movieDbHelper.getWritableDatabase();
        final int match = sUriMatcher.match(uri);
        int rowsUpdated;

        switch (match){
            case MOVIE :
            case MOVIE_ID :
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
        int returnCount = 0;

        switch (match) {
            case MOVIE:
                db.beginTransaction();
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

            case TRAILER :
                db.beginTransaction();
                try{
                    for(ContentValues value : values){
                        long _id = db.insert(MovieContractor.TrailerEntry.TABLE_NAME,null,value);
                        if(_id!=-1){
                            returnCount++;
                        }
                    }
                    db.setTransactionSuccessful();
                }finally {
                    db.endTransaction();
                }
                return  returnCount;
            case REVIEW :
                db.beginTransaction();
                try{
                    for(ContentValues value : values){
                        long _id = db.insert(MovieContractor.ReviewEntry.TABLE_NAME,null,value);
                        if(_id!=-1){
                            returnCount++;
                        }
                    }
                    db.setTransactionSuccessful();
                }finally {
                    db.endTransaction();
                }
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

    private Cursor getMovieBySetting( String[] projection, String selection, String[] selectionArgs, String sortOrder){
        SQLiteQueryBuilder builder = new SQLiteQueryBuilder();
        builder.setTables(MovieContractor.MovieEntry.TABLE_NAME);
        return builder.query(movieDbHelper.getReadableDatabase(),projection,selection,selectionArgs,null,null,sortOrder);
    }

    private Cursor getMovieById(Uri uri, String [] projection){
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
        builder.setTables(MovieContractor.MovieEntry.TABLE_NAME + " INNER JOIN " + MovieContractor.FavoriteEntry.TABLE_NAME);
        builder.setTables(MovieContractor.FavoriteEntry.TABLE_NAME + " INNER JOIN "
                +  MovieContractor.MovieEntry.TABLE_NAME + " ON "+ MovieContractor.FavoriteEntry.COLUMN_MOVIE_ID + " = " + MovieContractor.MovieEntry.TABLE_NAME+"."+MovieContractor.MovieEntry._ID);
        return  builder.query(movieDbHelper.getReadableDatabase(),projection,null,null,null,null,null);
    }

    private Cursor getLastestMovieOrder(String [] projection){
        SQLiteQueryBuilder builder = new SQLiteQueryBuilder();
        builder.setTables(MovieContractor.MovieEntry.TABLE_NAME);
        return builder.query(movieDbHelper.getReadableDatabase(),projection,null,null,null,null, MovieContractor.MovieEntry.COLUMN_MOVIE_DATE_QUERY_MOVIEDB + " ASC", "20");
    }

    private  Cursor getTrailerByMovie(String []projection, String selection, String[] selectionArg){
        SQLiteQueryBuilder builder = new SQLiteQueryBuilder();
        builder.setTables(MovieContractor.TrailerEntry.TABLE_NAME);
        return builder.query(movieDbHelper.getReadableDatabase(),projection,selection,selectionArg,null,null,null);
    }

    private  Cursor getReviewByMovie(String []projection, String selection, String[] selectionArg){
        SQLiteQueryBuilder builder = new SQLiteQueryBuilder();
        builder.setTables(MovieContractor.ReviewEntry.TABLE_NAME);
        return builder.query(movieDbHelper.getReadableDatabase(),projection,selection,selectionArg,null,null,null);
    }
}

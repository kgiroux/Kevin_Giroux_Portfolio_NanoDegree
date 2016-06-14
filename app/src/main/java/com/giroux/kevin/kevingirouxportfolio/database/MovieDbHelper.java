package com.giroux.kevin.kevingirouxportfolio.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.giroux.kevin.kevingirouxportfolio.database.MovieContractor.FavoriteEntry;
import com.giroux.kevin.kevingirouxportfolio.database.MovieContractor.MovieEntry;
import com.giroux.kevin.kevingirouxportfolio.database.MovieContractor.TrailerEntry;
import com.giroux.kevin.kevingirouxportfolio.database.MovieContractor.ReviewEntry;
/**
 * Created by kevin on 06/06/2016. Kevin Giroux Portfolio
 */

public class MovieDbHelper extends SQLiteOpenHelper {

    static final String DATABASE_NAME = "movie.db";
    private static final int DATABASE_VERSION = 1;

    public MovieDbHelper(Context context){
        super(context,DATABASE_NAME,null,DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        final String SQL_CREATE_FAVORITE_TABLE = "CREATE TABLE " + FavoriteEntry.TABLE_NAME + "("+
                FavoriteEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                FavoriteEntry.COLUMN_FAVORITE_DATE + " REAL NOT NULL," +
                FavoriteEntry.COLUMN_MOVIE_ID + " INTEGER NOT NULL," +
                " FOREIGN KEY (" + FavoriteEntry.COLUMN_MOVIE_ID +") REFERENCES " +
                MovieEntry.TABLE_NAME +"(" + MovieEntry._ID + "), " +
                "UNIQUE (" + FavoriteEntry._ID + ", " + FavoriteEntry.COLUMN_MOVIE_ID + ")" +
                " ON CONFLICT REPLACE )";

        final String SQL_CREATE_MOVIE_TABLE = "CREATE TABLE " + MovieEntry.TABLE_NAME + "("+
                MovieEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                MovieEntry.COLUMN_MOVIE_ORIGINAL_TITLE + " TEXT NOT NULL, "+
                MovieEntry.COLUMN_MOVIE_OVERVIEW + " TEXT NOT NULL, " +
                MovieEntry.COLUMN_MOVIE_POSTER_PATH + " TEXT NOT NULL, " +
                MovieEntry.COLUMN_MOVIE_RELEASE_DATE + " TEXT NOT NULL, "+
                MovieEntry.COLUMN_MOVIE_TITLE + " TEXT NOT NULL, " +
                MovieEntry.COLUMN_MOVIE_USER_RATING + " REAL NOT NULL, "  +
                MovieEntry.COLUMN_MOVIE_BACKDROP_PATH + " TEXT NOT NULL, " +
                MovieEntry.COLUMN_MOVIE_SETTING + " TEXT NOT NULL, " +
                MovieEntry.COLUMN_MOVIE_POSTER + " BLOP, " +
                MovieEntry.COLUMN_MOVIE_DATE_QUERY_MOVIEDB + " REAL NOT NULL, " +
                MovieEntry.COLUMN_MOVIE_POPULARITY + " REAL NOT NULL, " +
                MovieEntry.COLUMN_MOVIE_DURATION + " INTEGER, " +
                MovieEntry.COLUMN_MOVIE_MARK_AS_FAVORITE + " BOOLEAN,"+
                "UNIQUE (" + MovieEntry.COLUMN_MOVIE_ORIGINAL_TITLE + ", " + MovieEntry.COLUMN_MOVIE_TITLE + ")" +
                "  ON CONFLICT REPLACE )";

        final String SQL_CREATE_TRAILER_TABLE = "CREATE TABLE " + TrailerEntry.TABLE_NAME + "(" +
                TrailerEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                TrailerEntry.COLUMN_TRAILER_IS_YOUTUBE + " BOOLEAN, " +
                TrailerEntry.COLUMN_TRAILER_KEY_YOUTUBE + " TEXT NOT NULL, " +
                TrailerEntry.COLUMN_TRAILER_NAME + " TEXT NOT NULL, " +
                TrailerEntry.COLUMN_TRAILER_TYPE + " TEXT NOT NULL, " +
                TrailerEntry.COLUMN_TRAILER_MOVIE_ID + " INTEGER NOT NULL," +
                " FOREIGN KEY (" + TrailerEntry.COLUMN_TRAILER_MOVIE_ID +")  REFERENCES " +
                MovieEntry.TABLE_NAME + "(" + MovieEntry._ID + "), " +
                "UNIQUE (" +TrailerEntry.COLUMN_TRAILER_KEY_YOUTUBE + ")" +
                "ON CONFLICT REPLACE )";

        final String SQL_CREATE_REVIEW_TABLE = "CREATE TABLE " + ReviewEntry.TABLE_NAME + "(" +
                ReviewEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                ReviewEntry.COLUMN_REVIEWS_CONTENT + " TEXT NOT NULL, " +
                ReviewEntry.COLUMN_REVIEWS_NAME + " TEXT NOT NULL, " +
                ReviewEntry.COLUMN_REVIEWS_ID_MOVIE + " INTEGER NOT NULL, " +
                " FOREIGN KEY (" + ReviewEntry.COLUMN_REVIEWS_ID_MOVIE +")  REFERENCES " +
                MovieEntry.TABLE_NAME + "(" + MovieEntry._ID + "))";

        db.execSQL(SQL_CREATE_MOVIE_TABLE);
        db.execSQL(SQL_CREATE_FAVORITE_TABLE);
        db.execSQL(SQL_CREATE_TRAILER_TABLE);
        db.execSQL(SQL_CREATE_REVIEW_TABLE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (oldVersion < newVersion) {
            db.execSQL("DROP TABLE IF EXISTS" + FavoriteEntry.TABLE_NAME);
            db.execSQL("DROP TABLE IF EXISTS" + MovieEntry.TABLE_NAME);
            db.execSQL("DROP TABLE IF EXISTS" + TrailerEntry.TABLE_NAME);
            db.execSQL("DROP TABLE IF EXISTS" + ReviewEntry.TABLE_NAME);
        }
        this.onCreate(db);
    }


}

package com.giroux.kevin.kevingirouxportfolio.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.giroux.kevin.kevingirouxportfolio.database.MovieContractor.FavoriteEntry;
import com.giroux.kevin.kevingirouxportfolio.database.MovieContractor.MovieEntry;
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
                "UNIQUE (" + FavoriteEntry.COLUMN_MOVIE_ID + ", " + FavoriteEntry.COLUMN_MOVIE_ID + ")" +
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
                "UNIQUE (" + MovieEntry.COLUMN_MOVIE_ORIGINAL_TITLE + ", " + MovieEntry.COLUMN_MOVIE_TITLE + ")" +
                "  ON CONFLICT REPLACE )";


        db.execSQL(SQL_CREATE_MOVIE_TABLE);
        db.execSQL(SQL_CREATE_FAVORITE_TABLE);


    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (oldVersion < newVersion) {
            db.execSQL("DROP TABLE IF EXISTS" + FavoriteEntry.TABLE_NAME);
            db.execSQL("DROP TABLE IF EXISTS" + MovieEntry.TABLE_NAME);
        }
        this.onCreate(db);
    }


}
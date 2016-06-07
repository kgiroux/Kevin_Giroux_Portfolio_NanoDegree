package com.giroux.kevin.kevingirouxportfolio.database;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created by kevin on 06/06/2016. Kevin Giroux Portfolio
 */

public class MovieContractor {

    // Content authority
    public static final String CONTENT_AUTHORITY = "com.giroux.kevin.kevingirouxportfolio";
    // Base URL
    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);
    // POSSIBLE PATH
    public static final String PATH_MOVIE = "movie";

    public static final String PATH_FAVORITE = "favorite";

    public static final class MovieEntry implements BaseColumns {
        // Table name
        public static final String TABLE_NAME = "movie";
        // Movie original title
        public static final String COLUMN_MOVIE_ORIGINAL_TITLE = "original_title";
        // Movie title
        public static final String COLUMN_MOVIE_TITLE = "title";
        // Movie overview
        public static final String COLUMN_MOVIE_OVERVIEW = "overview";
        // Movie poster path
        public static final String COLUMN_MOVIE_POSTER_PATH = "poster_path";
        // Movie release date
        public static final String COLUMN_MOVIE_RELEASE_DATE = "release_date";
        // Movie userRating
        public static final String COLUMN_MOVIE_USER_RATING = "user_rating";

        // Content URI
        public static final Uri CONTENT_URI = BASE_CONTENT_URI.buildUpon().appendPath(PATH_MOVIE).build();

        public static final String CONTENT_TYPE =
                ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_MOVIE;
        public static final String CONTENT_ITEM_TYPE =
                ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_MOVIE;

        public static Uri buildMovieUri(long id) {
            return ContentUris.withAppendedId(CONTENT_URI, id);
        }
    }

    public static final class FavoriteEntry implements BaseColumns {
        public static final String TABLE_NAME = "favorite";
        public static final String COLUMN_FAVORITE_DATE = "favorite_date";
        public static final String COLUMN_MOVIE_ID = "id_movie";

        public static final Uri CONTENT_URI = BASE_CONTENT_URI.buildUpon().appendPath(PATH_FAVORITE).build();

        public static final String CONTENT_TYPE =
                ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_FAVORITE;
        public static final String CONTENT_ITEM_TYPE =
                ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_FAVORITE;

        public static Uri buildFavorityById(long id){
            return ContentUris.withAppendedId(CONTENT_URI,id);
        }
        
        public static Uri buildFavoriteByIdMovie(long idMovie){
            return ContentUris.withAppendedId(CONTENT_URI,idMovie);
        }

    }
}

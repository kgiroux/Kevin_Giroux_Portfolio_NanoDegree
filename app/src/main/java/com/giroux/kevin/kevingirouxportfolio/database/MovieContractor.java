package com.giroux.kevin.kevingirouxportfolio.database;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.database.Cursor;
import android.net.Uri;
import android.provider.BaseColumns;
import android.util.Log;

import com.giroux.kevin.androidhttprequestlibrairy.constants.Constants;
import com.giroux.kevin.kevingirouxportfolio.activity.popularMovies.PopularActivity;
import com.giroux.kevin.kevingirouxportfolio.dto.MovieInformation;

import java.util.ArrayList;
import java.util.List;

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
        // Movie backdropPath
        public static final String COLUMN_MOVIE_BACKDROP_PATH = "backdrop_path";
        // Movie Settings Order
        public static final String COLUMN_MOVIE_SETTING = "movie_setting";
        // Movie picture
        public static final String COLUMN_MOVIE_POSTER = "poster";
        // Movie query Date
        public static final String COLUMN_MOVIE_DATE_QUERY_MOVIEDB = "date_query_moviedb";
        // Movie popularity
        public static final String COLUMN_MOVIE_POPULARITY = "popularity";


        // Content URI
        public static final Uri CONTENT_URI = BASE_CONTENT_URI.buildUpon().appendPath(PATH_MOVIE).build();

        public static final String CONTENT_TYPE =
                ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_MOVIE;
        public static final String CONTENT_ITEM_TYPE =
                ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_MOVIE;

        public static Uri buildMovieUri(long id) {
            return ContentUris.withAppendedId(CONTENT_URI, id);
        }

        public static Uri buildUriForAllMovies(){
            return CONTENT_URI.buildUpon().appendPath("/*").build();
        }

        public static Uri buildUriLastestMovies(){
            return CONTENT_URI.buildUpon().appendEncodedPath("/lastest").build();
        }


        public static long getIdFromUri(Uri uri){
            String idToReturn = uri.getQueryParameter(_ID);
            if(idToReturn != null && idToReturn.length() > 0)
                return Long.parseLong(idToReturn);
            else
                return 0;
        }

        public static List<MovieInformation> getDataFromCursor(Cursor c) {
            List<MovieInformation> movieInformationList = new ArrayList<>();
            if(c != null){
                while(c.moveToNext()){
                    Log.i(Constants.TAG_MOVIE_CONTRACTOR,"Récupération des films");
                    MovieInformation movieInformation = new MovieInformation();
                    movieInformation.setId(c.getInt(PopularActivity.COL_MOVIE_ID));
                    movieInformation.setOriginalTitle(c.getString(PopularActivity.COLUMN_MOVIE_ORIGINAL_TITLE));
                    movieInformation.setTitle(c.getString(PopularActivity.COLUMN_MOVIE_TITLE));
                    movieInformation.setReleaseDate(c.getString(PopularActivity.COLUMN_MOVIE_RELEASE_DATE));
                    movieInformation.setOverView(c.getString(PopularActivity.COLUMN_MOVIE_OVERVIEW));
                    movieInformation.setUserRating(c.getDouble(PopularActivity.COLUMN_MOVIE_USER_RATING));
                    movieInformation.setPosterBitmap(c.getBlob(PopularActivity.COLUMN_MOVIE_POSTER));
                    movieInformation.setPosterPath(c.getString(PopularActivity.COLUMN_MOVIE_POSTER_PATH));
                    movieInformation.setBackdropPath(c.getString(PopularActivity.COLUMN_MOVIE_BACKDROP_PATH));
                    movieInformation.setSettings(c.getString(PopularActivity.COLUMN_MOVIE_SETTING));
                    movieInformation.setPopularity(c.getDouble(PopularActivity.COLUMN_MOVIE_POPULARITY));

                    movieInformationList.add(movieInformation);
                }
            }
            return movieInformationList;
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
            return CONTENT_URI.buildUpon().appendPath(Long.toString(idMovie)).build();
        }

    }
}

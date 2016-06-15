package com.giroux.kevin.kevingirouxportfolio.network;

import android.content.ContentValues;
import android.content.Context;
import android.util.Log;

import com.giroux.kevin.androidhttprequestlibrairy.AndroidHttpRequest;
import com.giroux.kevin.androidhttprequestlibrairy.constants.Constants;
import com.giroux.kevin.kevingirouxportfolio.adapter.TrailerAdapter;
import com.giroux.kevin.kevingirouxportfolio.database.MovieContractor;
import com.giroux.kevin.kevingirouxportfolio.dto.MovieInformation;
import com.giroux.kevin.kevingirouxportfolio.dto.Trailer;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;
import java.util.Map;
import java.util.Vector;

/**
 * Created by kevin on 11/06/2016. KevinGirouxPortfolio
 */

public class MovieTrailerTask extends AndroidHttpRequest {
    public MovieTrailerTask(String url, String method, Map<String, String> paramStr) {
        super(url, method, paramStr);
    }

    private Context context;

    public void setContext(Context context) {
        this.context = context;
    }

    @Override
    protected void onPostExecute(Object o) {
        if(o instanceof JSONObject){
            JSONObject object = (JSONObject)o;
            int idMovie = (int) this.getListObject().get("idMovie");
            TrailerAdapter adapter = (TrailerAdapter) this.getListObject().get("trailerAdapter");

            MovieInformation movieInformation = (MovieInformation) this.getListObject().get("movieInformation");
            List<Trailer> list = Trailer.getDataFromJson(object, idMovie);
            adapter.setData(list);
            Vector<ContentValues> cVVector = Trailer.convertListToContentValue(list);
            if (cVVector.size() > 0) {
                ContentValues[] cvArray = new ContentValues[cVVector.size()];
                cVVector.toArray(cvArray);

                context.getContentResolver().bulkInsert(MovieContractor.TrailerEntry.CONTENT_URI, cvArray);

                ContentValues contentValues = new ContentValues();
                contentValues.put(MovieContractor.MovieEntry.COLUMN_MOVIE_TRAILER_LOADED,true);
                String value [] = new String[]{Integer.toString(idMovie)};
                String whereClause = MovieContractor.MovieEntry._ID + "=?";
                context.getContentResolver().update(MovieContractor.MovieEntry.buildMovieUri(idMovie),contentValues,whereClause,value);
                movieInformation.setMarkAsFavorite(1);

            }
        }
    }
}

package com.giroux.kevin.kevingirouxportfolio.network;

import android.content.ContentValues;
import android.content.Context;

import com.giroux.kevin.androidhttprequestlibrairy.AndroidHttpRequest;
import com.giroux.kevin.androidhttprequestlibrairy.constants.MethodDatabase;
import com.giroux.kevin.kevingirouxportfolio.adapter.ReviewAdapter;
import com.giroux.kevin.kevingirouxportfolio.database.AsyncDeleteUpdate;
import com.giroux.kevin.kevingirouxportfolio.database.MovieContractor;
import com.giroux.kevin.kevingirouxportfolio.dto.Review;

import org.json.JSONObject;

import java.util.List;
import java.util.Map;
import java.util.Vector;

/**
 * Created by kevin on 11/06/2016. KevinGirouxPortfolio
 */

public class MovieReviewsTask extends AndroidHttpRequest {
    public MovieReviewsTask(String url, String method, Map<String, String> paramStr) {
        super(url, method, paramStr);
    }

    private Context context;

    public void setContext(Context context) {
        this.context = context;
    }

    @Override
    protected void onPostExecute(Object o) {
        if(o instanceof JSONObject){
            JSONObject object =  (JSONObject)o;

            int idMovie = (int) this.getListObject().get("idMovie");
            ReviewAdapter adapter = (ReviewAdapter) this.getListObject().get("reviewAdapter");
            List<Review> list = Review.getDataFromJson(object,idMovie);
            adapter.setData(list);
            Vector<ContentValues> cVVector = Review.convertListToContentValue(list);
            if (cVVector.size() > 0) {
                ContentValues[] cvArray = new ContentValues[cVVector.size()];
                cVVector.toArray(cvArray);
                context.getContentResolver().bulkInsert(MovieContractor.ReviewEntry.CONTENT_URI, cvArray);
                ContentValues contentValues = new ContentValues();
                contentValues.put(MovieContractor.MovieEntry.COLUMN_MOVIE_REVIEW_LOADED,true);
                String value [] = new String[]{Integer.toString(idMovie)};
                String whereClause = MovieContractor.MovieEntry._ID + "=?";

                AsyncDeleteUpdate asyncDeleteUpdate = new AsyncDeleteUpdate(context);
                asyncDeleteUpdate.setMethod(MethodDatabase.UPDATE);
                asyncDeleteUpdate.setUri(MovieContractor.MovieEntry.buildMovieUri(idMovie));
                asyncDeleteUpdate.setContentValues(contentValues);
                asyncDeleteUpdate.setWhereClause(whereClause);
                asyncDeleteUpdate.setValue(value);
                asyncDeleteUpdate.execute();

            }
        }
    }
}

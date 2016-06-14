package com.giroux.kevin.kevingirouxportfolio.network;

import android.content.ContentValues;
import android.content.Context;
import android.util.Log;
import android.widget.TextView;

import com.giroux.kevin.androidhttprequestlibrairy.AndroidHttpRequest;
import com.giroux.kevin.androidhttprequestlibrairy.constants.Constants;
import com.giroux.kevin.kevingirouxportfolio.Utils.Utility;
import com.giroux.kevin.kevingirouxportfolio.database.MovieContractor;
import com.giroux.kevin.kevingirouxportfolio.dto.MovieInformation;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Map;

/**
 * Created by kevin on 14/06/2016. KevinGirouxPortfolio
 */

public class MovieFullTask extends AndroidHttpRequest {

    private Context context;

    public void setContext(Context context) {
        this.context = context;
    }

    public MovieFullTask(String url, String method, Map<String, String> paramStr) {
        super(url, method, paramStr);
    }


    @Override
    protected void onPostExecute(Object o) {
        if(o instanceof JSONObject){
            try{
                JSONObject object =(JSONObject)o;
                if(object.has("runtime")){
                    MovieInformation movieInformation = (MovieInformation)this.getListObject().get("movieObject");
                    if(movieInformation != null){
                        movieInformation.setDuration(object.getDouble("runtime"));
                        ContentValues contentValues = new ContentValues();
                        contentValues.put(MovieContractor.MovieEntry.COLUMN_MOVIE_DURATION,movieInformation.getDuration());
                        String value [] = new String[]{Integer.toString(movieInformation.getId())};
                        String whereClause = MovieContractor.MovieEntry._ID + "=?";
                        context.getContentResolver().update(MovieContractor.MovieEntry.buildMovieUri(movieInformation.getId()),contentValues,whereClause,value);

                        TextView textView = (TextView)this.getListObject().get("durationTv");
                        textView.setText(Utility.formatDuration(movieInformation.getDuration()));
                    }
                }
            }catch (JSONException ex){
                Log.e(Constants.TAG_MOVIE_FULL,"Error during parsing json",ex);
            }
        }
    }
}

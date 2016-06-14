package com.giroux.kevin.kevingirouxportfolio.network;

import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;

import com.giroux.kevin.androidhttprequestlibrairy.AndroidHttpRequest;
import com.giroux.kevin.androidhttprequestlibrairy.constants.Constants;
import com.giroux.kevin.kevingirouxportfolio.R;
import com.giroux.kevin.kevingirouxportfolio.adapter.MovieAdapter;
import com.giroux.kevin.kevingirouxportfolio.database.MovieContractor;
import com.giroux.kevin.kevingirouxportfolio.dto.MovieInformation;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Vector;

/**
 * Created by kevin on 27/04/2016. Kevin Giroux Portfolio
 */
public class MovieTask extends AndroidHttpRequest {
    private Context context;
    public MovieTask(String url, String method, Map<String, String> paramStr) {
        super(url, method, paramStr);
    }

    @Override
    protected void onPostExecute(Object o) {

        if(o instanceof JSONObject){
            JSONObject object = (JSONObject) o;
            MovieAdapter adapter = ((MovieAdapter) getListObject().get("adapter"));
            List<MovieInformation> list = parseJSONData(object);
            if(list != null && adapter != null)
                adapter.setData(list);
            Log.e(Constants.TAG_MOVIE_TASK,o.toString());
            Log.e(Constants.TAG_MOVIE_TASK,"DONE");

        }
    }

    private List<MovieInformation> parseJSONData(JSONObject o){
        List<MovieInformation> toReturn = new ArrayList<>();
        if(!o.has("results")){
            return null;
        }
        try{
            JSONArray object = o.getJSONArray("results");
            Vector<ContentValues> cVVector = new Vector<>(object.length());
            SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
            String preferencesString = preferences.getString(context.getString(R.string.pref_list_key),context.getString(R.string.pref_list_default));
            for(int i = 0; i<object.length(); i++){
                JSONObject temp = object.getJSONObject(i);

                MovieInformation movieInformation = new MovieInformation();

                if(temp.has("id")){
                    movieInformation.setId(temp.getInt("id"));
                }
                if(temp.has("poster_path")){
                    movieInformation.setPosterPath(temp.getString("poster_path"));
                }

                if(temp.has("release_date")){
                    movieInformation.setReleaseDate(temp.getString("release_date"));
                }

                if(temp.has("original_title")){
                    movieInformation.setOriginalTitle(temp.getString("original_title"));
                }

                if(temp.has("title")){
                    movieInformation.setTitle(temp.getString("title"));
                }

                if(temp.has("vote_average")){
                    movieInformation.setUserRating(temp.getDouble("vote_average"));
                }

                if(temp.has("backdrop_path")){
                    movieInformation.setBackdropPath(temp.getString("backdrop_path"));
                }

                if(temp.has("overview")){
                    movieInformation.setOverView(temp.getString("overview"));
                }

                if(temp.has("popularity")){
                    movieInformation.setPopularity(temp.getDouble("popularity"));
                }

                toReturn.add(movieInformation);
                cVVector.add(movieInformation.generateContentValuesFromMovieInformation(preferencesString, System.currentTimeMillis()));


            }

            int insertedRow = 0;
            if(cVVector.size() > 0){
                ContentValues [] cvArray = new ContentValues[cVVector.size()];
                cVVector.toArray(cvArray);
                if(context.getContentResolver() != null)
                    insertedRow = context.getContentResolver().bulkInsert(MovieContractor.MovieEntry.CONTENT_URI,cvArray);
            }
            if(Log.isLoggable(Constants.TAG_MOVIE_TASK,Log.INFO))
                Log.i(Constants.TAG_MOVIE_TASK,"Nb rows inserted : " + insertedRow );

        }catch(JSONException ex){
            Log.e(Constants.TAG_MOVIE_TASK,"Error during parsing", ex);
        }

        return toReturn;
    }

    public void setContext(Context context) {
        this.context = context;
    }
}

package com.giroux.kevin.kevingirouxportfolio.network;

import android.util.Log;

import com.giroux.kevin.androidhttprequestlibrairy.AndroidHttpRequest;
import com.giroux.kevin.androidhttprequestlibrairy.constants.Constants;
import com.giroux.kevin.kevingirouxportfolio.adapter.MovieAdapter;
import com.giroux.kevin.kevingirouxportfolio.dto.MovieInformation;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by kevin on 27/04/2016. Kevin Giroux Portfolio
 */
public class MovieTask extends AndroidHttpRequest {
    public MovieTask(String url, String method, Map<String, String> paramStr) {
        super(url, method, paramStr);
    }

    @Override
    protected void onPostExecute(Object o) {

        if(o instanceof JSONObject){
            JSONObject object = (JSONObject) o;
            MovieAdapter adapter = ((MovieAdapter) getListObject().get("adapter"));
            adapter.setDate(parseJSONData(object));
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
            for(int i = 0; i<object.length(); i++){
                JSONObject temp = object.getJSONObject(i);

                MovieInformation movieInformation = new MovieInformation();

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
                toReturn.add(movieInformation);

            }
        }catch(JSONException ex){
            Log.e(Constants.TAG_MOVIE_TASK,"Error during parsing", ex);
        }

        return toReturn;
    }
}

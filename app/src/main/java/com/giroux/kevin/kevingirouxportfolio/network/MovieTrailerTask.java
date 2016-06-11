package com.giroux.kevin.kevingirouxportfolio.network;

import android.util.Log;

import com.giroux.kevin.androidhttprequestlibrairy.AndroidHttpRequest;
import com.giroux.kevin.androidhttprequestlibrairy.constants.Constants;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Map;

/**
 * Created by kevin on 11/06/2016. KevinGirouxPortfolio
 */

public class MovieTrailerTask extends AndroidHttpRequest {
    public MovieTrailerTask(String url, String method, Map<String, String> paramStr) {
        super(url, method, paramStr);
    }


    @Override
    protected void onPostExecute(Object o) {
        if(o instanceof String){
            try{
                JSONObject object = new JSONObject((String)o);




            }catch (JSONException ex){
                Log.e(Constants.TAG_MOVIE_TRAILER,"Error during parsing",ex);
            }
        }
    }
}

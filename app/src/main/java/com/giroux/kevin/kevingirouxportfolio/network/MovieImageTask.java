package com.giroux.kevin.kevingirouxportfolio.network;

import com.giroux.kevin.androidhttprequestlibrairy.AndroidHttpRequest;

import java.util.Map;

/**
 * Created by kevin on 27/04/2016. Kevin Giroux Portfolio
 */
public class MovieImageTask extends AndroidHttpRequest {
    public MovieImageTask(String url, String method, Map<String, String> paramStr) {
        super(url, method, paramStr);
    }



}

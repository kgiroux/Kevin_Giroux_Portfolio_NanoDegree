package com.giroux.kevin.kevingirouxportfolio.dto;

import android.content.ContentValues;

import com.giroux.kevin.kevingirouxportfolio.database.MovieContractor;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

/**
 * Created by kevin on 15/06/2016. KevinGirouxPortfolio
 */

public class Trailer {

    private long id;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    private boolean isYoutube;
    private String youtubeKey;
    private String name;
    private String type;
    private long idMovie;


    public boolean isYoutube() {
        return isYoutube;
    }

    public void setYoutube(int youtube) {
        if(youtube == 1)
            isYoutube = true;
        else
            isYoutube = false;
    }

    public String getYoutubeKey() {
        return youtubeKey;
    }

    public void setYoutubeKey(String youtubeKey) {
        this.youtubeKey = youtubeKey;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public long getIdMovie() {
        return idMovie;
    }

    public void setIdMovie(long idMovie) {
        this.idMovie = idMovie;
    }


    public static List<Trailer> getDataFromJson(JSONObject object,long idMovie) {
        ArrayList<Trailer> arrayList = new ArrayList<>();
        if(object.has("results")){
            try {
                JSONArray temp = object.getJSONArray("results");
                for(int i = 0; i<temp.length(); i++){
                    Trailer trailerMovie = new Trailer();

                    JSONObject trailer = temp.getJSONObject(i);
                    if(trailer.has("key"))
                        trailerMovie.setYoutubeKey(trailer.getString("key"));


                    if(trailer.has("name"))
                        trailerMovie.setName(trailer.getString("name"));

                    if(trailer.has("site")){
                        if(trailer.getString("site").equals("Youtube")){
                            trailerMovie.setYoutube(1);
                        }else{
                            trailerMovie.setYoutube(0);
                        }
                    }
                    if(trailer.has("type"))
                        trailerMovie.setType(trailer.getString("type"));

                    trailerMovie.setIdMovie(idMovie);
                    arrayList.add(trailerMovie);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return arrayList;
    }

    public static Vector<ContentValues> convertListToContentValue(List<Trailer> list) {
        Vector<ContentValues> cVVector = new Vector<>(list.size());

        for(Trailer t : list){
            ContentValues contentValue = new ContentValues();
            contentValue.put(MovieContractor.TrailerEntry.COLUMN_TRAILER_KEY_YOUTUBE,t.getYoutubeKey());
            contentValue.put(MovieContractor.TrailerEntry.COLUMN_TRAILER_IS_YOUTUBE,t.isYoutube());
            contentValue.put(MovieContractor.TrailerEntry.COLUMN_TRAILER_NAME,t.getName());
            contentValue.put(MovieContractor.TrailerEntry.COLUMN_TRAILER_MOVIE_ID,t.getIdMovie());
            contentValue.put(MovieContractor.TrailerEntry.COLUMN_TRAILER_TYPE,t.getType());
            cVVector.add(contentValue);

        }
        return cVVector;
    }
}

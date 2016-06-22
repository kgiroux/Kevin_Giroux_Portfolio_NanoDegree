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

public class Review {
    private String nameReviewer;
    private String content;
    private int idMovie;
    private int id;

    public String getNameReviewer() {
        return nameReviewer;
    }

    public void setNameReviewer(String nameReviewer) {
        this.nameReviewer = nameReviewer;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getIdMovie() {
        return idMovie;
    }

    public void setIdMovie(int idMovie) {
        this.idMovie = idMovie;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public static List<Review> getDataFromJson(JSONObject object,int idMovie){
        ArrayList<Review> arrayList = new ArrayList<>();

        if(object.has("results")){

            JSONArray temp ;
            try {
                temp = object.getJSONArray("results");
                for (int i = 0; i<temp.length(); i++){
                    Review review = new Review();
                    JSONObject reviewerItem = temp.getJSONObject(i);
                    if(reviewerItem.has("author")){
                        review.setNameReviewer(reviewerItem.getString("author"));
                    }

                    if(reviewerItem.has("content")){
                        review.setContent(reviewerItem.getString("content"));
                    }

                    review.setIdMovie(idMovie);
                    arrayList.add(review);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return arrayList;
    }

    public static Vector<ContentValues> convertListToContentValue(List<Review> list) {
        Vector<ContentValues> cVVector = new Vector<>(list.size());

        for(Review r : list){
            ContentValues contentValue = new ContentValues();
            contentValue.put(MovieContractor.ReviewEntry.COLUMN_REVIEWS_ID_MOVIE,r.getIdMovie());
            contentValue.put(MovieContractor.ReviewEntry.COLUMN_REVIEWS_NAME, r.getNameReviewer());
            contentValue.put(MovieContractor.ReviewEntry.COLUMN_REVIEWS_CONTENT, r.getContent());
            cVVector.add(contentValue);
        }
        return cVVector;

    }
}

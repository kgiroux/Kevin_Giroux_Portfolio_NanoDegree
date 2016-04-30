package com.giroux.kevin.kevingirouxportfolio.dto;

import java.io.Serializable;

/**
 * Created by kevin on 28/04/2016. Kevin Giroux Portfolio
 */
public class MovieInformation implements Serializable{

    private String originalTitle;
    private String overView;
    private String posterPath;
    private String releaseDate;
    private String title;
    private Double userRating;
    private String backdropPath;
    private byte []  posterBitmap;

    public byte [] getPosterBitmap() {
        return posterBitmap;
    }

    public void setPosterBitmap(byte [] posterBitmap) {
        this.posterBitmap = posterBitmap;
    }

    public String getOriginalTitle() {
        return originalTitle;
    }

    public void setOriginalTitle(String originalTitle) {
        this.originalTitle = originalTitle;
    }

    public Double getUserRating() {
        return userRating;
    }

    public void setUserRating(Double userRating) {
        this.userRating = userRating;
    }

    public String getOverView() {
        return overView;
    }

    public void setOverView(String overView) {
        this.overView = overView;
    }

    public String getPosterPath() {
        return posterPath;
    }

    public void setPosterPath(String posterPath) {
        this.posterPath = posterPath;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBackdropPath() {
        return backdropPath;
    }

    public void setBackdropPath(String backdropPath) {
        this.backdropPath = backdropPath;
    }

    public MovieInformation() {

    }

}

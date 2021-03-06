package com.giroux.kevin.kevingirouxportfolio.dto;

import android.content.ContentValues;
import android.os.Parcel;
import android.os.Parcelable;

import com.giroux.kevin.kevingirouxportfolio.database.MovieContractor;

import java.util.Arrays;

/**
 * Created by kevin on 28/04/2016. Kevin Giroux Portfolio
 */
public class MovieInformation implements Parcelable {

    private int id;
    private String settings;
    private String originalTitle;
    private String overView;
    private String posterPath;
    private String releaseDate;
    private String title;
    private Double userRating;
    private String backdropPath;
    private Double popularity;
    private Double duration;
    private double date_query_db;
    private boolean markAsFavorite;
    private boolean trailerLoaded;
    private boolean reviewLoaded;
    private byte[] posterBitmap;
    private byte[] backdropPathBitmap;


    public boolean isTrailerLoaded() {
        return trailerLoaded;
    }

    public void setTrailerLoaded(int trailerLoaded) {
        if(trailerLoaded == 1)
            this.trailerLoaded = true;
        else
            this.trailerLoaded = false;
    }

    public boolean isReviewLoaded() {
        return reviewLoaded;
    }

    public void setReviewLoaded(int reviewLoaded) {
        if(reviewLoaded == 1)
            this.reviewLoaded = true;
        else
            this.reviewLoaded = false;
    }

    public double getDate_query_db() {
        return date_query_db;
    }

    public void setDate_query_db(double date_query_db) {
        this.date_query_db = date_query_db;
    }


    public Double getDuration() {
        return duration;
    }

    public void setDuration(Double duration) {
        this.duration = duration;
    }

    public boolean isMarkAsFavorite() {
        return markAsFavorite;
    }

    public void setMarkAsFavorite(boolean markAsFavorite) {
        this.markAsFavorite = markAsFavorite;
    }

    public void setMarkAsFavorite(int markAsFavorite) {
        if(markAsFavorite == 1)
            this.markAsFavorite = true;
        else
            this.markAsFavorite = false;
    }

    public Double getPopularity() {
        return popularity;
    }

    public void setPopularity(Double popularity) {
        this.popularity = popularity;
    }



    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public byte [] getPosterBitmap() {
        return posterBitmap;
    }

    @Override
    public String toString() {
        return "MovieInformation{" +
                "id=" + id +
                ", settings='" + settings + '\'' +
                ", originalTitle='" + originalTitle + '\'' +
                ", overView='" + overView + '\'' +
                ", posterPath='" + posterPath + '\'' +
                ", releaseDate='" + releaseDate + '\'' +
                ", title='" + title + '\'' +
                ", userRating=" + userRating +
                ", backdropPath='" + backdropPath + '\'' +
                ", popularity=" + popularity +
                ", posterBitmap=" + Arrays.toString(posterBitmap) +
                '}';
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

    public MovieInformation(Parcel in) {
       readFromParcel(in);
    }

    public MovieInformation() {
        this.backdropPathBitmap = new byte[10];
        this.posterBitmap = new byte[10];
    }

    private void readFromParcel(Parcel in) {
        id = in.readInt();
        title = in.readString();
        originalTitle = in.readString();
        releaseDate = in.readString();
        overView = in.readString();
        int size = in.readInt();
        posterBitmap = new byte[size];
        in.readByteArray(posterBitmap);
        userRating = in.readDouble();
        backdropPath = in.readString();
        posterPath = in.readString();
        int sizeBack = in.readInt();
        backdropPathBitmap = new byte[sizeBack];
        in.readByteArray(backdropPathBitmap);
    }


    @Override
    public int describeContents() {
        return 0;
    }


    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(title);
        dest.writeString(originalTitle);
        dest.writeString(releaseDate);
        dest.writeString(overView);
        if(posterBitmap != null)
            dest.writeInt(posterBitmap.length);
        else
            dest.writeInt(10);
        dest.writeByteArray(posterBitmap);
        dest.writeDouble(userRating);
        dest.writeString(backdropPath);
        dest.writeString(posterPath);
        if(backdropPathBitmap != null)
            dest.writeInt(backdropPathBitmap.length);
        else
            dest.writeInt(10);
        dest.writeByteArray(backdropPathBitmap);
    }

    public static final Parcelable.Creator<MovieInformation> CREATOR
            = new Parcelable.Creator<MovieInformation>() {

        public MovieInformation createFromParcel(Parcel in) {
            return new MovieInformation(in);
        }

        public MovieInformation[] newArray(int size) {
            return new MovieInformation[size];
        }
    };


    public void setSettings(String settings) {
        this.settings = settings;
    }

    public byte[] getBackdropPathBitmap() {
        return backdropPathBitmap;
    }

    public void setBackdropPathBitmap(byte[] backdropPathBitmap) {
        this.backdropPathBitmap = backdropPathBitmap;
    }

    public ContentValues generateContentValuesFromMovieInformation(String settingValue, long dateQuery) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(MovieContractor.MovieEntry._ID, this.getId());
        contentValues.put(MovieContractor.MovieEntry.COLUMN_MOVIE_ORIGINAL_TITLE, this.getOriginalTitle());
        contentValues.put(MovieContractor.MovieEntry.COLUMN_MOVIE_OVERVIEW, this.getOverView());
        contentValues.put(MovieContractor.MovieEntry.COLUMN_MOVIE_POSTER_PATH, this.getPosterPath());
        contentValues.put(MovieContractor.MovieEntry.COLUMN_MOVIE_RELEASE_DATE, this.getReleaseDate());
        contentValues.put(MovieContractor.MovieEntry.COLUMN_MOVIE_TITLE, this.getTitle());
        contentValues.put(MovieContractor.MovieEntry.COLUMN_MOVIE_USER_RATING, this.getUserRating());
        contentValues.put(MovieContractor.MovieEntry.COLUMN_MOVIE_BACKDROP_PATH, this.getBackdropPath());
        contentValues.put(MovieContractor.MovieEntry.COLUMN_MOVIE_SETTING, settingValue);
        contentValues.put(MovieContractor.MovieEntry.COLUMN_MOVIE_DATE_QUERY_MOVIEDB, dateQuery);
        contentValues.put(MovieContractor.MovieEntry.COLUMN_MOVIE_POPULARITY,this.getPopularity());


        return contentValues;

    }
}

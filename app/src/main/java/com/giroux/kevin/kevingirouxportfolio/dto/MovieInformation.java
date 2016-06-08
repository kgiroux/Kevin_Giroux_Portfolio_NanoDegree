package com.giroux.kevin.kevingirouxportfolio.dto;

import android.content.ContentValues;
import android.os.Parcel;
import android.os.Parcelable;

import com.giroux.kevin.kevingirouxportfolio.database.MovieContractor;

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

    public Double getPopularity() {
        return popularity;
    }

    public void setPopularity(Double popularity) {
        this.popularity = popularity;
    }

    private byte []  posterBitmap;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

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

    public MovieInformation(Parcel in) {
       readFromParcel(in);
    }

    public MovieInformation() {

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
        dest.writeInt(posterBitmap.length);
        dest.writeByteArray(posterBitmap);
        dest.writeDouble(userRating);
        dest.writeString(backdropPath);
        dest.writeString(posterPath);
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

    public String getSettings() {
        return settings;
    }

    public void setSettings(String settings) {
        this.settings = settings;
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

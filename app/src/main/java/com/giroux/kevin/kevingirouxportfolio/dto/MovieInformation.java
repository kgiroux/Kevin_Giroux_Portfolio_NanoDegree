package com.giroux.kevin.kevingirouxportfolio.dto;

import android.graphics.Bitmap;

import java.util.List;

/**
 * Created by kevin on 28/04/2016. Kevin Giroux Portfolio
 */
public class MovieInformation {

    private String originalTitle;
    private String overView;
    private String posterPath;
    private String releaseDate;
    private String title;
    private Double userRating;
    private String backdropPath;
    private Bitmap posterBitmap;
    private Bitmap backdropBitmap;

    public Bitmap getPosterBitmap() {
        return posterBitmap;
    }

    public void setPosterBitmap(Bitmap posterBitmap) {
        this.posterBitmap = posterBitmap;
    }

    public Bitmap getBackdropBitmap() {
        return backdropBitmap;
    }

    public void setBackdropBitmap(Bitmap backdropBitmap) {
        this.backdropBitmap = backdropBitmap;
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

/*
{
	"adult": false,
	"backdrop_path": "\/rGQ5S7T4u1VHRx9yZ11ff8sz2TT.jpg",
	"belongs_to_collection": {
		"id": 387037,
		"name": "Death Trilogy",
		"poster_path": null,
		"backdrop_path": null
	},
	"budget": 2000000,
	"genres": [{
		"id": 18,
		"name": "Drama"
	}, {
		"id": 53,
		"name": "Thriller"
	}],
	"homepage": "",
	"id": 55,
	"imdb_id": "tt0245712",
	"original_language": "es",
	"original_title": "Amores perros",
	"overview": "Three interconnected stories about the different strata of life in Mexico City all resolve with a fatal car accident. Octavio is trying to raise enough money to run away with his sister-in-law, and decides to enter his dog Cofi into the world of dogfighting. After a dogfight goes bad, Octavio flees in his car, running a red light and causing the accident. Daniel and Valeria's new-found bliss is prematurely ended when she loses her leg in the accident. El Chivo is a homeless man who cares for stray dogs and is there to witness the collision. Amores Perros is the first film in Iñárritu's \"Death Trilogy\".",
	"popularity": 1.447544,
	"poster_path": "\/8gEXmIzw1tDnBfOaCFPimkNIkmm.jpg",
	"production_companies": [{
		"name": "Altavista Films",
		"id": 5084
	}, {
		"name": "Zeta Film",
		"id": 11230
	}],
	"production_countries": [{
		"iso_3166_1": "MX",
		"name": "Mexico"
	}],
	"release_date": "2000-03-14",
	"revenue": 21000000,
	"runtime": 154,
	"spoken_languages": [{
		"iso_639_1": "es",
		"name": "Español"
	}],
	"status": "Released",
	"tagline": "Love. Betrayal. Death.",
	"title": "Amores perros",
	"video": false,
	"vote_average": 7.3,
	"vote_count": 216
}
 */

}

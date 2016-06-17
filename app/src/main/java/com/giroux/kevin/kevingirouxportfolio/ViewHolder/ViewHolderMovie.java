package com.giroux.kevin.kevingirouxportfolio.ViewHolder;

import android.app.Activity;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.giroux.kevin.kevingirouxportfolio.R;
import com.giroux.kevin.kevingirouxportfolio.database.MovieContractor;
import com.giroux.kevin.kevingirouxportfolio.dto.MovieInformation;
import com.giroux.kevin.kevingirouxportfolio.interfaces.OnCustomItemClickListener;

import pl.droidsonroids.gif.GifImageView;

/**
 * Created by kevin on 28/04/2016. Kevin Giroux Portfolio
 */
public class ViewHolderMovie extends RecyclerView.ViewHolder implements View.OnClickListener {

    private GifImageView imageMovie;

    private int mPosition;
    private Activity activity;
    private MovieInformation movieInformation;

    public void setMovieInformation(MovieInformation movieInformation) {
        this.movieInformation = movieInformation;
    }

    public int getMPosition() {
        return mPosition;
    }

    public void setMPosition(int position) {
        this.mPosition = position;
    }

    public GifImageView getImageMovie() {
        return imageMovie;
    }

    public ViewHolderMovie(View itemView) {
        super(itemView);
        itemView.setOnClickListener(this);
        imageMovie = (GifImageView) itemView.findViewById(R.id.movie_item_picture);
    }

    public Activity getActivity() {
        return activity;
    }

    public void setActivity(Activity activity) {
        this.activity = activity;
    }

    @Override
    public void onClick(View v) {
        Uri uri = MovieContractor.MovieEntry.buildMovieUri(movieInformation.getId());
        ((OnCustomItemClickListener)activity).onItemSelected(uri);
    }
}

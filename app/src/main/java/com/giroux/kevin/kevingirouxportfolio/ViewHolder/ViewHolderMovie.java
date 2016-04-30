package com.giroux.kevin.kevingirouxportfolio.ViewHolder;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.giroux.kevin.androidhttprequestlibrairy.constants.Constants;
import com.giroux.kevin.kevingirouxportfolio.R;
import com.giroux.kevin.kevingirouxportfolio.activity.popularMovies.MovieDetailActivity;
import com.giroux.kevin.kevingirouxportfolio.dto.MovieInformation;

import pl.droidsonroids.gif.GifImageView;

/**
 * Created by kevin on 28/04/2016. Kevin Giroux Portfolio
 */
public class ViewHolderMovie extends RecyclerView.ViewHolder implements View.OnClickListener {

    private GifImageView imageMovie;
    private int mPosition;
    private Context context;
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

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    @Override
    public void onClick(View v) {
        Log.e(Constants.VIEW_HOLDER_MOVIE,"TEST : " + getMPosition());

        Intent t = new Intent(context, MovieDetailActivity.class);
        t.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        t.putExtra("movieInformation",movieInformation);
        context.startActivity(t);

    }
}

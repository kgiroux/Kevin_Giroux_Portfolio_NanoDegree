package com.giroux.kevin.kevingirouxportfolio.ViewHolder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.giroux.kevin.kevingirouxportfolio.R;

import pl.droidsonroids.gif.GifImageView;

/**
 * Created by kevin on 28/04/2016. Kevin Giroux Portfolio
 */
public class ViewHolderMovie extends RecyclerView.ViewHolder {

    private TextView titleMovie;
    private GifImageView imageMovie;

    public TextView getTitleMovie() {
        return titleMovie;
    }

    public void setTitleMovie(TextView titleMovie) {
        this.titleMovie = titleMovie;
    }

    public GifImageView getImageMovie() {
        return imageMovie;
    }

    public void setImageMovie(GifImageView imageMovie) {
        this.imageMovie = imageMovie;
    }

    public ViewHolderMovie(View itemView) {
        super(itemView);

        titleMovie = (TextView) itemView.findViewById(R.id.movie_item_textview);
        imageMovie = (GifImageView) itemView.findViewById(R.id.movie_item_picture);
    }
}

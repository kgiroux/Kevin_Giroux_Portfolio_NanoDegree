package com.giroux.kevin.kevingirouxportfolio.ViewHolder;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.giroux.kevin.kevingirouxportfolio.R;
import com.giroux.kevin.kevingirouxportfolio.dto.Trailer;

/**
 * Created by kevin on 15/06/2016. KevinGirouxPortfolio
 */

public class ViewHolderTrailer extends RecyclerView.ViewHolder implements View.OnClickListener {

    private TextView textView;
    private ImageView imageView;
    private Context context;
    private Trailer trailer;

    public TextView getTextView() {
        return textView;
    }

    public ViewHolderTrailer(View itemView) {
        super(itemView);

        imageView = (ImageView) itemView.findViewById(R.id.imageTrailerLaunch);
        itemView.setOnClickListener(this);
        textView = (TextView) itemView.findViewById(R.id.titleTrailer);

    }

    public void setContext(Context context) {
        this.context = context;
    }

    public void setTrailer(Trailer trailer) {
        this.trailer = trailer;
    }

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.imageTrailerLaunch){
            try {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("vnd.youtube:" + trailer.getYoutubeKey()));
                context.startActivity(intent);
            } catch (ActivityNotFoundException ex) {
                Intent intent = new Intent(Intent.ACTION_VIEW,
                        Uri.parse("http://www.youtube.com/watch?v=" + trailer.getYoutubeKey()));
                context.startActivity(intent);
            }
        }

    }
}
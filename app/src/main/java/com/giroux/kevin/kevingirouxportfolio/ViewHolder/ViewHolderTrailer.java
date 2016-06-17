package com.giroux.kevin.kevingirouxportfolio.ViewHolder;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.giroux.kevin.kevingirouxportfolio.R;
import com.giroux.kevin.kevingirouxportfolio.dto.Trailer;

/**
 * Created by kevin on 15/06/2016. KevinGirouxPortfolio
 */

public class ViewHolderTrailer extends RecyclerView.ViewHolder implements View.OnClickListener {

    private TextView textView;
    private Context context;
    private Trailer trailer;

    public TextView getTextView() {
        return textView;
    }

    public ViewHolderTrailer(View itemView) {
        super(itemView);
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
        if (v.getId() == R.id.linearLayout) {
            try {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("vnd.youtube:" + trailer.getYoutubeKey()));

                if(intent.resolveActivity(context.getPackageManager()) != null){
                    context.startActivity(intent);
                }


            } catch (ActivityNotFoundException ex) {
                Intent intent = new Intent(Intent.ACTION_VIEW,
                        Uri.parse("http://www.youtube.com/watch?v=" + trailer.getYoutubeKey()));
                if(intent.resolveActivity(context.getPackageManager()) != null) {

                    context.startActivity(intent);
                }else{
                    Toast.makeText(context,context.getString(R.string.warningIntent),Toast.LENGTH_LONG).show();
                }
            }
        }

    }
}

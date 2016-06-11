package com.giroux.kevin.kevingirouxportfolio.network;

import android.content.ContentValues;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.widget.ImageView;

import com.giroux.kevin.androidhttprequestlibrairy.AndroidHttpRequest;
import com.giroux.kevin.kevingirouxportfolio.ViewHolder.ViewHolderMovie;
import com.giroux.kevin.kevingirouxportfolio.database.MovieContractor;
import com.giroux.kevin.kevingirouxportfolio.dto.MovieInformation;

import java.util.List;
import java.util.Map;

/**
 * Created by kevin on 27/04/2016. Kevin Giroux Portfolio
 */
public class MovieImageTask extends AndroidHttpRequest {

    private Context context;
    public MovieImageTask(String url, String method, Map<String, String> paramStr) {
        super(url, method, paramStr);
    }

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    @Override
    protected void onPostExecute(Object o) {

        if(getListObject().get("ViewHolder") instanceof ViewHolderMovie){
            ViewHolderMovie vh =  ((ViewHolderMovie) getListObject().get("ViewHolder"));
            if(o instanceof byte[]){
                ImageView imageView =vh.getImageMovie();
                byte []bytes = (byte[])o;
                BitmapDrawable bitmapDrawable = new BitmapDrawable(context.getResources(),BitmapFactory.decodeByteArray(bytes,0,bytes.length));
                Bitmap image = bitmapDrawable.getBitmap();
                if(image != null){
                    imageView.setImageBitmap(bitmapDrawable.getBitmap());

                    if(getListObject().get("listMovie") instanceof List){
                        List<MovieInformation> movieInformationList = (List<MovieInformation>) getListObject().get("listMovie");
                        movieInformationList.get(vh.getMPosition()).setPosterBitmap(bytes);
                        // Updating the BLOP field into the database;
                        ContentValues contentValues = new ContentValues();
                        contentValues.put(MovieContractor.MovieEntry.COLUMN_MOVIE_POSTER,movieInformationList.get(vh.getMPosition()).getPosterBitmap());
                        context.getContentResolver().update(MovieContractor.MovieEntry.buildMovieUri(movieInformationList.get(vh.getMPosition()).getId()),contentValues,null,null);

                    }
                }
            }
        }

    }
}

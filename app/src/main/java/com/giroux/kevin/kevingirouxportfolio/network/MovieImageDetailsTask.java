package com.giroux.kevin.kevingirouxportfolio.network;

import android.content.ContentValues;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.widget.ImageView;

import com.giroux.kevin.androidhttprequestlibrairy.AndroidHttpRequest;
import com.giroux.kevin.kevingirouxportfolio.database.MovieContractor;

import java.util.Map;

import pl.droidsonroids.gif.GifImageView;

/**
 * Created by kevin on 30/04/2016. Kevin Giroux Portfolio
 */
public class MovieImageDetailsTask extends AndroidHttpRequest {
    private Context context;
    public MovieImageDetailsTask(String url, String method, Map<String, String> paramStr) {
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

        if(getListObject().get("gifImageView") instanceof GifImageView && o != null){
            if(o instanceof byte[]){
                ImageView imageView = (GifImageView) getListObject().get("gifImageView");
                byte []bytes = (byte[])o;
                BitmapDrawable bitmapDrawable = new BitmapDrawable(context.getResources(), BitmapFactory.decodeByteArray(bytes,0,bytes.length));
                Bitmap image = bitmapDrawable.getBitmap();
                if(image != null){
                    imageView.setImageBitmap(bitmapDrawable.getBitmap());
                }
                int idMovie = (int) this.getListObject().get("idMovie");
                ContentValues contentValues = new ContentValues();
                contentValues.put(MovieContractor.MovieEntry.COLUMN_MOVIE_BACKGROUND, bytes);
                String value[] = new String[]{Integer.toString(idMovie)};
                String whereClause = MovieContractor.MovieEntry._ID + "=?";
                context.getContentResolver().update(MovieContractor.MovieEntry.buildMovieUri(idMovie), contentValues, whereClause, value);
            }
        }
    }
}

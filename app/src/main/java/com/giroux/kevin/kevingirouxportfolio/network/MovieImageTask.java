package com.giroux.kevin.kevingirouxportfolio.network;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.widget.ImageView;

import com.giroux.kevin.androidhttprequestlibrairy.AndroidHttpRequest;
import com.giroux.kevin.kevingirouxportfolio.ViewHolder.ViewHolderMovie;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
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
            if(o instanceof byte[]){
                ImageView imageView = ((ViewHolderMovie) getListObject().get("ViewHolder")).getImageMovie();
                byte []bytes = (byte[])o;
                BitmapDrawable bitmapDrawable = new BitmapDrawable(context.getResources(),BitmapFactory.decodeByteArray(bytes,0,bytes.length));
                Bitmap image = bitmapDrawable.getBitmap();
                if(image != null)
                    imageView.setImageBitmap(bitmapDrawable.getBitmap());
            }
        }

    }
}

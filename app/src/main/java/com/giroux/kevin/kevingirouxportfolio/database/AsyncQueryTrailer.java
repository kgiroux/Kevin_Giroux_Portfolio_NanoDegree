package com.giroux.kevin.kevingirouxportfolio.database;

import android.content.Context;
import android.database.Cursor;

import com.giroux.kevin.androidhttprequestlibrairy.AsyncCursor;
import com.giroux.kevin.kevingirouxportfolio.adapter.TrailerAdapter;
import com.giroux.kevin.kevingirouxportfolio.dto.Trailer;

import java.util.List;

/**
 * Created by kevin on 17/06/2016. KevinGirouxPortfolio
 */

public class AsyncQueryTrailer extends AsyncCursor{
    public AsyncQueryTrailer(Context context) {
        super(context);
    }


    @Override
    protected void onPostExecute(Object o) {
        if(o instanceof Cursor){

            List<Trailer> listTrailerData = MovieContractor.TrailerEntry.convertToList((Cursor)o);
            if(!listTrailerData.isEmpty())
                ((TrailerAdapter)this.getObjectUI().get("trailerAdapter")).setData(listTrailerData);

        }
    }
}

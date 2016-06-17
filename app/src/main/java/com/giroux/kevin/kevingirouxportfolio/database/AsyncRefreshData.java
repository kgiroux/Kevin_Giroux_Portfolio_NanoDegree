package com.giroux.kevin.kevingirouxportfolio.database;

import android.content.Context;
import android.database.Cursor;

import com.giroux.kevin.androidhttprequestlibrairy.AsyncCursor;
import com.giroux.kevin.kevingirouxportfolio.activity.popularMovies.PopularActivityFragment;
import com.giroux.kevin.kevingirouxportfolio.adapter.MovieAdapter;
import com.giroux.kevin.kevingirouxportfolio.dto.MovieInformation;

import java.util.List;

/**
 * Created by kevin on 17/06/2016. KevinGirouxPortfolio
 */

public class AsyncRefreshData extends AsyncCursor {
    public AsyncRefreshData(Context context) {
        super(context);
    }

    @Override
    protected void onPostExecute(Object o) {

        if(o instanceof Cursor){
            PopularActivityFragment ff = ((PopularActivityFragment)this.getObjectUI().get("fragment"));
            Cursor cursor = (Cursor)o;
            if(cursor.getCount() != 0){
                List<MovieInformation> movieInformationList = MovieContractor.MovieEntry.getDataFromCursor(cursor);
                if(!movieInformationList.isEmpty())
                    ((MovieAdapter) this.getObjectUI().get("movieAdapter")).setData(movieInformationList);
                else{
                    ff.onQueryListInternet();
                }

                ff.restartLoader();
            }
        }

    }
}

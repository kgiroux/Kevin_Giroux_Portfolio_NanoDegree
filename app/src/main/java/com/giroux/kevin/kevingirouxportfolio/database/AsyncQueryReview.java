package com.giroux.kevin.kevingirouxportfolio.database;

import android.content.Context;
import android.database.Cursor;

import com.giroux.kevin.androidhttprequestlibrairy.AsyncCursor;
import com.giroux.kevin.kevingirouxportfolio.adapter.ReviewAdapter;
import com.giroux.kevin.kevingirouxportfolio.dto.Review;

import java.util.List;

/**
 * Created by kevin on 17/06/2016. KevinGirouxPortfolio
 */

public class AsyncQueryReview extends AsyncCursor {
    public AsyncQueryReview(Context context) {
        super(context);
    }


    @Override
    protected void onPostExecute(Object o) {
        if(o instanceof Cursor){

            List<Review> listReviewData = MovieContractor.ReviewEntry.convertToList((Cursor)o);
            if(!listReviewData.isEmpty())
                ((ReviewAdapter)this.getObjectUI().get("reviewAdapter")).setData(listReviewData);

        }
    }
}

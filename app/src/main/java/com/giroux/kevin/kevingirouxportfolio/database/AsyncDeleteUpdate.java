package com.giroux.kevin.kevingirouxportfolio.database;

import android.content.Context;
import android.util.Log;

import com.giroux.kevin.androidhttprequestlibrairy.AsyncCursor;
import com.giroux.kevin.androidhttprequestlibrairy.constants.Constants;
import com.giroux.kevin.androidhttprequestlibrairy.constants.MethodDatabase;
import com.giroux.kevin.kevingirouxportfolio.R;

/**
 * Created by kevin on 17/06/2016. KevinGirouxPortfolio
 */

public class AsyncDeleteUpdate extends AsyncCursor {
    public AsyncDeleteUpdate(Context context) {
        super(context);
    }

    @Override
    protected void onPostExecute(Object aVoid) {

        try {
            int nbRowAffected = (int) aVoid;
            String textToDisplay = this.getContext().getString(R.string.nbRowConcernByDatabaseOperation) + MethodDatabase.convertValue(this.getMethod()) + " nb : " + nbRowAffected;
            Log.d(Constants.TAG_CURSOR_DELETE_UPDATE, textToDisplay);

        } catch (ClassCastException ex) {
            Log.e(Constants.TAG_CURSOR_DELETE_UPDATE, "Error during casting the object", ex);
        }


    }
}

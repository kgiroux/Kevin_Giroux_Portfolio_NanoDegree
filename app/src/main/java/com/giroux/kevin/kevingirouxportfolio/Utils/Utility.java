package com.giroux.kevin.kevingirouxportfolio.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;

import com.giroux.kevin.androidhttprequestlibrairy.constants.Constants;
import com.giroux.kevin.kevingirouxportfolio.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;

/**
 * Created by kevin on 07/06/2016. Kevin Giroux Portfolio
 */

public class Utility {

    public static long normaliseDate(long dateToNormalise){
        GregorianCalendar gregorianCalendar = new GregorianCalendar();
        gregorianCalendar.setTime(new Date(dateToNormalise));
        gregorianCalendar.set(Calendar.HOUR_OF_DAY, 0);
        gregorianCalendar.set(Calendar.MINUTE, 0);
        gregorianCalendar.set(Calendar.SECOND, 0);
        gregorianCalendar.set(Calendar.MILLISECOND, 0);

        return gregorianCalendar.getTimeInMillis();
    }

    public static String formatUserRating(double userRating){
        return "" + userRating + "/10";
    }

    public static String formatDuration(double duration){
        String convertDuration = String.valueOf(duration);
        int index = convertDuration.indexOf(".");
        if(index == -1){
            return convertDuration + " min";
        }else{
            String substring = convertDuration.substring(0,index);
            return substring + " min";
        }


    }

    public static String formatDate(String dateString){
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd", Locale.FRANCE);
        String dateInString = "7-Jun-2013";

        try {

            Date date = formatter.parse(dateString);
            GregorianCalendar gregorianCalendar = new GregorianCalendar();
            gregorianCalendar.setTime(date);
            return "" + gregorianCalendar.get(Calendar.YEAR);


        } catch (ParseException e) {
            Log.e(Constants.TAG_UTILITY,"Error during parsing date",e);
        }
        return dateInString;
    }

    public static String getPreferredOrderMovie(Context context){
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        return  preferences.getString(context.getString(R.string.pref_list_key),context.getString(R.string.pref_list_default));
    }

}

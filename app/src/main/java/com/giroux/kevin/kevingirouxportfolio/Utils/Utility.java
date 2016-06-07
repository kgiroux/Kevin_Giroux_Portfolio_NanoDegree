package com.giroux.kevin.kevingirouxportfolio.Utils;

import java.sql.Time;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

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
}

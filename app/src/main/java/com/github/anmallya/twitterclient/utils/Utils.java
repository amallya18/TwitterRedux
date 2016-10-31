package com.github.anmallya.twitterclient.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * Created by anmallya on 10/30/2016.
 */

public class Utils {
    public static String getTwitterDate(String date)
    {
        if(date.equals(Constants.JUST_NOW)){
            return date;
        }
        final String TWITTER = "EEE MMM dd HH:mm:ss ZZZZZ yyyy";
        SimpleDateFormat sf = new SimpleDateFormat(TWITTER, Locale.ENGLISH);
        sf.setLenient(true);
        Date dateNew = null;
        try {
            dateNew = sf.parse(date);
        } catch(ParseException e){
            System.out.println(e);
        }
            Calendar cal = Calendar.getInstance();
        long diff = cal.getTime().getTime() - dateNew.getTime();
        long diffSeconds = diff / 1000 % 60;
        long diffMinutes = diff / (60 * 1000) % 60;
        long diffHours = diff / (60 * 60 * 1000) % 24;
        long diffDays = diff / (24 * 60 * 60 * 1000);

        if(diffDays > 0){
            return diffDays + "d";
        } else if((diffHours > 0)){
            return diffHours + "h";
        } else if (diffMinutes > 0){
            return diffMinutes + "m";
        } else {
            return diffSeconds + "s";
        }
    }

    public static String getTwitterDateVerbose(String date)
    {
        if(date.equals(Constants.JUST_NOW)){
            return date;
        }
        final String TWITTER = "EEE MMM dd HH:mm:ss ZZZZZ yyyy";
        SimpleDateFormat sf = new SimpleDateFormat(TWITTER, Locale.ENGLISH);
        sf.setLenient(true);
        Date dateNew = null;
        try {
            dateNew = sf.parse(date);
        } catch(ParseException e){
            System.out.println(e);
        }

        SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yy");
        return sdf.format(dateNew);
    }
}

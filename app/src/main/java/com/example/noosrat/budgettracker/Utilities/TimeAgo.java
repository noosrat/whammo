package com.example.noosrat.budgettracker.Utilities;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class TimeAgo {

    public static String getTimeAgo(Date d) {

        Calendar now = Calendar.getInstance();
        now = setToMidnight(now);
        Calendar start = Calendar.getInstance();
        start.setTime(d);
        start = setToMidnight(start);

        long milliseconds1 = start.getTimeInMillis();
        long milliseconds2 = now.getTimeInMillis();
        long diff = milliseconds2 - milliseconds1;
        long diffSeconds = diff / 1000;
        long diffMinutes = diff / (60 * 1000);
        long diffHours = diff / (60 * 60 * 1000);
        long diffDays = diff / (24 * 60 * 60 * 1000);
        long diffWeeks = diff / (7 * 24 * 60 * 60 * 1000);

        SimpleDateFormat dayDateformat = new SimpleDateFormat("EEEE"); // the day of the week spelled out completely


        if (diffDays == 0) {
            return "Today";
        } else if (diffDays < 2) {
            return "Yesterday";
        } else if (diffDays < 7) {
            return dayDateformat.format(d);
        } else if (diffWeeks < 2) {
            return "1 Week ago";
        } else {
            return diffWeeks + " Weeks ago";
        }

    }

    private static Calendar setToMidnight(Calendar c){
        c.set(Calendar.HOUR_OF_DAY, 0);
        c.set(Calendar.MINUTE, 0);
        c.set(Calendar.SECOND, 0);
        c.set(Calendar.MILLISECOND, 0);

        return c;
    }
}

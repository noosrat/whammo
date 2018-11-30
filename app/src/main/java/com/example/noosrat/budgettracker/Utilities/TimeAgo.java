package com.example.noosrat.budgettracker.Utilities;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class TimeAgo {

    public static String getTimeAgo(Date d) {

        Calendar now = Calendar.getInstance();
        Calendar start = Calendar.getInstance();
        start.setTime(d);

        long milliseconds1 = start.getTimeInMillis();
        long milliseconds2 = now.getTimeInMillis();
        long diff = milliseconds2 - milliseconds1;
        long diffSeconds = diff / 1000;
        long diffMinutes = diff / (60 * 1000);
        long diffHours = diff / (60 * 60 * 1000);
        long diffDays = diff / (24 * 60 * 60 * 1000);
        long diffWeeks = diff / (7 * 24 * 60 * 60 * 1000);

        SimpleDateFormat dayDateformat = new SimpleDateFormat("EEEE"); // the day of the week spelled out completely


        if (diffHours < 12) {
            return "Today";
        } else if (diffDays < 1) {
            return "Yesterday";
        } else if (diffDays < 7) {
            return dayDateformat.format(d);
        } else if (diffWeeks < 2) {
            return "1 Week ago";
        } else {
            return diffWeeks + " Weeks ago";
        }

    }
}

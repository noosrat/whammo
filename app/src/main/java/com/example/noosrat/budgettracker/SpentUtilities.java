package com.example.noosrat.budgettracker;

import android.content.ContentResolver;
import android.database.Cursor;
import android.net.Uri;
import android.provider.Telephony;
import android.util.Log;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;

public class SpentUtilities {

    public static final int BODY = 0;
    public static final int DATE = 1;

    public static boolean isBundledSms(String sms){

        int pos = sms.indexOf(":");
        String bank = sms.substring(0, pos);

        if (bank.equals("FNB ")){
            int separator = sms.indexOf(";");

            if (separator>-1)
                    return true;
        }


        return false;

    }

    public static SMS[] smsCleaner(SMS sms){

        String message = sms.getMessage();

        int pos = message.indexOf(")"); //end of FNB:-)

        String bankIndicator = message.substring(0, pos + 1);

        message = message.substring(pos+2);

        String transactionTypeIndicator = message.substring(0, message.indexOf(":"));

        String[] messages_list = new String[0];

        if (message.charAt(0) != 'R') {

            message = message.substring(message.indexOf(":")+2);
            messages_list = message.split("; ");
            }

            if (messages_list[messages_list.length-1].trim().startsWith("Avail")){
                messages_list = Arrays.copyOfRange(messages_list, 0,messages_list.length-1);
            }

            SMS[] sms_list = new SMS[messages_list.length];

            for (int i=0; i<messages_list.length; i++){
                messages_list[i] = bankIndicator + " "+ messages_list[i] + " " + transactionTypeIndicator;

                sms_list[i] = new SMS(messages_list[i], sms.getDate());
            }

            return sms_list;

    }

    public static ArrayList<SMS> getSMSes(ContentResolver cr, Uri uri){


        ArrayList<SMS> lstSms = new ArrayList<SMS>();

        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);

        Date monthStart = calendar.getTime();

        long millis = monthStart.getTime();

        Cursor c = cr.query(uri, // Official CONTENT_URI from docs
                new String[] { Telephony.Sms.Inbox.BODY,  Telephony.Sms.Inbox.DATE}, // Select body text
                "(body LIKE ? OR body LIKE ?)",
                new String[] {"ABSA:%", "FNB%" },
                Telephony.Sms.Inbox.DEFAULT_SORT_ORDER); // Default sort order

        int totalSMS = c.getCount();

        if (c.moveToFirst()) {

            for (int i = 0; i < totalSMS; i++) {
                lstSms.add(new SMS(c.getString(BODY), new Date(Long.parseLong( c.getString(DATE)))));

                c.moveToNext();


            }
        } else {
            Log.i("ERROR: ","You have no SMS in Inbox");
        }
        c.close();



        return lstSms;
    }
    
    public static ArrayList<SMS> getSMSes(Date date, ContentResolver cr, Uri uri){


        ArrayList<SMS> lstSms = new ArrayList<SMS>();

        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);

        Date monthStart = calendar.getTime();

        //long millis = monthStart.getTime();
        long millis = date.getTime();


        Cursor c = cr.query(uri, // Official CONTENT_URI from docs
                new String[] { Telephony.Sms.Inbox.BODY,  Telephony.Sms.Inbox.DATE}, // Select body text
                "date > ? AND (body LIKE ? OR body LIKE ? OR body LIKE ?)",
                new String[] {"" + millis, "ABSA:%", "FNB%", "Standard Bank:%" },
                Telephony.Sms.Inbox.DEFAULT_SORT_ORDER); // Default sort order

        int totalSMS = c.getCount();

        if (c.moveToFirst()) {

            for (int i = 0; i < totalSMS; i++) {
                lstSms.add(new SMS(c.getString(BODY), new Date(Long.parseLong( c.getString(DATE)))));

                c.moveToNext();


            }
        } else {
            Log.i("ERROR: ","You have no SMS in Inbox");
        }
            c.close();



            return lstSms;
    }

    public static float calculateExpenses(ArrayList<Transaction> transactionList){

        float sum = 0;

        for (int i=0; i<transactionList.size(); i++){
            sum = sum + transactionList.get(i).getNumberAmount();
        }
        return sum;
    }

    public static Date getMonthStart() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);

        Date monthStart = calendar.getTime();

        return monthStart;
    }


}

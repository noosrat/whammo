package com.example.noosrat.budgettracker;

import android.content.ContentResolver;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.provider.Telephony;
import android.support.v4.content.ContextCompat;
import android.widget.ArrayAdapter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;

public class SpentUtilities {

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

    public static String[] smsCleaner(String message){

        int pos = message.indexOf(")"); //end of FNB:-)
        message = message.substring(pos+2);

        String[] messages_list = new String[0];
        if (message.charAt(0) != 'R') {

            message = message.substring(message.indexOf(":")+2);
            messages_list = message.split("; ");
            }

            if (messages_list[messages_list.length-1].trim().startsWith("Avail")){
                messages_list = Arrays.copyOfRange(messages_list, 0,messages_list.length-2);
            }



            return messages_list;

    }
    
    public static ArrayList<String> getSMSes(String[] banks, Date date, ContentResolver cr, Uri uri){


        ArrayList<String> lstSms = new ArrayList<String>();

        Date monthStart = new Date();
        monthStart.setDate(1);
        monthStart.setHours(0);
        monthStart.setMinutes(0);
        monthStart.setSeconds(0);

        long millis = monthStart.getTime();


        Cursor c = cr.query(uri, // Official CONTENT_URI from docs
                new String[] { Telephony.Sms.Inbox.BODY,  Telephony.Sms.Inbox.DATE}, // Select body text
                "date > ? AND body LIKE ?",
                new String[] {"" + millis, banks[1] + "%"},
                Telephony.Sms.Inbox.DEFAULT_SORT_ORDER); // Default sort order

        int totalSMS = c.getCount();

        if (c.moveToFirst()) {

            for (int i = 0; i < totalSMS; i++) {
                lstSms.add(c.getString(0));

                c.moveToNext();


            }
        } else {
            throw new RuntimeException("You have no SMS in Inbox");
        }
            c.close();



            return lstSms;
    }

    public static float calculateExpenses(ArrayList<Transaction> transactionList){

        float sum = 0;

        for (int i=0; i<transactionList.size(); i++){
            sum = sum + transactionList.get(i).getNumberAmount();
        }

        return 0;
    }

//    public ArrayList<Transaction> SMStoTransaction(ArrayList<String> smsList){
//
//        ArrayList<Transaction> lstSms = new ArrayList<>();
//
//
//        for (int i = 0; i < smsList.size(); i++) {
//            Transaction sms = new Transaction(smsList.get(i));
//            if ((sms.getTransactionType() != 3) && (sms.getTransactionType() != 1)) {
//                lstSms.add(sms);
//
//            }
//        }
//
//        return lstSms;
//    }


}

package com.example.noosrat.budgettracker;

import android.content.ContentResolver;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.provider.Telephony;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class FeedActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feed);

        TextView sms =findViewById(R.id.textView);
        List<String> lstSms = new ArrayList<String>();

        if(ContextCompat.checkSelfPermission(getBaseContext(), "android.permission.READ_SMS") == PackageManager.PERMISSION_GRANTED) {

            ContentResolver cr = getContentResolver();

            Cursor c = cr.query(Telephony.Sms.Inbox.CONTENT_URI, // Official CONTENT_URI from docs
                    new String[] { Telephony.Sms.Inbox.BODY }, // Select body text
                    null,
                    null,
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

            sms.setText(lstSms.get(0));


        }
    }
}

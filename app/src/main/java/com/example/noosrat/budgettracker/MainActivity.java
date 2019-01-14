package com.example.noosrat.budgettracker;

import android.Manifest;
import android.app.Activity;
import android.content.ContentResolver;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.provider.Telephony;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Toast;

import com.example.noosrat.budgettracker.Singleton.SpentSingleton;
import com.example.noosrat.budgettracker.Utilities.MerchantHelper;
import com.example.noosrat.budgettracker.Utilities.TimeAgo;

import java.util.ArrayList;
import java.util.Date;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Remove title bar
        getSupportActionBar().hide();

        setContentView(R.layout.activity_main);
    }

    public void onContinueClicked(View view) {
        ActivityCompat.requestPermissions(MainActivity.this,
                new String[]{Manifest.permission.READ_SMS},
                1);


    }

    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case 1: {

                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    // permission was granted, yay! Do the
                    // contacts-related task you need to do.

                    ArrayList<Transaction> transactionList = new ArrayList<>();
                    ArrayList<FeedItem> feedItemsList = new ArrayList<>();

                    if (ContextCompat.checkSelfPermission(getBaseContext(), "android.permission.READ_SMS") == PackageManager.PERMISSION_GRANTED) {

                        ContentResolver cr = getContentResolver();

                        ArrayList<SMS> smsLst = SpentUtilities.getSMSes(new Date(), cr, Telephony.Sms.Inbox.CONTENT_URI);

                        if (smsLst != null) {

                            MerchantHelper mh = new MerchantHelper();

                            String today = "";

                            for (int k = 0; k < smsLst.size(); k++) {
                                Log.i("orig sms", smsLst.get(k).getMessage());
                                if (SpentUtilities.isBundledSms(smsLst.get(k).getMessage())) {
                                    SMS[] bundledSMSes = SpentUtilities.smsCleaner(smsLst.get(k));

                                    for (int m = 0; m < bundledSMSes.length; m++) {
                                        Transaction transaction = new Transaction(bundledSMSes[m]);
                                        if (transaction.getTransactionType() != Transaction.TRANSACTION_TYPE_TRANSFER && transaction.getTransactionType() != Transaction.TRANSACTION_TYPE_DEPOSIT && transaction.getTransactionType() != Transaction.TRANSACTION_TYPE_INFO  && transaction.getTransactionType() != Transaction.TRANSACTION_TYPE_UNKNOWN) {
                                            transaction.setMerchant(mh.getMerchant(transaction.getRecipient()));
                                            if (TimeAgo.getTimeAgo(transaction.getDate()).equals(today)) {
                                                transactionList.add(transaction);
                                                feedItemsList.add(new FeedItem(transaction));
                                            }
                                            else{
                                                feedItemsList.add(new FeedItem(TimeAgo.getTimeAgo(transaction.getDate())));
                                                transactionList.add(transaction);
                                                feedItemsList.add(new FeedItem(transaction));
                                                today = TimeAgo.getTimeAgo(transaction.getDate());
                                            }

                                        }

                                    }
                                } else {
                                    Transaction transaction = new Transaction(smsLst.get(k));
                                    if (transaction.getTransactionType() != Transaction.TRANSACTION_TYPE_TRANSFER && transaction.getTransactionType() != Transaction.TRANSACTION_TYPE_DEPOSIT && transaction.getTransactionType() != Transaction.TRANSACTION_TYPE_INFO && transaction.getTransactionType() != Transaction.TRANSACTION_TYPE_UNKNOWN) {
                                        transaction.setMerchant(mh.getMerchant(transaction.getRecipient()));
                                        if (TimeAgo.getTimeAgo(transaction.getDate()).equals(today)) {
                                            transactionList.add(transaction);
                                            feedItemsList.add(new FeedItem(transaction));

                                        }
                                        else{
                                            feedItemsList.add(new FeedItem(TimeAgo.getTimeAgo(transaction.getDate())));
                                            transactionList.add(transaction);
                                            feedItemsList.add(new FeedItem(transaction));
                                            today = TimeAgo.getTimeAgo(transaction.getDate());
                                        }

                                    }
                                }
                            }
                        }

                        SpentSingleton.transactionList = transactionList;
                        SpentSingleton.feedItemsList = feedItemsList;
                    }


                    Intent i = new Intent(MainActivity.this, PagerActivity.class);

                    startActivity(i);
                } else {

                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                    Toast.makeText(MainActivity.this, "Permission denied to read your SMSs", Toast.LENGTH_SHORT).show();
                }
                return;
            }

            // other 'case' lines to check for other
            // permissions this app might request
        }
    }
}

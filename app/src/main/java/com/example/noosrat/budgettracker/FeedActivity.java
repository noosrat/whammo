package com.example.noosrat.budgettracker;

import android.content.ContentResolver;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.provider.Telephony;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import java.util.Date;
import java.util.ArrayList;
import java.util.List;

public class FeedActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feed);

        TextView txtBalance =findViewById(R.id.balance);
        float balance = 0;

        ArrayList<Transaction> transactionList = new ArrayList<>();

        if(ContextCompat.checkSelfPermission(getBaseContext(), "android.permission.READ_SMS") == PackageManager.PERMISSION_GRANTED) {

            ContentResolver cr = getContentResolver();

            String[] banks = {"ABSA", "FNB"};

            ArrayList<String> smsLst = SpentUtilities.getSMSes(banks, new Date(), cr, Telephony.Sms.Inbox.CONTENT_URI);

            if (smsLst != null) {


                for (int k = 0; k < smsLst.size(); k++) {
                    if (SpentUtilities.isBundledSms(smsLst.get(k))) {
                        String[] bundledSMSes = SpentUtilities.smsCleaner(smsLst.get(k));

                        for (int m = 0; m < bundledSMSes.length; m++) {
                            Transaction transaction = new Transaction(bundledSMSes[m]);
                            if (transaction.getTransactionType() != Transaction.TRANSACTION_TYPE_DEPOSIT && transaction.getTransactionType() != Transaction.TRANSACTION_TYPE_INFO)
                                transactionList.add(transaction);
                        }
                    }
                    else{
                        Transaction transaction = new Transaction(smsLst.get(k));
                        if (transaction.getTransactionType() != Transaction.TRANSACTION_TYPE_DEPOSIT && transaction.getTransactionType() != Transaction.TRANSACTION_TYPE_INFO)
                            transactionList.add(transaction);
                    }
                }
            }


            RecyclerView rv = findViewById(R.id.recyclerView);

            LinearLayoutManager llm = new LinearLayoutManager(FeedActivity.this);
            rv.setLayoutManager(llm);
            TransactionAdapter adapter = new TransactionAdapter(transactionList, FeedActivity.this);

            rv.setAdapter(adapter);


        }
        txtBalance.setText(balance + "");
    }
}

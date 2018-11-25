package com.example.noosrat.budgettracker;

import android.content.ContentResolver;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.provider.Telephony;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import com.example.noosrat.budgettracker.Utilities.CustomSparkAdapter;
import com.example.noosrat.budgettracker.Utilities.MerchantHelper;
import com.robinhood.spark.SparkView;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Random;

public class FeedActivity extends AppCompatActivity {

    private ArrayList<Float> sumDataList = new ArrayList<>();
    private float[] yData;
    private Random random;
    private SparkView sparkView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feed);

        sparkView = (SparkView) findViewById(R.id.sparkview);
        TextView txtBalance = findViewById(R.id.balance);
        float balance = 0;

        ArrayList<Transaction> transactionList = new ArrayList<>();

        if (ContextCompat.checkSelfPermission(getBaseContext(), "android.permission.READ_SMS") == PackageManager.PERMISSION_GRANTED) {

            ContentResolver cr = getContentResolver();

            String[] banks = {"ABSA", "FNB"};

            ArrayList<String> smsLst = SpentUtilities.getSMSes(banks, new Date(), cr, Telephony.Sms.Inbox.CONTENT_URI);

            if (smsLst != null) {

                MerchantHelper mh = new MerchantHelper();

                for (int k = 0; k < smsLst.size(); k++) {
                    if (SpentUtilities.isBundledSms(smsLst.get(k))) {
                        String[] bundledSMSes = SpentUtilities.smsCleaner(smsLst.get(k));

                        for (int m = 0; m < bundledSMSes.length; m++) {
                            Transaction transaction = new Transaction(bundledSMSes[m]);
                            if (transaction.getTransactionType() != Transaction.TRANSACTION_TYPE_DEPOSIT && transaction.getTransactionType() != Transaction.TRANSACTION_TYPE_INFO) {
                                transaction.setMerchant(mh.getMerchant(transaction.getRecipient()));
                                transactionList.add(transaction);

                            }

                        }
                    } else {
                        Transaction transaction = new Transaction(smsLst.get(k));
                        if (transaction.getTransactionType() != Transaction.TRANSACTION_TYPE_DEPOSIT && transaction.getTransactionType() != Transaction.TRANSACTION_TYPE_INFO) {
                            transaction.setMerchant(mh.getMerchant(transaction.getRecipient()));
                            transactionList.add(transaction);
                        }
                    }
                }
            }


            RecyclerView rv = findViewById(R.id.recyclerView);

            LinearLayoutManager llm = new LinearLayoutManager(FeedActivity.this);
            rv.setLayoutManager(llm);
            TransactionAdapter adapter = new TransactionAdapter(transactionList, FeedActivity.this);

            rv.setAdapter(adapter);


        }
        balance = calculateExpenses(transactionList);

        DecimalFormat df = new DecimalFormat("#.##");
        df.setRoundingMode(RoundingMode.CEILING);

        txtBalance.setText(df.format(balance) + "");
    }

    public float calculateExpenses(ArrayList<Transaction> transactionList) {

        float sum = 0;

        for (int i = 0; i < transactionList.size(); i++) {
            sum = sum + transactionList.get(i).getNumberAmount();
            sumDataList.add(15000 - sum);

        }
        sparkView.setAdapter(new CustomSparkAdapter(sumDataList));

        return sum;
    }
}
